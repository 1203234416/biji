@echo off
echo Starting Biji Frontend on port 5173...
cd /d "%~dp0frontend"
call npm run dev
pause
