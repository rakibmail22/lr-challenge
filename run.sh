#!/usr/bin/env bash
java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -Dspring.profiles.active=dev ./build/libs/challenge-0.0.1-SNAPSHOT.jar