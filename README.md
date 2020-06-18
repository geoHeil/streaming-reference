[![Build Status](https://travis-ci.com/geoHeil/streaming-reference.svg?branch=master)](https://travis-ci.com/geoHeil/streaming-reference)

# streaming reference example

Streaming reference example with Avro, SchemaRegistry, NiFi, NiFi registry, Kafka/Pulsar, Elastic & Kibana and Flink.

Where a rasperry pi actas as a sensor.


The NiFi flows are version controlled using GIT and can be found in a separate repository.

## steps:

```bash
git clone https://github.com/geoHeil/flow_storage
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

docker-compose exec broker \
    kafka-topics --create --topic tweets-raw --partitions 1 --replication-factor 1 --if-not-exists --zookeeper zookeeper:2181

docker-compose exec broker  \
    kafka-topics --describe --topic test --zookeeper zookeeper:2181

docker-compose exec broker  \
    kafka-console-consumer --bootstrap-server localhost:29092 --topic test --from-beginning --max-messages 30

docker-compose exec broker  \
    kafka-console-consumer --bootstrap-server localhost:29092 --topic tweets-raw --from-beginning --max-messages 30
```

- let the messages flow

### NiFi bitcoins example

1. Connect to registry
2. import processor groups from the registry
3. examine workflows & click play for all (of the bitconi related stuff)

JOLT mode is chain. For a spec of:

```
[{
  "operation": "shift",
  "spec": {
    "timestamp" : "timestamp",
  	"last" : "last",
  	"volume" : "volume"
 }
 
},
{
  "operation": "modify-overwrite-beta",
  "spec": {
    "last": "=toDouble",
    "volume": "=toDouble",
   "timestamp": "${timestamp:append('000'):format('yyyy-MM-dd HH:mm:ss.SSS')}"
  }
}]
```

and input of:

```
{
	"high": "9566.53",
	"last": "9437.12",
	"timestamp": "1592464384",
	"bid": "9430.99",
	"vwap": "9414.02",
	"volume": "5071.24329638",
	"low": "9230.32",
	"ask": "9437.14",
	"open": "9459.82"
}
```

the data is cleaned up and transformed nicely.

If not cleaned up below can be used to fix the data types directly in Elastic:

```
PUT _template/bits_template
{
  "index_patterns": "bits*",
  "order": 1,
  "mappings": {
     "properties": {
      "last": {
        "type": "float"
      },
      "reindexBatch": {
        "type": "text",
        "fields": {
          "keyword": {
            "type": "keyword",
            "ignore_above": 256
          }
        }
      },
      "timestamp": {
        "type": "date",
        "format":
          "epoch_second"
      },
      "volume": {
        "type": "float"
      }
    }
  }
  }
```

Now go to kibana and create an indexing pattern for both document types.

#### Cleaning up in Elastic

```
DELETE bitstamp*
DELETE fixed-bitstamp-*
```

### minifi

TODO

### kafka connect

TODO

### elastic

- https://github.com/tjaensch/nifi_docker_elasticsearch_demo
- https://linkbynet.github.io/elasticsearch/tuning/2017/02/07/Bitcoin-ELK-NiFi.html

### kibana



### pulsar

TODO


### schema registry (hortonworks)

TODO including custom docker image, no SASL

### egeria

TODO atlas egeria

### neo4j

https://community.neo4j.com/t/nifi-goes-neo/11262/6

### nifi improve

data sample flows:

- https://www.youtube.com/watch?v=QJqUpfAy6w4

improvements:

- variables in workflows
- tags
- monitoring
- site2site
- rules engine http://lonnifi.blogspot.com/

### flink job

#### aggregation

#### hive integration 


## other good examples

Or simply other ideas for nice data to stream in this pipeline:

- https://github.com/asdaraujo/edge2ai-workshop