:: Author: Rony Byalsky
:: To run this, add %ANDROID_SDK_HOME%\tools to your "path" in the environment variables.

set project-name=uia-server
cd %~dp0
cd ..
rem set project-path=%~dp0

:: Run android-list-targets.bat to get the API level ID to use in the command below after the -t flag
call android.bat create uitest-project -n %project-name% -t 1 -p .

pause