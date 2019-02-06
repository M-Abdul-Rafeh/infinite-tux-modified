echo off

IF EXIST .\leveleditor goto deleteimb
mkdir leveleditor

javac -source 1.6 -target 1.6 -d .\leveleditor .\src\main\java\com\mojang\mario\*.java .\src\main\java\com\mojang\mario\level\*.java .\src\main\java\com\mojang\mario\mapedit\*.java .\src\main\java\com\mojang\mario\sprites\*.java .\src\main\java\com\mojang\sonar\*.java .\src\main\java\com\mojang\sonar\mixer\*.java .\src\main\java\com\mojang\sonar\sample\*.java
xcopy src\main\resources\*  leveleditor /E/Y

jar cfm leveleditor.jar manifest.txt -C .\leveleditor . 


IF EXIST .\dist goto deletedist

:deletedist
del /q /s .\dist  > nul
rmdir /q /s .\dist  > nul
:exit

:deleteimb
del /q /s .\leveleditor > nul
rmdir /q /s .\leveleditor > nul
:exit

mkdir .\dist
move /y leveleditor.jar .\dist > nul
del /s /q .\leveleditor  > nul
rmdir /s /q .\leveleditor  > nul


