// Copyright (C) 0 geoHeil
package com.github.geoheil.streamingreference

import org.gradle.api.JavaVersion

object Libraries {
    object SharedVersions {
        const val scalaVBase = "2.12"
        const val scalaVPatch = "11"
        const val scalaV = "$scalaVBase.$scalaVPatch"

        const val flinkVBase = "1.11"
        const val flinkVPatch = "0"

        //        const val flinkVBase = "1.10"
//        const val flinkVPatch = "1"
        const val flinkV = "$flinkVBase.$flinkVPatch"
        val javaVersion = JavaVersion.VERSION_11
    }

    private object Versions {
        const val log4jVersion = "2.13.3"
        const val pureconfigV = "0.12.3"
        const val dataSketchesV = "1.3.0-incubating"
        const val json4s = "3.6.9"
    }

    const val scalaLibrary = "org.scala-lang:scala-library:${SharedVersions.scalaV}"
    const val flinkJava = "org.apache.flink:flink-java:${SharedVersions.flinkV}"
    const val flinkRuntimeWeb = "org.apache.flink:flink-runtime-web_${SharedVersions.scalaVBase}:${SharedVersions.flinkV}"
    const val flinkRuntime = "org.apache.flink:flink-runtime_${SharedVersions.scalaVBase}:${SharedVersions.flinkV}"
    const val flinkStreamingJava = "org.apache.flink:flink-streaming-java_${SharedVersions.scalaVBase}:${SharedVersions.flinkV}"
    const val flinkStreamingScala = "org.apache.flink:flink-streaming-scala_${SharedVersions.scalaVBase}:${SharedVersions.flinkV}"
    const val flinkTableApiScalaBridge = "org.apache.flink:flink-table-api-scala-bridge_${SharedVersions.scalaVBase}:${SharedVersions.flinkV}"
    const val flinkTwitterConnector = "org.apache.flink:flink-connector-twitter_${SharedVersions.scalaVBase}:${SharedVersions.flinkV}"

    const val dataSketches = "org.apache.datasketches:datasketches-java:${Versions.dataSketchesV}"

    const val flinkAvro = "org.apache.flink:flink-avro:${SharedVersions.flinkV}"
    const val flinkAvroConfluentRegistry = "org.apache.flink:flink-avro-confluent-registry:${SharedVersions.flinkV}"
    const val flinkConnectorKafka = "org.apache.flink:flink-connector-kafka_${SharedVersions.scalaVBase}:${SharedVersions.flinkV}"

    //    const val slf4j = org.slf4j:slf4j-log4j12:${Versions.slf4jVersion}"
//const val log4jSlf4j = "org.apache.logging.log4j:log4j-slf4j-impl:${Versions.log4jVersion}"
//const val log4jApi = "org.apache.logging.log4j:log4j-api:${Versions.log4jVersion}"
//const val log4jCore = "org.apache.logging.log4j:log4j-core:${Versions.log4jVersion}"
    const val commonsLogging = "commons-logging:commons-logging:1.2"

    const val json4sCore = "org.json4s:json4s-core_${SharedVersions.scalaVBase}:${Versions.json4s}"
    const val json4sNative = "org.json4s:json4s-native_${SharedVersions.scalaVBase}:${Versions.json4s}"
//    const val json4sJackson = "org.json4s:json4s-jackson_${SharedVersions.scalaVBase}:${Versions.json4s}"
//    const val jacksonScalaModule = "com.fasterxml.jackson.module:jackson-module-scala_${SharedVersions.scalaVBase}:2.11.1"


    const val pureConfig = "com.github.pureconfig:pureconfig_${SharedVersions.scalaVBase}:${Versions.pureconfigV}"
    const val pureConfigEnumeratum = "com.github.pureconfig:pureconfig-enumeratum_${SharedVersions.scalaVBase}:${Versions.pureconfigV}"

}

object TestLibraries {
    private object Versions {
        const val scalaTestV = "3.1.2"
        //const val junitV = "5.6.2" // 1.2.17
        //const val hamcrestV = "2.2" // 1.3
    }

    const val scalaTest =
            "org.scalatest:scalatest_${Libraries.SharedVersions.scalaVBase}:${Versions.scalaTestV}"

    //const val junit = "junit:junit:${Versions.junitV}"
    const val flinkTestUtilsJunit = "org.apache.flink:flink-test-utils-junit:${Libraries.SharedVersions.flinkV}"
    //const val hamcrest = "org.hamcrest:hamcrest-library:${Versions.hamcrestV}"
}
