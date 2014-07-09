#!/bin/sh

javac -cp './src' src/com/indecisive/meshidoko/managers/VoteManager.java
javac -cp './src:./test:./libs/junit-4.11.jar' test/com/indecisive/meshidoko/managers/VoteManagerTest.java
