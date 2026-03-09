@echo off
echo start diliver
for /f %%i in ('call mvn -q --non-recursive "-Dexec.executable=cmd" "-Dexec.args=/C echo ${project.version}" "org.codehaus.mojo:exec-maven-plugin:1.3.1:exec"') do set VERSION=%%i
for /f %%i in ('call mvn -q --non-recursive "-Dexec.executable=cmd" "-Dexec.args=/C echo ${project.name}" "org.codehaus.mojo:exec-maven-plugin:1.3.1:exec"') do set NAME=%%i
set FILENAME=%NAME%-%VERSION%
echo %FILENAME%
D:\upis\app\resource-service\ResourceService1.exe stop
copy target\%FILENAME%.jar D:\upis\app\resource-service\ResourceService1.jar
D:\upis\app\resource-service\ResourceService1.exe start