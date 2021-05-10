package sparkstreaming.KeepExactlyOnce.AggregateComputeTask

import java.sql.{Connection, PreparedStatement}

import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @author huangJunJie 2021-05-09-12:07
 *
 *         聚合类型的运算，将计算好的结果和偏移量都保存到Mysql中以保证计算结果的精确
 */
object KafkaWordCountStoreResultAndOffsetInMysql {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[5]").setAppName("KafkaWordCountStoreResultAndOffsetInMysql")
    val ssc = new StreamingContext(conf, Seconds(5))
    ssc.sparkContext.setLogLevel("WARN")


    val groupId = "group_three"
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "192.168.204.101:9092,192.168.204.102:9092,192.168.204.103:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> groupId,
      "auto.offset.reset" -> "earliest",
      "enable.auto.commit" -> (false: java.lang.Boolean) //不让消费者自动提交偏移量
    )
    val topics = Array("topicA")

    //在创建kafkaDStream之前，在Driver端查询历史偏移量
    //从Mysql中读取Kafka的历史偏移量
    val historyOffsets : Map[TopicPartition,Long] = KafkaOffsetUtils.queryHistoryOffsetFromMysql(groupId)
    val kafkaDStream = KafkaUtils.createDirectStream(
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topics, kafkaParams,historyOffsets)
    )

    kafkaDStream.foreachRDD(rdd => {
      if (!rdd.isEmpty()) {
        //获取偏移量
        val offsetRanges: Array[OffsetRange] = rdd.asInstanceOf[HasOffsetRanges].offsetRanges

        //执行聚合类运算，这里以WordCount为例，因为WordCount就是一种聚合类运算
        val reduced = rdd.map(_.value()).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)

        //将各Executor中的计算结果收集到Driver
        val results = reduced.collect()

        /**
         * Driver将计算结果和偏移量信息（在一个事务中）写入到Mysql
         */

        var connection: Connection = null
        var pstm1: PreparedStatement = null
        var pstm2: PreparedStatement = null
        try {
          //1.获取一个数据库连接（使用数据库连接池）
          connection = DruidConnectPoolUtils.getConnection
          //2.mysql默认自动提交事务，所以要取消自动提交事务，来手动控制事务的提交
          connection.setAutoCommit(false)
          //3.将计算结果写入/更新进Mysql
          pstm1 = connection.prepareStatement("insert into wordcount(word,counts) values(?,?) on duplicate key update counts = counts + ?")
          for (t <- results) {
            pstm1.setString(1, t._1)
            pstm1.setLong(2, t._2)
            pstm1.setLong(3, t._2)
            pstm1.addBatch()
          }
          pstm1.executeBatch()
          //4.将偏移量写入/更新进Mysql
          pstm2 = connection.prepareStatement("insert into kafka_offset(topic_partition,gid,offset) values(?,?,?) on duplicate key update offset = ?")
          for (range <- offsetRanges) {
            val topic = range.topic
            val partition = range.partition
            val offset = range.untilOffset

            pstm2.setString(1, topic + "_" + partition)
            pstm2.setString(2, groupId)
            pstm2.setLong(3, offset)
            pstm2.setLong(4, offset)

            pstm2.addBatch()
          }
          pstm2.executeBatch()

          //5.提交事务
          connection.commit()

        } catch {
          case e: Exception => {
            e.printStackTrace()
            //回滚事务
            connection.rollback()
            //停掉SparkStreaming程序。为什么呢？如果不停掉的话，SparkStreaming会继续从kafka读数据，也就是我们上面做的提交偏移量啥的就没意义了
            //所以要停掉SparkStreaming程序，然后外部写一个shell脚本进行监控，如果发现SparkStreaming程序停的了话，就重启程序
            // 重启程序读取Mysql中的Kafka历史偏移量,由于这部分数据的计算失败了，计算结果和偏移量都不会更新，所以SparkStreaming会重新读取这部分数据进行计算
            ssc.stop(true)
          }
        } finally {
          if (pstm1 != null) {
            pstm1.close()
          }
          if (pstm2 != null) {
            pstm2.close()
          }
          if (connection != null) {
            connection.close()
          }
        }
      }
    })
    //开启
    ssc.start()
    //让程序一直运行，将Driver挂起
    ssc.awaitTermination()
  }
}
