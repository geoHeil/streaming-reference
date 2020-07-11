// Copyright (C) 2020 geoHeil
package com.github.geoheil.streamingreference.tweets

import com.github.geoheil.streamingreference.common.BaseJobConfiguration
import com.github.geoheil.streamingreference.common.conf.{
  KafkaParameters,
  KafkaParametersDefault,
  SchemaRegistrySupport
}

case class TweetsAnalysisConfiguration(
    override val schemaRegistryUrl: String,
    override val kafka: KafkaParameters,
    inputTopic: String
) extends BaseJobConfiguration
    with SchemaRegistrySupport
    with KafkaParametersDefault
