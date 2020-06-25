// Copyright (C) 2020 geoHeil
package com.github.geoheil.streamingreference.tweets

import java.util.Properties

import com.github.geoheil.streamingreference.common.FlinkBaseJob
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka.{
  FlinkKafkaConsumer,
  FlinkKafkaProducer
}
import java.util.Properties
import org.apache.flink.formats.avro.registry.confluent.ConfluentRegistryAvroDeserializationSchema
import pureconfig.generic.auto._
import pureconfig.module.enumeratum._

object TweetsAnalysis extends FlinkBaseJob[TweetsAnalysisConfiguration] {
  println(s"hello: ${c}")

  // TODO optionally fall back to file (no kafka, no registry needed) and use manually specified avro schema

  // get the execution environment
  val env: StreamExecutionEnvironment =
    StreamExecutionEnvironment.getExecutionEnvironment

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
  //enable checkpoint for kafka (Flink Kafka Consumer will consume records from a topic and periodically checkpoint all its Kafka offsets, together with the state of other operations, in a consistent manner.)
  /*
  env.enableCheckpointing(5000)

  //set all required properties which is important to connect with kafka
  val properties = new Properties()
  properties.setProperty("bootstrap.servers", "localhost:9092")
  // only required for Kafka 0.8
  properties.setProperty("zookeeper.connect", "localhost:2181")
  properties.setProperty("group.id", "test")
  // always read the Kafka topic from the start
  properties.setProperty("auto.offset.reset", "earliest")

  //crete the employee list which can be used to send data in kafka
  val userList = env.fromElements(
    new User("Yogesh", 26,"Yellow"),
    new User("Keshav",76,"Green"),
    new User("Mahesh",45,"Blue"))

  //perform key-by name operation to generate data-stream and send data to kafka(Note : while writing class(pojo) we need to use TypeInformationSerializationSchema)
  val userSource = userList.keyBy("name")

  //get the avro deserialize and serialize object for the employee instance which is case class
  val userSerialize : AvroSerializationSchema[User] = new AvroSerializationSchema[User](classOf[User])
  //val employeeDeserialize : AvroDeserializationSchema[Employee] = new AvroDeserializationSchema[Employee](classOf[Employee])

  val schemaRegistryUrl = "http://localhost:8081"

  //write the employee data into kafka using avro serialization
  userSource.addSink(new FlinkKafkaProducer[User]("test", userSerialize, properties))

  //read the employee data from kafka using avro deserialization(
  val userKafkaReaderResult = env.addSource(new FlinkKafkaConsumer[User]("test",
    ConfluentRegistryAvroDeserializationSchema.forSpecific(classOf[User], schemaRegistryUrl), properties).setStartFromEarliest())

  //print the output data read from kafka
  userKafkaReaderResult.print()


  env.execute("Avro Serialization/Deserialization using Confluent Registry Example")

 */

}
