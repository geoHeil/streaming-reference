plugins {
    `kotlin-dsl`
    //id("com.diffplug.gradle.spotless") version "4.3.0"
}

repositories {
    gradlePluginPortal()
    jcenter()
}
kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

// disabled as intellJ fails to import, gradle CLI works just fine
//spotless {
//    kotlin {
//        ktfmt()
//        licenseHeader("// Copyright (C) ${org.gradle.internal.impldep.org.joda.time.YearMonth.YEAR} geoHeil", "package ")
//    }
//}
