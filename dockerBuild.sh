#!/usr/bin/env bash
./gradlew clean build
mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/challenge-0.0.1-SNAPSHOT.jar)
docker build -t lforward/lr-challenge .