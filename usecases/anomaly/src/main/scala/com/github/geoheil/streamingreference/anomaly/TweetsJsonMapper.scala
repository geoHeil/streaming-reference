// Copyright (C) 2020 geoHeil
package com.github.geoheil.streamingreference.anomaly

import org.apache.flink.api.common.functions.FlatMapFunction
import org.apache.flink.util.Collector
import org.json4s._
import org.json4s.native.JsonMethods._

class TweetsJsonMapper
//    extends RichFlatMapFunction[String, Seq[HashtagLangTuple]] {
    extends FlatMapFunction[String, HashtagLangTuple] {

//  override def open(parameters: Configuration) = {
  // initialize heavy objects only once - not for each record.
  // TODO question: would a simple @transient lazy val ... also do the job and a simple scala map/MapFunction?
  //p = Pattern.compile("#\\w+")
//  }

  override def flatMap(
      tweet: String,
      out: Collector[HashtagLangTuple]
  ): Unit = {
    val jsonNode = parse(tweet).camelizeKeys
    implicit val formats = DefaultFormats
    val stuff = jsonNode.extractOpt[RawTweetFragment]
    stuff match {
      case None => Seq()
      case Some(s) =>
        if (stuff.isDefined) {
          val sh = stuff.head
          if (sh.lang.equals("en") || sh.lang.equals("de")) {
            s.entities.hashtags
              .map(tag => {
                out.collect(
                  HashtagLangTuple(s.lang, tag.text, 1, s.timestampMs.toLong)
                )
              })
          }
        }
    }
  }
}
