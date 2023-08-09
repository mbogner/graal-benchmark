#!/usr/bin/env bash
IMAGE=sample-kotlin-native:latest
DOCKERFILE=Dockerfile-native
docker build -t $IMAGE -f $DOCKERFILE . || exit 1
docker run --rm $IMAGE
