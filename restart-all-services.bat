@echo off
call stop-all-services.bat
timeout /t 5 /nobreak > nul
call start-all-services.bat