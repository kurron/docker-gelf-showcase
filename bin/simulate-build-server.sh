#!/bin/bash

UNIXTIME=$(date +%s)
./gradlew -PrunIntegrationTests=true -PpublishArtifacts=true -Pbranch=master -Ppatch=${UNIXTIME} --stacktrace
