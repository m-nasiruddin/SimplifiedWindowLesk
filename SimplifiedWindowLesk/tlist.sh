#!/bin/bash

for i in $(seq 1 `ls -l $1*|wc -l`); do
	./test.sh $1 $i
done
