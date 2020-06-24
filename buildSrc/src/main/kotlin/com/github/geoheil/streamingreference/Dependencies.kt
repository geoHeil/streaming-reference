// Copyright (C) 0 geoHeil
package com.github.geoheil.streamingreference

import org.gradle.api.JavaVersion

object Libraries {
    object SharedVersions {
        const val scalaVBase = "2.12"
        const val scalaVPatch = "11"
        const val scalaV = "$scalaVBase.$scalaVPatch"

        const val flinkVBase = "1.10"
        const val flinkVPatch = "1"
        const val flinkV = "$flinkVBase.$flinkVPatch"
        val javaVersion = JavaVersion.VERSION_11
    }

    private object Versions {
        //const val slf4jVersion = "" // 1.7.15 todo figure out which version to use
        const val pureconfigV = "0.12.3"
    }

    const val scalaLibrary = "org.scala-lang:scala-library:${SharedVersions.scalaV}"
    const val flinkJava = "org.apache.flink:flink-java:${SharedVersions.flinkV}"
    const val flinkRuntimeWeb = "org.apache.flink:flink-runtime-web_${SharedVersions.scalaVBase}:${SharedVersions.flinkV}"
    const val flinkRuntime = "org.apache.flink:flink-runtime_${SharedVersions.scalaVBase}:${SharedVersions.flinkV}"
    const val flinkStreamingJava = "org.apache.flink:flink-streaming-java_${SharedVersions.scalaVBase}:${SharedVersions.flinkV}"
    const val flinkStreamingScala = "org.apache.flink:flink-streaming-scala_${SharedVersions.scalaVBase}:${SharedVersions.flinkV}"
    //const val slf4j = org.slf4j:slf4j-log4j12:${slf4jVersion}"

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
