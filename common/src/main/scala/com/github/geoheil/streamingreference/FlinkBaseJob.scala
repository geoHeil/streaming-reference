// Copyright (C) 2020 geoHeil
package com.github.geoheil.streamingreference

//import com.sun.org.slf4j.internal.LoggerFactory
import com.github.geoheil.streamingreference.common.ConfigurationUtils
import pureconfig.ConfigReader

abstract class FlinkBaseJob[T <: Product](implicit A: ConfigReader[T])
    extends App {

  // TODO figure out how logging works in flink
  //@transient lazy val logger = LoggerFactory.getLogger(this.getClass)

  val c = ConfigurationUtils.loadConfiguration[T]

}
