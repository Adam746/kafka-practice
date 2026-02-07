#!/bin/bash
cub kafka-ready -b broker:29092 1 30

kafka-topics --create --if-not-exists --bootstrap-server broker:29092 --partitions 1 --replication-factor 1 --topic practice-input
kafka-topics --create --if-not-exists --bootstrap-server broker:29092 --partitions 1 --replication-factor 1 --topic product
kafka-topics --create --if-not-exists --bootstrap-server broker:29092 --partitions 1 --replication-factor 1 --topic order
kafka-topics --create --if-not-exists --bootstrap-server broker:29092 --partitions 1 --replication-factor 1 --topic practice-output

echo "Topics created successfully!"