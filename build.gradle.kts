import com.github.geoheil.streamingreference.Libraries
import org.gradle.internal.impldep.org.joda.time.YearMonth.YEAR

plugins {
    idea
    `maven-publish`
    `java-library`
    id("org.shipkit.java") version "2.3.1"
    id("com.diffplug.gradle.spotless") version "4.3.0"
    id("com.github.maiflai.scalatest") version "0.26" apply false
    id("com.github.johnrengelman.shadow") version "5.2.0" apply false
    //id ("com.zlad.gradle.avrohugger") version "0.4.4" apply false
}

allprojects {
    apply(plugin = "java-library")

    group = "com.github.geoheil.streamingreference"
    repositories {
        jcenter()
    }

    java {
        sourceCompatibility = Libraries.SharedVersions.javaVersion
        targetCompatibility = Libraries.SharedVersions.javaVersion
    }

    // https://github.com/apache/flink/pull/5900
    // NOTE: We cannot use "compileOnly" or "shadow" configurations since then we could not run code
    // in the IDE or with "gradle run". We also cannot exclude transitive dependencies from the
    // shadowJar yet (see https://github.com/johnrengelman/shadow/issues/159).
    // TODO for now run task still needs to be fixed!

    configurations {
        testCompile {
            extendsFrom(configurations.compileOnly.get())
        }
    }
}

configure(subprojects/*.filter { it.name == "greeter" || it.name == "greeting-library" }*/) {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "scala")
    apply(plugin = "checkstyle")
    // apply no automatic formatting validation for auto generated avro classes
    if (!name.startsWith("models")) {
//    if (subprojects.filter { it.name != ":commmon:models" }.isEmpty()) {
        apply(plugin = "com.diffplug.gradle.spotless")
        spotless {
            scala {
                scalafmt()
                licenseHeader("// Copyright (C) 2020 geoHeil", "package ")
//            target{
//                exclude("**/*.md")
//                excludeBuildDirectories()
//            }
            }
            kotlin {
                ktfmt()
                licenseHeader("// Copyright (C) 2020 geoHeil", "package ")
            }
        }
    }
    apply(plugin = "com.github.maiflai.scalatest")
    apply(plugin = "com.github.johnrengelman.shadow")

    // TODO cross build for multiple scala/flink/spark versions
    base.archivesBaseName = "${project.name}_${Libraries.SharedVersions.scalaVBase}"

    // NOTE: We cannot use "compileOnly" or "shadow" configurations since then we could not run code
    // in the IDE or with "gradle run". We also cannot exclude transitive dependencies from the
    // shadowJar yet (see https://github.com/johnrengelman/shadow/issues/159).
    // -> Explicitly define the // libraries we want to be included in the "flinkShadowJar" configuration!

//    configurations {
//        "flinkShadowJar"{
    // dependencies which go into the shadowJar
    // provided by Flink
//            exclude(group = "org.apache.flink", module = "force-shading")
    //flinkShadowJar.exclude group: "org.apache.flink", module: "force-shading"
//            flinkShadowJar.exclude group: "com.google.code.findbugs", module: "jsr305"
//            flinkShadowJar.exclude group: "org.slf4j"
//            flinkShadowJar.exclude group: "log4j"

    // already provided dependencies from serializer frameworks
//            flinkShadowJar.exclude group: "com.esotericsoftware.kryo", module: "kryo"
//            flinkShadowJar.exclude group: "javax.servlet", module: "servlet-api"
//            flinkShadowJar.exclude group: "org.apache.httpcomponents", module: "httpclient"
//        }
//    }

    dependencies {
        val implementation by configurations
//        val testImplementation by configurations
//        val testRuntimeOnly by configurations
        implementation(Libraries.scalaLibrary)
//        implementation("org.scala-lang:scala-library:2.13.2")

        // Use Scalatest for testing our library
//        testImplementation("junit:junit:null")
//        testImplementation(TestLibraries.scalaTest)
//        testImplementation("org.scalatestplus:junit-4-12_2.13:3.1.2.0")

        // Need scala-xml at test runtime
        //testRuntimeOnly("org.scala-lang.modules:scala-xml_2.13:1.2.0")
    }

//    java {
//        jar{
//            project.arch archivesBaseName = "asdf"
//        }
//    }
    //archivesBaseName.set("${project.name}_${Libraries.ScalaVersions.scalaVBase}")
//    val shadowJar: ShadowJar by tasks
//    shadowJar.apply {
//        baseName = artifactID
//        classifier = null
//    }
    val shadowJar: com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar by tasks
    shadowJar.apply {
        // duplicatesStrategy(DuplicatesStrategy.FAIL)
//        mergeServiceFiles()
//        manifest.attributes.apply {
//            put("Main-Class", serverClassName)
//        }
//        baseName = "${project.name}_${Libraries.ScalaVersions.scalaVBase}-all"
//        classifier = null
        archiveBaseName.set("${project.name}_${Libraries.SharedVersions.scalaVBase}")
    }
    tasks {
        build {
            dependsOn(shadowJar)
        }
    }
//    fun MavenPom.addDependencies() = withXml {
//        asNode().appendNode("dependencies").let { depNode ->
//            configurations.compile.allDependencies.forEach {
//                depNode.appendNode("dependency").apply {
//                    appendNode("groupId", it.group)
//                    appendNode("artifactId", it.name)
//                    appendNode("version", it.version)
//                }
//            }
//        }
//    }
// https://kotlinexpertise.com/kotlinlibrarydistibution/
//    val publicationName = "tlslib"
//    publishing {
//        publications.invoke {
//            publicationName(MavenPublication::class) {
//                artifactId = "${project.name}_${Libraries.ScalaVersions.scalaVBase}-all"
//                artifact(shadowJar)
//                pom.addDependencies()
//            }
//        }
//    }


//    publishing {
//        publications.invoke {
//            publicationName(MavenPublication::class) {
//                artifactId = project.name
//                artifact(shadowJar)
//                pom.addDependencies()
//            }
//        }
//    }

//    tasks {
//        withType(GradleBuild::class.java) {
//            dependsOn(shadowJar)
//        }
//    }

//        publishing {
//        publications {
//            create<MavenPublication>("mavenJava") {
//                artifact(shadowJar)
////                artifact(sourceJar)
////                artifact(scaladocJar)
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
