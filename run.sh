#!/bin/bash
mvn clean package -DskipTests
docker build -t aselab:message .
docker run -p 8085:8085 -d --name aselabmessageservice aselab:message