title task03
TASKKILL /F /FI "WINDOWTITLE eq task03"

:: 强制结束进程中的java.exe和任何用java.exe启动的其它进程。
taskkill /f /im "java.exe" /t

cd /d %~dp0

set nowPath=%cd%
set jarPath=%nowPath%\jarPath

::关闭显示
@echo off    
@echo 当前目录为:%cd%



:: 暂停，阻止程序退出
pause   