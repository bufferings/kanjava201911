#!/bin/bash
set -e
set -x

SCRIPT_DIR=$(cd $(dirname $0); pwd)

function buildNormal() {
    appName=$1
    normalTag="kanjava1911/${appName}:normal"
    cd ${SCRIPT_DIR}/../micronaut/${appName}

    gradle build
    docker build -t ${normalTag} -f docker/normal.Dockerfile .
}

for app in details reviews ratings productpage
do
    buildNormal $app
done
