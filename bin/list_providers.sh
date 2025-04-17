#!/bin/bash

TAR="${TAR_EXEC:-gtar}"
DATASOURCE_URL="https://zenodo.org/records/14851262/files/datasource.tar?download=1"

if [ ! -d data ]; then
  mkdir data
fi

if [ ! -f data/datasource.tar ]; then
    >&2 echo "Downloading ${DATASOURCE_URL}"
    curl -s --output data/datasource.tar ${DATASOURCE_URL}
    if [ $? -ne 0 ]; then
        >&2 echo "Download failed"
        exit 2
    fi
fi

$TAR -x -f data/datasource.tar --to-command 'zcat - | jq -r .officialName '