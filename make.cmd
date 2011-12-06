@echo off
set APPDIR=..\..\..\apps
call ant
xcopy /Y /S /H jar-sources\* %APPDIR%\MyLocationRedux\libs\