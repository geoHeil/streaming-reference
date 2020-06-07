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