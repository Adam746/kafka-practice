docker-compose up -d

docker-compose down


### To view KafkaUI go to http://localhost:8080/

To start Stream Processor, run App.main()

#### To produce an Order message:
    Use KafkaUI Produce Message
    --or--
    run OrderProducer.main()\
    
#### To produce a Product message:
    Use KafkaUI Produce Message
    --or--
    run ProductProducer.main()

#### To create shell inside kafka
    docker exec -it broker-3.8 bash
    to exit, use Ctrl + D or type exit

#### To list existing topics
    docker exec broker-3.8 kafka-topics --list --bootstrap-server localhost:9092

#### To completely reset broker and delete kafka topics (probably don't do this it broke lots of stuff)
    docker exec broker-3.8 sh -c "kafka-topics --list --bootstrap-server localhost:9092 | grep 'streams-app' | xargs -I {} kafka-topics --delete --topic {} --bootstrap-server localhost:9092"

#### If you get: Schema being registered is incompatible with an earlier schema for subject "{schema-name-here}"...
    Delete the existing schema by opening Git Bash and executing: 
        curl -X DELETE http://localhost:8081/subjects/{schema-name-here}

#### To fully restart kafka-ui after changes, use:
    docker compose up -d --force-recreate kafka-ui
