title task03

:: 强制结束进程中的java.exe和任何用java.exe启动的其它进程。
taskkill /f /im "java.exe" /t

cd /d %~dp0

set nowPath=%cd%
set jarPath=%nowPath%\jarPath

::关闭显示
@echo off    
@echo 当前目录为:%cd%

:: 监控项目
cd %nowPath%
@echo build microservice-hystrix-dashboard...
cd microservice-hystrix-dashboard
call mvn clean package -Dmaven.test.skip=true
cd target
copy microservice-hystrix-*.jar %jarPath%
cd %nowPath%


cd %jarPath%

start java -jar microservice-discovery-eureka.jar
start java -jar microservice-provider-user.jar
start java -jar microservice-provider-user.jar --server.port=9001
start java -jar microservice-provider-user.jar --server.port=9002
start java -jar microservice-consume-movie-feign-hystrix.jar
start java -jar microservice-hystrix-dashboard.jar


start "C:\Users\Taowd\AppData\Local\Google\Chrome\Application\chrome.exe" http://127.0.0.1:9090/hystrix 


:: 暂停，阻止程序退出
pause   