"""TCP server that exposes PythonDebugger over a JSON protocol.

Protocol (newline-delimited JSON):
  Request:  {"command": "start", "path": "/abs/path/script.py"}
            {"command": "step"}
  Response: {"locals": {"x": 1, ...}, "lineno": 2}  -- stopped at a line
            {"locals": null, "lineno": null}          -- script finished
            {"error": "message"}                      -- protocol or runtime error
"""

import json
import socket
import sys

from debugger import PythonDebugger


def _serialize(v):
    """Recursively convert a value to something JSON-serializable."""
    if isinstance(v, (bool, int, float, str, type(None))):
        return v
    if isinstance(v, (list, tuple)):
        return [_serialize(i) for i in v]
    if isinstance(v, dict):
        return {str(k): _serialize(val) for k, val in v.items()}
    return repr(v)


def _state_to_resp(result):
    if result is None:
        return {"locals": None, "lineno": None}
    locals_, lineno = result
    return {"locals": {k: _serialize(v) for k, v in locals_.items()}, "lineno": lineno}


def handle_client(conn: socket.socket) -> None:
    dbg: PythonDebugger | None = None
    reader = conn.makefile("r")
    try:
        for raw in reader:
            raw = raw.strip()
            if not raw:
                continue
            req = json.loads(raw)
            cmd = req.get("command")

            if cmd == "start":
                dbg = PythonDebugger(req["path"])
                resp = _state_to_resp(dbg.start())
            elif cmd == "step":
                if dbg is None:
                    resp = {"error": "no active session; send 'start' first"}
                else:
                    resp = _state_to_resp(dbg.step())
            else:
                resp = {"error": f"unknown command: {cmd!r}"}

            conn.sendall((json.dumps(resp) + "\n").encode())
    except Exception as exc:
        try:
            conn.sendall((json.dumps({"error": str(exc)}) + "\n").encode())
        except OSError:
            pass
    finally:
        reader.close()
        conn.close()


def main() -> None:
    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    server.bind(("127.0.0.1", 0))
    server.listen(1)

    port = server.getsockname()[1]
    print(port, flush=True)  # Scala reads this line to learn the port

    conn, _ = server.accept()
    server.close()
    handle_client(conn)


if __name__ == "__main__":
    main()
