# All Claude Code Prompts — visualPython

## Project Setup
1. Please initialize a scala 2.13 project with sbt in this directory including an example unit test.
2. In this project, please set up a scala play web page.

## Running the Project
3. What command would run this scala play project?
4. *(Pasted application secret error output)*

## Debugger Implementation
5. I would like you to implement an object that captures the python debugger. It should be initialized with the path of a python script. Then there should be a method to run the python script in debugging mode which breaks on the first line of the script. Finally there should be a method that steps to the next line and returns the current variables in the top stack frame.
6. Please make a connector that lets me call the debugger from scala.
7. README.md contains a description of two endpoints that match up with the start and step functions. Please implement these endpoints and connect them to create an instance of the debugger client and step the debugger client accordingly. For now assume there is only one global state for the web app to keep things simple.
8. Could I get a curl command to see the output of start? *(+ curl testing)*

## Frontend
9. Please update the main web page to contain a text box to enter a python program, a run button, a next line button, and a text box showing the current state of the python program memory. These should correspondingly call the endpoints described in README.md.

## AI Integration
10. What would be a good program to demo this project with?
11. Could you add a new endpoint that calls the local instance of ollama with the code and debugging context as well as a query.

## Stack Display
12. Please update this project so that the python debugger wrapper also gives the stack. Then make it so that the stack is displayed in the state of the web page.

## Bug Fixes
13. I am currently getting this error in this project, could you help me fix it? Error: Unexpected response from Ollama

## UI Tweaks
14. Could you change the page to have a title of "AI Debugger".
15. Please change "ask ollama" to "explain this step".
