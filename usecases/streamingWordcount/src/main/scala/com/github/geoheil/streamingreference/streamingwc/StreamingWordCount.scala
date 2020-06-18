// Copyright (C) 0 geoHeil
package com.github.geoheil.streamingreference.streamingwc

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

/**
 * Implements a streaming windowed version of the "WordCount" program.
 *
 * This program connects to a server socket and reads strings from the socket.
 * The easiest way to try this out is to open a text sever (at port 12345)
 * using the ''netcat'' tool via
 * {{{
 * nc -l 9000 on Linux or nc -l -p 9000 on Windows
 * }}}
 * and run this example with the hostname and the port as arguments..
 *
 * And follow the logs (i.e. output) at: `tail -f log/flink-*.out` based on the location of the
 * default flink installation
 */
object StreamingWordCount extends App {
  println("hello")

  // the host and the port to connect to
  var hostname: String = "localhost"
  var port: Int = 0

  try {
    val params = ParameterTool.fromArgs(args)
    hostname = if (params.has("hostname")) params.get("hostname") else "localhost"
    port = params.getInt("port")
  } catch {
    case e: Exception => {
      System.err.println("No port specified. Please run 'SocketWindowWordCount " +
        "--hostname <hostname> --port <port>', where hostname (localhost by default) and port " +
        "is the address of the text server")
      System.err.println("To start a simple text server, run 'netcat -l <port>' " +
        "and type the input text into the command line")
      throw new Exception("invalid configuration")
    }
  }

  // get the execution environment
  val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

  // get input data by connecting to the socket
  val text: DataStream[String] = env.socketTextStream(hostname, port, '\n')

  // parse the data, group it, window it, and aggregate the counts
  val windowCounts = text
    //.flatMap { w => w.split("\\s") }
    .flatMap { w => w.split(" ") }
    .map { w => WordWithCount(w, 1) }
    .keyBy("word")
    .timeWindow(Time.seconds(5))
    .sum("count")

  // print the results with a single thread, rather than in parallel
  windowCounts.print().setParallelism(1)

  env.execute("Socket Window WordCount")
}

/** Data type for words with count */
case class WordWithCount(word: String, count: Long)
