title task02
:: TASKKILL /F /FI "WINDOWTITLE eq task01"

:: 强制结束进程中的java.exe和任何用java.exe启动的其它进程。
taskkill /f /im "java.exe" /t

cd /d %~dp0

set nowPath=%cd%
set jarPath=%nowPath%\jarPath

::关闭显示
@echo off    
@echo 当前目录为:%cd%

@echo build microservice-discovery-eureka...
cd microservice-discovery-eureka
call mvn clean package -Dmaven.test.skip=true
cd target
copy microservice-discovery-eureka*.jar %jarPath%
@echo 当前目录为:%cd%

cd %nowPath%
@echo build microservice-provider-user...
cd microservice-provider-user
call mvn clean package -Dmaven.test.skip=true
cd target
copy microservice-provider-user*.jar %jarPath%
@echo 当前目录为:%cd%


cd %nowPath%
@echo build microservice-consume-movie-feign...
cd microservice-consume/microservice-consume-movie-feign
call mvn clean package -Dmaven.test.skip=true
cd target
copy microservice-consume-movie*.jar %jarPath%
cd %nowPath%

cd %jarPath%

start java -jar microservice-discovery-eureka.jar
start java -jar microservice-provider-user.jar
start java -jar microservice-provider-user.jar --server.port=9001
start java -jar microservice-provider-user.jar --server.port=9002
start java -jar microservice-consume-movie-feign.jar


c:
cd C:\Users\Taowd\AppData\Local\Google\Chrome\Application
start chrome.exe http://127.0.0.1:8761/

:: 暂停，阻止程序退出
pause   