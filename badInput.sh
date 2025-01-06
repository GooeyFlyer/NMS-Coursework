#!/bin/bash
find . -name "*.class" -delete
javac NMS.java
java NMS devices.txt connections.txt PC8