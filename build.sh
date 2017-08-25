#!/bin/bash

./mvnw package
docker build -t the-courtroom .
