#!/usr/bin/env bash

#This script is for demonstration purpose only. This should never be in main repo
docker run -e "SPRING_PROFILES_ACTIVE=prod" -e "SPRING_DATASOURCE_USERNAME=produser" -e "SPRING_DATASOURCE_PASSWORD=prodpass" -p 8080:8080 -t lforward/lr-challenge