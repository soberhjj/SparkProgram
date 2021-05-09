package sparkstreaming

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.{CanCommitOffsets, ConsumerStrategies, HasOffsetRanges, KafkaUtils, LocationStrategies, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @author huangJunJie 2021-05-06-21:45
 *
 *         KafkaSourceManualCommitOffsetWordCount与KafkaSourceWordCount的区别：
 *         前者在kafkaParams中设置了"enable.auto.commit" -> (false: java.lang.Boolean) 即关闭自动提交kafka的offset
 *         而后者没有设置此参数，默认就是自动提交kafka的offset
 *         在KafkaSourceManualCommitOffsetWordCount中来学习下如何手动控制提交offset
 */
object KafkaSourceManualCommitOffsetWordCount {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[5]").setAppName("KafkaSourceManualCommitOffsetWordCount")
    val ssc = new StreamingContext(conf, Seconds(5))
    ssc.sparkContext.setLogLevel("WARN")

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "192.168.204.101:9092,192.168.204.102:9092,192.168.204.103:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "group_two",
      "auto.offset.reset" -> "earliest",
      "enable.auto.commit" -> (false: java.lang.Boolean) //不让消费者自动提交偏移量
    )
    val topics = Array("topicA")

    //SparkStreaming和Kafka整合，使用的是直连方式，这种方式下是使用Kafka底层的消费API（SparkStreaming不需要去连接zookeeper），效率更高
    //直连方式：SparkStreaming与Kafka的topic中的每一个分区直接连接
    val kafkaDStream = KafkaUtils.createDirectStream(
      ssc,
      LocationStrategies.PreferConsistent, //位置策略。SparkStreaming会按照就近原则读取Kafka中的数据。比如说某个机器节点上既有SparkStreaming又有Kafka，那么该节点上的SparkStreaming程序会从该节点上的Kafka中读数据
      ConsumerStrategies.Subscribe[String, String](topics, kafkaParams) //消费策略。指定读哪些topic，指定kafka参数
    )

    /**
     * 要想拿到kafka的offset，有两种方式
     *
     * 方式1如下
     */
    //foreachRDD会依次遍历DStream中的每个RDD。DStream会定期的生成RDD，在上面new StreamingContext中指定了batchDuration为5秒，所以DStream会每隔5秒生成一个RDD
    //注意foreachRDD是在Driver端执行的，所以foreachRDD既不是transform算子也不是action算子
    kafkaDStream.foreachRDD(rdd => {
      //有数据的rdd才会被执行相关操作
      if (!rdd.isEmpty()) {
        //获取偏移量，第一手的RDD是特殊的RDD(它是KafkaRDD)，只有KafkaRDD才有偏移量，其它RDD(比如HadoopRDD等)都是没有偏移量的
        val offsetRanges: Array[OffsetRange] = rdd.asInstanceOf[HasOffsetRanges].offsetRanges //这个强转为HasOffsetRanges也只有KafkaRDD能够这样强转

        //直接打印输出偏移量信息看下
        //        for (elem <- offsetRanges) {
        //          println(s"topic：${elem.topic}, partition：${elem.partition} ,fromOffset：${elem.fromOffset} ,untilOffset：${elem.untilOffset}")
        //        }

        //进行WordCount
        rdd.map(_.value()).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).foreach(println)

        //Driver端异步更新偏移量(偏移量信息记录进)。注意上面的WordCount是在Executor端执行的。下面的更新偏移量是在Driver端执行的，所以这两个是异步的，有可能对rdd进行WordCount还没有计算完，但偏移量已经更新了
        kafkaDStream.asInstanceOf[CanCommitOffsets].commitAsync(offsetRanges)
      }
      //注意偏移量的获取和提交都是在Driver端

    })



    //开启
    ssc.start()
    //让程序一直运行，将Driver挂起
    ssc.awaitTermination()
  }

}
