package sparkstreaming

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}

/**
 * @author huangJunJie 2021-05-06-21:45
 */
object KafkaSourceWordCount {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[5]").setAppName("KafkaSourceWordCount")
    val ssc = new StreamingContext(conf, Seconds(5))
    ssc.sparkContext.setLogLevel("WARN")

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "192.168.204.101:9092,192.168.204.102:9092,192.168.204.103:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "group_one",
      "auto.offset.reset" -> "earliest"
    )
    val topics = Array("topicA")

    //SparkStreaming和Kafka整合，使用的是直连方式，这种方式下是使用Kafka底层的消费API（SparkStreaming不需要去连接zookeeper），效率更高
    //直连方式：SparkStreaming与Kafka的topic中的每一个分区直接连接
    val kafkaDStream = KafkaUtils.createDirectStream(
      ssc,
      LocationStrategies.PreferConsistent, //位置策略。SparkStreaming会按照就近原则读取Kafka中的数据。比如说某个机器节点上既有SparkStreaming又有Kafka，那么该节点上的SparkStreaming程序会从该节点上的Kafka中读数据
      ConsumerStrategies.Subscribe[String, String](topics, kafkaParams) //消费策略。指定读哪些topic，指定kafka参数
    )

    val wordCount = kafkaDStream.map(_.value()).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)

    wordCount.print()

    //开启
    ssc.start()
    //让程序一直运行，将Driver挂起
    ssc.awaitTermination()
  }

}
