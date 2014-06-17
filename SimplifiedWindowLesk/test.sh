#!/bin/bash
./scorer.pl $1it_$2 dataset21.test.key | grep Total | cut -d',' -f2
