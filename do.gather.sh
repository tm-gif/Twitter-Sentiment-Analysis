#!/bin/bash

# This script runs a loop for a number of iterations.
# On each iteration it could call SearchTweets
# Between iterations it sleeps.
# You must make it executable (i.e., chmod +x do.gather)

numseconds=1000 # time between runs
iters=100 # number of iterations
i=0

while [ $i -lt $iters ]
do
	echo Iteration $i
	# Call SearchTweets right here.
	sleep $numseconds
	i=$[$i+1]
done