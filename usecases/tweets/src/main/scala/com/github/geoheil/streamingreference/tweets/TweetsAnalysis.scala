// Copyright (C) 2020 geoHeil
package com.github.geoheil.streamingreference.tweets

import java.util.Properties

import com.github.geoheil.streamingreference.{FlinkBaseJob, Tweet}

//import com.github.geoheil.streamingreference.common.FlinkBaseJob
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka.{
  FlinkKafkaConsumer,
  FlinkKafkaProducer
}
import java.util.Properties

//import com.github.geoheil.streamingreference.Tweet
import org.apache.flink.configuration.Configuration
import org.apache.flink.core.fs.Path
import org.apache.flink.formats.avro.AvroInputFormat
import org.apache.flink.formats.avro.registry.confluent.ConfluentRegistryAvroDeserializationSchema
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer.Semantic
import pureconfig.generic.auto._
import pureconfig.module.enumeratum._

object TweetsAnalysis extends FlinkBaseJob[TweetsAnalysisConfiguration] {
  println(s"hello: ${c}")

  //val conf = new Configuration();
  // TODO optionally fall back to file (no kafka, no registry needed) and use manually specified avro schema

  // get the execution environment
  val env: StreamExecutionEnvironment =
    StreamExecutionEnvironment.getExecutionEnvironment
  // force disable kryo
  env.getConfig.disableGenericTypes()
  env.getConfig.enableForceAvro()

  // TODO find a better way
//  val conf = env.getConfig
//  conf..disableForceKryo()
//  conf.enableForceAvro()
//  val env: StreamExecutionEnvironment = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(conf)

  // read and log basic information from Kafka
  // 1) using generated case classes

  // 2) using generic record
//  val properties = new Properties()
//  properties.setProperty("bootstrap.servers", "localhost:9092")
//  // only required for Kafka 0.8
//  properties.setProperty("zookeeper.connect", "localhost:2181")
//  properties.setProperty("group.id", "test")
//  stream = env
//    .addSource(new FlinkKafkaConsumer1[String]("topic", new SimpleStringSchema(), properties))
//    .print()

  // **************************************************
//  enable checkpoint for kafka (Flink Kafka Consumer will consume records from a topic and periodically checkpoint all its Kafka offsets, together with the state of other operations, in a consistent manner.)
  // TODO support whole directory of files
  // TODO like in flink training falls back to file avro source if kafka is not enabled
  /*
  val p = new AvroInputFormat[Tweet](
    new Path(
      "/Users/geoheil/development/projects/streaming-reference/example-data/twitter-avro/1bd1a264-c1b2-4c7f-bbf2-3d73ddb7f9af.json"
    ),
    classOf[Tweet]
  )
  val inputDataset = env.createInput(p);
  inputDataset.print
  env.execute("asdf")

   */
//  println(inputDataset.dataType)
//  println(inputDataset.countWindowAll(10))
//  println(inputDataset.name)

  env.enableCheckpointing(5000)

  //set all required properties which is important to connect with kafka
  val properties = new Properties()
  //properties.setProperty("bootstrap.servers", c.kafka.bootstrapServers)
  properties.setProperty("bootstrap.servers", "localhost:9092")
  // only required for Kafka 0.8
  //properties.setProperty("zookeeper.connect", c.kafka.zookeeper)
  properties.setProperty("group.id", "test")
  // always read the Kafka topic from the start
  //properties.setProperty("auto.offset.reset", "earliest")

  val schemaRegistryUrl = "http://localhost:8081"

  //val events = env.addSource(new FlinkKafkaConsumer[Tweet]("twet-raw-json", new JsonSerde(classOf[Tweet], new Tweet), properties))

  // toy example
//  val myProducer = new FlinkKafkaProducer[String](
//    "localhost:9092",         // broker list
//    "my-topic",               // target topic
//    new SimpleStringSchema)   // serialization schema
//  val writerProp = new Properties();
//  writerProp.put("bootstrap.servers", "localhost:9092")
//  val myProducer = new FlinkKafkaProducer("my-topic",
//      new SimpleStringSchema(),
//    writerProp,
//      Semantic.EXACTLY_ONCE);
//      myProducer.setWriteTimestampToKafka(true)
//  stream.addSink(myProducer)

  //get the avro deserialize and serialize object
  val serializer = ConfluentRegistryAvroDeserializationSchema
    .forSpecific[Tweet](classOf[Tweet], schemaRegistryUrl)
  // TODO pre-key the tweets with lang directly from NiFi
  // ConfluentRegistryAvroDeserializationSchema.forGeneric("tweets-raw-value", schemaRegistryUrl)

  val stream = env.addSource(
    new FlinkKafkaConsumer(
      "tweets-raw",
      serializer,
      properties
    ).setStartFromEarliest() // TODO experiment with different start values
  )

  stream.print

  // using Avro & schema registry

  /*

  //crete the employee list which can be used to send data in kafka
  val tweets = env.fromElements(
    Tweet(tweet_id= None, text = Some("asdf"), source = None, geo= None, place = None, lang = None, created_at = None, timestamp_ms = None, coordinates = None, user_id = None, user_name = Some("Yogesh"), screen_name = None, user_created_at= None, followers_count = None, friends_count = None, user_lang = Some("en"), user_location = None, hashtags = None),
    Tweet(tweet_id= None, text = Some("as df gf"), source = None, geo= None, place = None, lang = None, created_at = None, timestamp_ms = None, coordinates = None, user_id = None, user_name = Some("Keshav"), screen_name = None, user_created_at= None, followers_count = None, friends_count = None, user_lang = Some("de"), user_location = None, hashtags = None),
    Tweet(tweet_id= None, text = Some("asdf kjölk asdf"), source = None, geo= None, place = None, lang = None, created_at = None, timestamp_ms = None, coordinates = None, user_id = None, user_name = Some("Mahesh"), screen_name = None, user_created_at= None, followers_count = None, friends_count = None, user_lang = Some("en"), user_location = None, hashtags = None))

  //perform key-by name operation to generate data-stream and send data to kafka(Note : while writing class(pojo) we need to use TypeInformationSerializationSchema)
  val userSource = tweets.keyBy("lang")

  //get the avro deserialize and serialize object for the employee instance which is case class
  val userSerialize = new ConfluentRegistryAvroDeserializationSchema[Tweet](classOf[Tweet])
  //val userSerialize : AvroSerializationSchema[Tweet] = new AvroSerializationSchema[Tweet](classOf[Tweet])
  //val employeeDeserialize : AvroDeserializationSchema[Employee] = new AvroDeserializationSchema[Employee](classOf[Employee])

  val schemaRegistryUrl = "http://localhost:8081"

  //write the employee data into kafka using avro serialization
  userSource.addSink(new FlinkKafkaProducer[Tweet]("test", userSerialize, properties))

  //read the employee data from kafka using avro deserialization(
  val userKafkaReaderResult = env.addSource(new FlinkKafkaConsumer[Tweet]("test",
    ConfluentRegistryAvroDeserializationSchema.forGeneric(classOf[Tweet], schemaRegistryUrl), properties).setStartFromEarliest())

  //print the output data read from kafka
  userKafkaReaderResult.print()


   */

  // for visual topology of the pipeline. Paste the below output in https://flink.apache.org/visualizer/
  println(env.getExecutionPlan)

  env.execute(
    "Avro Serialization/Deserialization using Confluent Registry Example"
  )

}
