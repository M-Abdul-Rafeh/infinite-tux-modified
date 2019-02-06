#!/bin/bash
make clean
make
cd leveleditor
java com.mojang.mario.mapedit.LevelEditor
cd ..
