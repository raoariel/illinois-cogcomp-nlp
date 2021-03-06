#!/bin/bash

###
# Runs a benchmark evaluation for the Illinois Chunker.
# Labelled test data needs to be provided (not included)


VERSION=2.1.0
MAIN_JAR=target/illinois-chunker-${VERSION}.jar
MAIN=edu.illinois.cs.cogcomp.chunker.main.ChunkTester
LIB=target/dependency

CP=$MAIN_JAR

for JAR in `ls $LIB`; do
	CP="$CP:$LIB/$JAR"
done


TESTDATA="src/main/resources/test.txt"


CMD="java -Xmx2g -cp $CP $MAIN $TESTDATA"

echo "$0: running command '$CMD'..."

${CMD}

echo "$0: done."
