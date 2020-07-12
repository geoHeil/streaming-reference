import com.github.geoheil.streamingreference.Libraries

description = "basic twitter frequent item set calculation for hashtags in flink as well as anomaly detection"

dependencies {
    compileOnly(Libraries.flinkJava)
    compileOnly(Libraries.flinkRuntime)
    compileOnly(Libraries.flinkRuntimeWeb)
    compileOnly(Libraries.flinkStreamingScala)
    compileOnly(Libraries.flinkTableApiScalaBridge)

//    implementation(Libraries.log4jApi)
//    implementation(Libraries.log4jCore)
//    implementation(Libraries.log4jSlf4j)
    implementation(Libraries.commonsLogging)
    implementation(Libraries.json4sCore)
    implementation(Libraries.json4sNative)
//    implementation(Libraries.json4sJackson)
//    implementation(Libraries.jacksonScalaModule)

    implementation(project(":common"))
    implementation(project(":common:models"))

    implementation(Libraries.flinkTwitterConnector)
    implementation(Libraries.dataSketches)
}