#!/bin/bash

docker run --rm -ti --net=host -p 8080:8080 -v /var/run/docker.sock:/var/run/docker.sock the-courtroom
