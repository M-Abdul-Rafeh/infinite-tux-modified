#!/bin/bash
make clean
make
cd infinitetux
java com.mojang.mario.mapedit.LevelEditor
cd ..
