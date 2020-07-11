import com.github.geoheil.streamingreference.Libraries

description = "basic twitter frequent item set calculation for hashtags in flink as well as anomaly detection"

dependencies {
    compileOnly(Libraries.flinkJava)
    compileOnly(Libraries.flinkRuntime)
    compileOnly(Libraries.flinkRuntimeWeb)
    compileOnly(Libraries.flinkStreamingScala)
    compileOnly(Libraries.flinkTableApiScalaBridge)

    implementation(project(":common"))
    implementation(project(":common:models"))

    implementation(Libraries.flinkTwitterConnector)
    implementation(Libraries.dataSketches)
}