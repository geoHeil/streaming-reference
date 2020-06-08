# streaming referenc example

Streaming reference example with Avro, SchemaRegistry, NiFi, NiFi registry, Kafka/Pulsar, Elastic & Kibana and Flink.

Where a rasperry pi actas as a sensor.

## steps:

```bash
docker-compuse up
```

- Kibana localhost:5601
- NiFi localhost:8080/nifi/
- NiFi registry localhost:18080/nifi-registry
- Schema registry localhost:8081
- Flink localhost:8082
- zookeeper localhost:8081

- Elastic localhost:9200
- broker
    - kafka: localhost:29092 and localhost:9092
    - pulsar

## steps

### nifi stuff
- in registry create a test bucket
- in NiFi connect the registry in the controller settings
    - http://registry:18080
- create a processor & group
- version control

### initial kafka example
- https://towardsdatascience.com/big-data-managing-the-flow-of-data-with-apache-nifi-and-apache-kafka-af674cd8f926

- follow along and set-up NiFi
- then prepare kafka

```bash
docker-compose exec broker \
    kafka-topics --create --topic test --partitions 1 --replication-factor 1 --if-not-exists --zookeeper zookeeper:2181

docker-compose exec broker  \
    kafka-topics --describe --topic test --zookeeper zookeeper:2181

docker-compose exec broker  \
    kafka-console-consumer --bootstrap-server localhost:29092 --topic test --from-beginning --max-messages 30
```

- let the messages flow

> WARNING: need to fix connectivity with Kafka - currently, this is a timeout.