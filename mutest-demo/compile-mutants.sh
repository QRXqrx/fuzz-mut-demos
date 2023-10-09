#!/bin/bash

if [ $# -lt 1 ]; then
  echo "<prepare-mutest>: <POOL_DIR>"
  exit 1
fi

# Mutant pool dir
POOL_DIR="$1"

# Compile mutants
for FN in $(ls "$POOL_DIR")
do
  # Locate dir for each mutant
  MUT_DIR="$POOL_DIR/$FN"
  # Locate each source file.
  for SRC_FILE in $(find "$MUT_DIR" | grep ".java")
  do
    # Compile and output to the directory.
    echo "javac -d $MUT_DIR $SRC_FILE"
    javac -d "$MUT_DIR" "$SRC_FILE"
  done
done
