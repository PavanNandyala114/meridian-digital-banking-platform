@echo off
setlocal enabledelayedexpansion

echo Stopping services...

set "PIDS="

for %%p in (8761 8081 8082 8083) do (
    for /f "tokens=5" %%a in ('netstat -ano ^| findstr :%%p') do (
        echo !PIDS! | find "%%a" > nul
        if errorlevel 1 (
            echo Killing process on port %%p with PID %%a
            taskkill /PID %%a /F
            set "PIDS=!PIDS! %%a"
        )
    )
)

echo All matching services stopped.
pause