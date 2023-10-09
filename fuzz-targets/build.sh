#!/bin/bash

TRGT_DIR=$(dirname "$0")
pushd "$TRGT_DIR" || exit 1
TRGT_DIR="$PWD"
popd || exit 1

CLASS_DIR="$TRGT_DIR/classes"
if [ -e "$CLASS_DIR" ]; then
  rm -rf "$CLASS_DIR"
fi
mkdir "$CLASS_DIR"

javac -d "$CLASS_DIR" "$TRGT_DIR"/edu/nju/isefuzz/target/Target*.java
echo "Compile classes to: $CLASS_DIR"
