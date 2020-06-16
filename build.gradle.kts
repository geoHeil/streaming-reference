import org.gradle.internal.impldep.org.joda.time.YearMonth.YEAR

plugins {
    idea
//    `maven-publish`
    id("org.shipkit.java") version "2.3.1"
    id("com.diffplug.gradle.spotless") version "4.3.0"
    id("com.github.maiflai.scalatest") version "0.26" apply false
    id("com.github.johnrengelman.shadow") version "5.2.0" apply false
}

allprojects {
    group = "com.github.geoheil.streamingreference"
    repositories {
        jcenter()
    }

//    publishing {
//        publications {
//            create<MavenPublication>("mavenJava") {
//                artifact shadowJar
//                artifact sourceJar
//                artifact scaladocJar
//                pom {
//                    name.set("My Library")
//                    description.set("A concise description of my library")
//                    url.set("http://www.example.com/library")
//                    properties.set(mapOf(
//                            "myProp" to "value",
//                            "prop.with.dots" to "anotherValue"
//                    ))
//                    licenses {
//                        license {
//                            name.set("The Apache License, Version 2.0")
//                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
//                        }
//                    }
//                    developers {
//                        developer {
//                            id.set("johnd")
//                            name.set("John Doe")
//                            email.set("john.doe@example.com")
//                        }
//                    }
//                    scm {
//                        connection.set("scm:git:git://example.com/my-library.git")
//                        developerConnection.set("scm:git:ssh://example.com/my-library.git")
//                        url.set("http://example.com/my-library/")
//                    }
//                }
//            }
//        }
//    }


}

configure(subprojects/*.filter { it.name == "greeter" || it.name == "greeting-library" }*/) {
    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "scala")
    apply(plugin = "com.github.maiflai.scalatest")
    apply(plugin = "com.diffplug.gradle.spotless")
    apply(plugin = "com.github.johnrengelman.shadow")

    // TODO cross build for multiple scala/flink/spark versions
    base.archivesBaseName = "${project.name}_${Libraries.ScalaVersions.scalaVBase}"

    dependencies {
        // Use Scala 2.13 in our library project
        val implementation by configurations
        val testImplementation by configurations
        val testRuntimeOnly by configurations
        implementation("org.scala-lang:scala-library:2.13.2")

        // Use Scalatest for testing our library
//        testImplementation("junit:junit:null")
        testImplementation(TestLibraries.scalaTest)
//        testImplementation("org.scalatestplus:junit-4-12_2.13:3.1.2.0")

        // Need scala-xml at test runtime
        //testRuntimeOnly("org.scala-lang.modules:scala-xml_2.13:1.2.0")
    }
    spotless {
        scala {
            scalafmt()
            licenseHeader("// Copyright (C) $YEAR geoHeil", "package ")
        }
        kotlin {
            ktfmt()
            licenseHeader("// Copyright (C) $YEAR geoHeil", "package ")
        }
    }

//    java {
//        jar{
//            project.arch archivesBaseName = "asdf"
//        }
//    }
    //archivesBaseName.set("${project.name}_${Libraries.ScalaVersions.scalaVBase}")
    val shadowJar: com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar by tasks
    shadowJar.apply {
        mergeServiceFiles()
//        manifest.attributes.apply {
//            put("Main-Class", serverClassName)
//        }
        archiveBaseName.set("${project.name}_${Libraries.ScalaVersions.scalaVBase}-all")
//        baseName = project.name + "-fat"
    }
    tasks {
        build {
            dependsOn(shadowJar)
        }
    }

//    tasks.withType(ScalaCompile) {
//        configure(scalaCompileOptions.forkOptions) {
//            memoryMaximumSize = '1g'
//            jvmArgs = ['-XX:MaxPermSize=512m']
//        }
//    }
//
//    dependencies {
//        "testCompile"("org.spockframework:spock-core:1.0-groovy-2.4") {
//            exclude(module = "groovy-all")
//        }
//    }
    }
