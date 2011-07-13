call ant
set PROJECTBASE=D:\android-development\app-patching
call xcopy /E /H /Y jar-sources %PROJECTBASE%\beryl-examples\webcache\libs
call xcopy /E /H /Y jar-sources %PROJECTBASE%\projects\android\apps\MakeMotivator\MakeMotivator\libs