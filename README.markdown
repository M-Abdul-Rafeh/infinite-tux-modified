Infinite Tux For Windows, Linux, and OSX
=========================================
This is a great mario game clone for Widnows, Linux, and OSX that will bring tons of fun to your day! Thank you Chris Lewis for putting this out there!<br>
I've replaced all of the proprietary artwork with stuff I created or downloaded and I've added a full screen mode and the ability to exit the game by hitting escape.<br>
Also added some scripts to help compile the game.

Now it's completely open source!

[Infinite Mario Bros](http://www.mojang.com/notch/mario/) source code has a bug in the timing, which prevents it working on Mac OS X, and maybe other platforms. This version uses the timing from the Mario AI competition, and is Mac-compatible.

<br>

Download executable
===================

https://github.com/qbancoffee/infinite-tux/releases

<br>

AI Gameplay Video
--------------

[![AI Gameplay Video](https://img.youtube.com/vi/eiG-bJdwCyc/0.jpg)](https://www.youtube.com/watch?v=eiG-bJdwCyc)
[![AI Gameplay Video over ground](https://img.youtube.com/vi/5RukWnRZ0yI/0.jpg)](https://www.youtube.com/watch?v=5RukWnRZ0yI)
<br>

Gameplay Video
--------------

[![Gameplay Video](https://img.youtube.com/vi/51M4KL25qoM/0.jpg)](https://www.youtube.com/watch?v=51M4KL25qoM)

<br>

ScreenShots
-----------

![Infinite Tux Title Screen](/screenshots/tux_title.png?raw=true "Infinite Tux Title Screen")

![Infinite Tux World Map](/screenshots/tux_map.png?raw=true "Infinite Tux World Map")

![Infinite Tux Above Ground 1](/screenshots/tux_jump_shark.png?raw=true "Infinite Tux Above Ground 1")

![Infinite Tux Above Ground 2](/screenshots/tux_fire_shark.png?raw=true "Infinite Tux Above Ground 2")

![Infinite Tux Dungeon](/screenshots/tux_dungeon_jump.png?raw=true "Infinite Tux Dungeon")

![Infinite Tux Castle](/screenshots/tux_castle_cannon.png?raw=true "Infinite Tux Castle")




<br>

Problems with OpenJDK on Ubuntu 14.04 and 16.04
-----------------------------------------------
Under Ubuntu 14.04 and 16.04 the graphics are not rendered correctly at the window edges when when running the game with an OpenJDK from the
Ubuntu repositories. However, everything works as it should when using a downloaded OpenJDK binary from https://openjdk.java.net or by installing the Oracle JRE even if compiled with OpenJDK<br>
<br>
This is not an issue with an OpenJDK from the Ubuntu 18.04 repositories.

Compiling and running
---------------------
The following was tested on Ubuntu 16.04 and 18.04. At a minimum you'll need a JDK of at least 1.6.<br>
on Ubuntu you can install 1.8 like this. 

You can compile via any of the following methods.
* Maven
* Make
* makeit
* makeit.bat(for windows)

```bash
sudo apt-get install openjdk-8-jdk
```

* With Maven
if you dont have maven installed, this worked for me.
```bash
sudo apt-get install maven
```
Once installed, you can compile, package and run the resulting jar file.

```
mvn compile
mvn package
cd target
java -jar Mario-1.0-jar-with-dependencies.jar
```
* With gnu make<br>
If you don't have make installed, this worked for me.

```bash
sudo apt-get install make
```
Once installed, you can compile, package and run the resulting jar file.

```bash
make
make run
```
You could also start the game by running a starter script created in the dist folder.

```bash
cd dist
./infinitetux
```

or you could run it full screen 

```bash
./infinitetux  f
```
to exit just hit escape.<br>

If you want to install the game.
```bash
sudo make install
```
This should install the game allowing you to start the game by clicking on its icon.<br>
You could open a terminal and start the game from the terminal.
```bash
infinitetux
```
or 
```bash
infinitetux f
```

or

```bash
java -jar infinitetux.jar
```
or

```bash
java -cp .:infinitetux.jar com.mojang.mario.FullScreenFrameLauncher
```


* With java jdk

If you only wish to install the minimum, then all you need is a JDK to compile and run it.<br>
On windows make sure the jdk is in your path.

Under linux and Mac you should only need the makeit script. If the script isn't executable, do the following.<br>
Make executable.
```bash
chmod +x makeit
```
Compile 
```bash
./makeit
cd dist
```
Run
```bash
infinitetux
```
or 
```bash
infinitetux f
```

or

```bash
java -jar infinitetux.jar
```
or

```bash
java -cp .:infinitetux.jar com.mojang.mario.FullScreenFrameLauncher
```

On windows you should just have to double click on the makeit.bat file. I think you could double click on the
jar file and it will start. You can always issue the commands from the command prompt. I still need to make a batch script that
will run the game in full screen  mode.

Compile 
```windows
makeit
```
Run
```windows
java -jar infinitetux.jar
```
or

```windows
java -cp .;infinitetux.jar com.mojang.mario.FullScreenFrameLauncher
```


Making a debian package
-----------------------

You'll need gnu make, a jdk and the following additional dependencies.

devscripts
debhelper
jarwrapper

If you don't hae these installed, this worked for me.

```bash
sudo apt-get install devscripts debhelper jarwrapper
```

Once installed, make the "make_deb_package" script executable and run it.

```bash
chmod +x make_deb_package
./make_deb_package
```

If all goes well, this will produce an installable deb package on ubuntu and possibly other debian based systems.

the deb file should be one level above the current directory.

../infinitetux_1.0_all.deb

Install
```bash
sudo dpkg -i infinitetux_1.0_all.deb
```

