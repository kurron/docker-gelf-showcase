#!/bin/bash

# docker tag SOURCE_IMAGE[:TAG] TARGET_IMAGE[:TAG]
docker tag dockergelfshowcase_gelf:latest kurron/gelf-generator:latest
docker images

# Usage:  docker push [OPTIONS] NAME[:TAG]
docker push kurron/gelf-generator:latest