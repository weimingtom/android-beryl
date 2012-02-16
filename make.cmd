@echo off
set APPDIR=..\..\..\apps
call ant
xcopy /Y /S /H jar-sources\* %APPDIR%\MakeMotivator\MakeMotivator\libs\
xcopy /Y /S /H jar-sources\* %APPDIR%\MyLocationRedux\libs\
xcopy /Y /S /H jar-sources\* %APPDIR%\VolumeWidget\libs\
xcopy /Y /S /H jar-sources\* %APPDIR%\SmartSettings\Common\libs\
xcopy /Y /S /H jar-sources\* %APPDIR%\WidgetPreview\libs\
xcopy /Y /S /H jar-sources\* %APPDIR%\NoKeyguard\libs\
