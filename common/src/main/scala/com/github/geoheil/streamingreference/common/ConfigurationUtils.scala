// Copyright (C) 2020 geoHeil
package com.github.geoheil.streamingreference.common

import pureconfig.{ConfigReader, ConfigSource}

object ConfigurationUtils {

  def loadConfiguration[T <: Product](implicit A: ConfigReader[T]): T = {
    val r = ConfigSource.default.load[T]
    r match {
      case Right(s) => s
      case Left(l) =>
        throw new ConfigurationException(
          s"Failed to start. There is a problem with the configuration: ${l}"
        )
    }
  }

}
