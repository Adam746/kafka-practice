docker-compose up -d

./scripts/init-topics.sh

mvn exec:java

To list existing topics
docker exec broker-3.8 kafka-topics --list --bootstrap-server localhost:9092

To completely reset broker and delete kafka topics
docker exec broker-3.8 sh -c "kafka-topics --list --bootstrap-server localhost:9092 | grep 'streams-app' | xargs -I {} kafka-topics --delete --topic {} --bootstrap-server localhost:9092"