#!/usr/bin/env bash

set -x

./mvnw clean package -Dquarkus.kubernetes.deploy=true
