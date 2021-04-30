#!/bin/sh

javac -d classes src/calculator/*.java
cd classes
jar cvfm ../calculator.jar ../META-INF/MANIFEST.MF calculator/*
cd ..

