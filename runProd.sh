#!/usr/bin/env bash

#This script is for demonstration purpose only. This should never be in main repo
java -jar -Dspring.profiles.active=prod ./build/libs/challange-0.0.1-SNAPSHOT.jar --spring.datasource.username=prod --spring.datasource.password=prod