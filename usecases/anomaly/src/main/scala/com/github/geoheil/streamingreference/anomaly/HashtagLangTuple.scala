// Copyright (C) 2020 geoHeil
package com.github.geoheil.streamingreference.anomaly

case class BasicLangHashtags(lang: String, hashTags: Seq[String])
case class HashtagLangTuple(
    lang: String,
    hashTag: String,
    count: Long,
    timestamp: Long
)
case class IndividualTag(text: String, indices: Int)
case class HashTagFragment(text: String)
case class EntitiesFragment(hashtags: Seq[HashTagFragment])
case class RawTweetFragment(
    lang: String,
    entities: EntitiesFragment,
    timestampMs: String
)
