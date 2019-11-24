#!/bin/bash
set -e
set -x

SCRIPT_DIR=$(cd $(dirname $0); pwd)

function deployNormal() {
    appName=$1
    cd ${SCRIPT_DIR}/../micronaut/${appName}

    kubectl kustomize k8s/overlays/normal | kbld -f - | kubectl apply -f -
}

for app in details reviews ratings productpage
do
    deployNormal $app
done
