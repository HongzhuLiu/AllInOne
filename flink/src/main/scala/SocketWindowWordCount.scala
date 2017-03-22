import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

object SocketWindowWordCount {
  /** Main program method */
  def main(args: Array[String]) : Unit = {
    // get the execution environment
    val env: StreamExecutionEnvironment =  StreamExecutionEnvironment.getExecutionEnvironment
    env.setBufferTimeout(100)
    // get input data by connecting to the socket
    val text = env.socketTextStream("172.16.100.134", 9999, '\n')
    // parse the data, group it, window it, and aggregate the counts
    val windowCounts = text
      .flatMap { w => w.split("\\s") }
      .map { w => WordWithCount(w, 1) }
      .keyBy("word")
      //.timeWindow(Time.seconds(5), Time.seconds(1))
        .timeWindow(Time.seconds(3))
      .sum("count")

    // print the results with a single thread, rather than in parallel
    windowCounts.print().setParallelism(1)

    env.execute("Socket Window WordCount")
  }

  /** Data type for words with count */
  case class WordWithCount(word: String, count: Long)
}
