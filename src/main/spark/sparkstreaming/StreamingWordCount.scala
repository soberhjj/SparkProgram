package sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming._

/**
 * @author huangJunJie 2021-05-05-22:58
 *
 *         SparkStreaming入门程序WordCount
 */
object StreamingWordCount {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("StreamingWordCount")
    val ssc = new StreamingContext(conf, Seconds(5))
    ssc.sparkContext.setLogLevel("WARN")

    val lines = ssc.socketTextStream("localhost", 8888)

    val words = lines.flatMap(_.split(" "))

    val wordAndOne = words.map((_, 1))

    val wordCount = wordAndOne.reduceByKey(_ + _)

    wordCount.print()

    ssc.start()
    ssc.awaitTermination()
  }

}
