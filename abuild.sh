#!/bin/bash
./gradlew build
docker build --build-arg JAR_FILE=build/libs/lendinclub-demo-0.0.1-SNAPSHOT.jar -t lendingclub .
