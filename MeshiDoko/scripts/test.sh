#!/bin/sh

java -cp './src:./test:./libs/junit-4.11.jar:./libs/hamcrest-core-1.3.jar' org.junit.runner.JUnitCore com.indecisive.meshidoko.managers.VoteManagerTest
