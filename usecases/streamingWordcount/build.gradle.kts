import com.github.geoheil.streamingreference.Libraries

description = "Streaming Wordcount"

dependencies {
    compileOnly(Libraries.flinkJava)
    compileOnly(Libraries.flinkRuntime)
    compileOnly(Libraries.flinkRuntimeWeb)
    compileOnly(Libraries.flinkStreamingScala)
}