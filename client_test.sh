#!/bin/bash

# Define the base Java command
base_command='./gradlew :run --args='\''--name=team4  --password=IgUraDn4(kS>_-7>  -v --url=https://husksheets.fly.dev --publisher=george --sheet='

# Define the sheets array
sheets=("S0" "S1" "S2" "S3" "S4" "S5" "S6" "S7" "S8" "S9" "S10" "S11" "S12" "S13" "S13a" "S13b" "S14" "S15" "S16" "S17" "S18" "S19" "S20" "S21" "S22" "S23")

# Iterate over each sheet and execute the Java command
for sheet in "${sheets[@]}"; do
    command="${base_command}${sheet}'"
    echo "Executing: $command"
    eval $command
done
