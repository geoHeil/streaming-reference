package com.github.geoheil.streamingreference.anomaly

import java.util

import com.github.geoheil.streamingreference.anomaly.queries.approximation.FrequentItemSketchStateless
import org.apache.flink.api.java.functions.KeySelector
import org.apache.flink.api.java.tuple.Tuple2
import org.apache.flink.streaming.api.datastream.DataStream
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment

object NaiveFrequentItemsScetch extends App{

  val categories = Seq("A", "B")
  val r = scala.util.Random
  val data = categories.flatMap(cat => {
    Range(1, r.nextInt(100)).map(range => {
      (1, cat)//, range)
    })
  })
  val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
  val inputStream = env.fromElements(data:_*)

//  val targetKeySelector: KeySelector[_, _] = new KeySelector[Tuple2[String, Integer], AnyRef]() {
//    @throws[Exception]
//    override def getKey(stringIntegerTuple2: Tuple2[String, Integer]): Any = stringIntegerTuple2.f0
//  }
//  val targetValueSelector: KeySelector[_, _] = new KeySelector[Tuple2[String, Integer], AnyRef]() {
//    @throws[Exception]
//    override def getKey(stringIntegerTuple2: Tuple2[String, Integer]): Any = stringIntegerTuple2.f1
//  }

//val stream = CountDistinctQueries.runContinuousHll(inputStream, targetKeySelector, targetValueSelector, 1)
val sketchFunction = new FrequentItemSketchStateless[String, Int]((String, Int), 5)
  val stream =   inputStream.keyBy(0).flatMap(sketchFunction)
  stream.print
  env.execute
}
