// Copyright (C) 2020 geoHeil
package com.github.geoheil.streamingreference.anomaly

import com.github.geoheil.streamingreference.FlinkBaseJob
import pureconfig.generic.auto._
import pureconfig.module.enumeratum._
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._

object TweetAnomaly extends FlinkBaseJob[TweetAnomalyConfiguration] {
  println(s"hello: ${c}")

  /*
  val env: StreamExecutionEnvironment =
    StreamExecutionEnvironment.getExecutionEnvironment
  // force disable kryo
  env.getConfig.disableGenericTypes()
  env.getConfig.enableForceAvro()

  // for visual topology of the pipeline. Paste the below output in https://flink.apache.org/visualizer/
  println(env.getExecutionPlan)

  env.execute(
    "Avro Serialization/Deserialization using Confluent Registry Example"
  )

   */

}
