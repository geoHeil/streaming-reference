// Copyright (C) 2020 geoHeil
package com.github.geoheil.streamingreference.tweets

import java.util.Properties

import com.github.geoheil.streamingreference.FlinkBaseJob
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import pureconfig.generic.auto._
import pureconfig.module.enumeratum._

object TweetsAnalysisMinimalistic01
    extends FlinkBaseJob[TweetsAnalysisConfiguration] {
  println(s"hello: ${c}")

  val env: StreamExecutionEnvironment =
    StreamExecutionEnvironment.getExecutionEnvironment
  val properties = new Properties()
  properties.setProperty("bootstrap.servers", "localhost:9092")
  val stream = env.addSource(
    new FlinkKafkaConsumer(
      "hello-streams",
      new SimpleStringSchema(),
      properties
    )
  );
  stream.print
  println(env.getExecutionPlan)

  env.execute("Minimalistic Kafka interaction")
}
