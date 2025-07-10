#!/bin/bash

SOLID_DIR=./out

find ${SOLID_DIR}/test/scholix -name "*.jsonld" -exec rm {} \;