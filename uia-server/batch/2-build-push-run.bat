:: Author: Rony Byalsky

rem set workspace-path=c:\workspaces\uia_workspace_git
set project-name=uia-server
rem set project-path=%workspace-path%\%project-name%
set package-name=il.co.topq.uia.server
set class-name=UiAutomatorTcpServer

cd %~dp0
cd ..


:: 1 - ANT BUILD THE PROJECT - create %project-name%.jar file
rem cd %project-path%
call ant build

:: 2 - PUSH THE JAR FILE TO THE ANDROID DEVICE
adb push bin\%project-name%.jar /data/local/tmp/

:: 3 - RUN THE JAR ON THE DEVICE
adb shell uiautomator runtest %project-name%.jar -c %package-name%.%class-name%

pause