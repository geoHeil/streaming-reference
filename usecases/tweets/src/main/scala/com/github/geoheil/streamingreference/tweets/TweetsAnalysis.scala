// Copyright (C) 2020 geoHeil
package com.github.geoheil.streamingreference.tweets

import com.github.geoheil.streamingreference.common.FlinkBaseJob
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import pureconfig.generic.auto._
import pureconfig.module.enumeratum._

object TweetsAnalysis extends FlinkBaseJob[TweetsAnalysisConfiguration] {
  println(s"hello: ${c.foo}")
  println(s"hello: ${c.foo}")
  println(s"hello: ${c.foo}")

  // get the execution environment
//  val env: StreamExecutionEnvironment =
//    StreamExecutionEnvironment.getExecutionEnvironment

}
