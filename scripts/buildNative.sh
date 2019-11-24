#!/bin/bash
set -e
set -x

SCRIPT_DIR=$(cd $(dirname $0); pwd)

function buildNative() {
    appName=$1
    nativeTag="kanjava1911/${appName}:native"
    cd ${SCRIPT_DIR}/../micronaut/${appName}

    gradle build
    docker build -t ${nativeTag} -f docker/native.Dockerfile .
}

for app in details reviews ratings productpage
do
    buildNative $app
done
