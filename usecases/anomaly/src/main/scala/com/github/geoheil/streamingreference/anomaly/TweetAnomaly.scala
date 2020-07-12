// Copyright (C) 2020 geoHeil
package com.github.geoheil.streamingreference.anomaly

import java.util.Properties

import com.github.geoheil.streamingreference.FlinkBaseJob
import pureconfig.generic.auto._
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.twitter.TwitterSource
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._

object TweetAnomaly extends FlinkBaseJob[TweetAnomalyConfiguration] {
  println(s"hello: ${c}")

  val env: StreamExecutionEnvironment =
    StreamExecutionEnvironment.getExecutionEnvironment
  // force disable kryo
  env.getConfig.disableGenericTypes()
  env.getConfig.enableForceAvro()

  val twitterProps = new Properties()
  twitterProps.setProperty(TwitterSource.CONSUMER_KEY, c.twitter.apiKey)
  twitterProps.setProperty(TwitterSource.CONSUMER_SECRET, c.twitter.apiSecret)
  twitterProps.setProperty(TwitterSource.TOKEN, c.twitter.tokenKey)
  twitterProps.setProperty(TwitterSource.TOKEN_SECRET, c.twitter.tokenSecret)

  // this is initializing the sample endpoint (free, but not all the data)
  // https://eventador.io/blog/flink-json-and-twitter/ shows how to follow specific topics (still free)
  val streamSource = env.addSource(new TwitterSource(twitterProps))

  /*
  // TODO figure out why it is not outputting any results
  val resultStream = streamSource.flatMap{
    (rawTweet, out: Collector[Seq[HashtagLangTuple]]) =>
      val jsonNode = parse(rawTweet)
      val stuff = jsonNode.extractOpt[RawTweetFragment]
      stuff match {
        case None => println("NNNNN")
        case Some(s) => s.entities.hashtags.foreach(println)
      }
      val result = stuff match {
        case None => Seq()
        case Some(s) => s.entities.hashtags.map(tag => HashtagLangTuple(s.lang, tag.text, 1))
      }
      result.foreach(println)
      //val sh = stuff.head
      // TODO implement filter
      out.collect(result)
  }

   */
  val resultStream = streamSource.flatMap(new TweetsJsonMapper)
  resultStream.print
  // TODO experiment with table API (SQL) aggregates
  env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
  val timedStream = resultStream.assignAscendingTimestamps(_.timestamp)

//  resultStream.keyBy(_.lang).window().sum()
//  val counts = recordSlim
//    .keyBy(0)
//    .timeWindow(Time.seconds(30))
//    .sum(1)
//  counts.print



  // for visual topology of the pipeline. Paste the below output in https://flink.apache.org/visualizer/
  println(env.getExecutionPlan)

  env.execute(
    "Tweet Anomaly"
  )
}
