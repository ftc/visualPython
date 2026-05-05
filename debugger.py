from __future__ import annotations

import bdb
import os
import sys
import threading


class PythonDebugger(bdb.Bdb):
    """Controls a Python script running under the debugger."""

    def __init__(self, script_path: str):
        super().__init__()
        self.script_path = os.path.abspath(script_path)
        self._current_frame = None
        self._stopped = threading.Event()
        self._resume = threading.Event()
        self._thread = None
        self._finished = False

    # --- bdb overrides ---

    def user_line(self, frame):
        self._current_frame = frame
        self._stopped.set()
        self._resume.wait()
        self._resume.clear()

    # --- public API ---

    def start(self) -> tuple[dict, int] | None:
        """Run the script in debug mode, breaking on the first line.

        Returns (locals, line_number), or None if the script has no stoppable lines.
        """
        self._thread = threading.Thread(target=self._run_script, daemon=True)
        self._thread.start()
        self._stopped.wait()
        self._stopped.clear()
        return None if self._finished else self._get_state()

    def step(self) -> tuple[dict, int] | None:
        """Advance one source line and return (locals, line_number).

        Returns None when the script has finished executing.
        """
        if self._finished:
            return None
        self.set_step()
        self._resume.set()
        self._stopped.wait()
        self._stopped.clear()
        return None if self._finished else self._get_state()

    # --- internals ---

    def _run_script(self):
        with open(self.script_path, "rb") as f:
            code = compile(f.read(), self.script_path, "exec")

        script_dir = os.path.dirname(self.script_path)
        if script_dir not in sys.path:
            sys.path.insert(0, script_dir)

        globals_dict = {
            "__file__": self.script_path,
            "__name__": "__main__",
            "__builtins__": __builtins__,
        }
        try:
            self.run(code, globals_dict)
        except Exception:
            pass
        finally:
            self._finished = True
            self._stopped.set()

    def _get_state(self) -> tuple[dict, int]:
        frame = self._current_frame
        locals_ = {k: v for k, v in frame.f_locals.items() if not k.startswith("__")}
        return locals_, frame.f_lineno
