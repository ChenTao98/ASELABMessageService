#!/bin/bash
mvn clean package -DskipTests
docker build -t aselab:message .
docker run -p 21000:21000 -d --name aselabmessageservice aselab:message