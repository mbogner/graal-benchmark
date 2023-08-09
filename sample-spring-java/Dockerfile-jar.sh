#!/usr/bin/env bash
IMAGE=sample-spring-java-jar:latest
DOCKERFILE=Dockerfile-jar
docker build -t $IMAGE -f $DOCKERFILE . || exit 1
docker run --rm $IMAGE