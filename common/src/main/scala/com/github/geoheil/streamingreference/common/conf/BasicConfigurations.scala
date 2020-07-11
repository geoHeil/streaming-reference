// Copyright (C) 2020 geoHeil
package com.github.geoheil.streamingreference.common.conf

trait SchemaRegistrySupport {
  val schemaRegistryUrl: String
}

case class KafkaParameters(
    bootstrapServers: String,
    zookeeper: String
)
trait KafkaParametersDefault {
  val kafka: KafkaParameters
}
