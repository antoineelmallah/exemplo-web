#!/bin/bash

DOCKER_IMAGE=markhobson/maven-chrome:jdk-17

docker pull $DOCKER_IMAGE
docker run --rm -it -v "$PWD":/usr/src -w /usr/src $DOCKER_IMAGE mvn clean verify

sudo rm -rf target