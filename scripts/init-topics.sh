#!/bin/bash
# Create the topics required for the practice
docker exec broker-3.8 kafka-topics.sh --create --topic input-topic --bootstrap-server localhost:9092 --if-not-exists
docker exec broker-3.8 kafka-topics.sh --create --topic output-topic --bootstrap-server localhost:9092 --if-not-exists