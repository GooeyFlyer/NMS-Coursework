#!/bin/bash
find . -name "*.class" -delete
javac NMS.java
java NMS temp/devices.txt temp/connections.txt PC8 F1