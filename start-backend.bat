@echo off
echo Starting Biji Backend on port 8080...
cd /d "%~dp0backend"
set PATH=D:\maven\bin;%PATH%
call mvn spring-boot:run -q
pause
