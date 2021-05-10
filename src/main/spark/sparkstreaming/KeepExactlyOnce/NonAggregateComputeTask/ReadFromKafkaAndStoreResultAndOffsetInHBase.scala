package sparkstreaming.KeepExactlyOnce.NonAggregateComputeTask

import java.util

import com.alibaba.fastjson.{JSON, JSONException}
import org.apache.hadoop.hbase.TableName
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.util.Bytes
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.{SparkConf, TaskContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @author huangJunJie 2021-05-10-21:18
 *
 *         非聚合类型的运算，将计算好的结果和偏移量都保存到HBase中以保证计算结果的精确
 */
object ReadFromKafkaAndStoreResultAndOffsetInHBase {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[5]").setAppName("ReadFromKafkaAndStoreResultAndOffsetInHBase")
    val ssc = new StreamingContext(conf, Seconds(5))
    ssc.sparkContext.setLogLevel("WARN")

    val groupId = "group_one"
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
    //从HBase中查询Kafka的历史偏移量（即上一次成功写入到HBase的偏移量）
    val historyOffsets: Map[TopicPartition, Long] = null //HBaseOffsetUtils.queryHistoryOffsetFromHBase(groupId)

    val kafkaDStream = KafkaUtils.createDirectStream(
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topics, kafkaParams, historyOffsets)
    )

    kafkaDStream.foreachRDD(rdd => {
      if (!rdd.isEmpty()) {
        //获取偏移量信息
        val offsetRanges: Array[OffsetRange] = rdd.asInstanceOf[HasOffsetRanges].offsetRanges

        //获取Kafka中的数据
        val lines: RDD[String] = rdd.map(_.value())
        //过滤问题数据（如将为null的数据过滤掉）
        val filtered = lines.filter(_ != null)
        //对数据进行解析以及一系列操作（如关联一些维度）
        val orderRDD = filtered.map(line => {
          var order: Order = null
          try {
            order = JSON.parseObject(line, classOf[Order])
          } catch {
            case e: JSONException => {
              //TODO
            }
          }
          order
        })
        //将处理后的数据保存到HBase中
        orderRDD.foreachPartition(iter => {
          //将RDD中每个分区的数据保存在HBase中
          if (iter.nonEmpty) {
            //创建一个HBase连接
            val hbaseConnection = HBaseUtil.getConnection("192.168.204.101,192.168.204.102,192.168.204.103", 2181)

            /**
             * 开始把数据写到HBase
             */
            val htable = hbaseConnection.getTable(TableName.valueOf("t_orders"))
            val hbasePutList = new util.LinkedList[Put]()
            iter.foreach(order => {
              //将每条数据封装成HBase的Put对象
              val oid = order.oid
              val money: Double = order.money
              val put = new Put(Bytes.toBytes(oid))
              //HBase的t_orders表有两个列族，一个data列族（存处理后的数据），一个是offset列族（存kafka偏移量信息）
              //oid已作为rowkey，只要将money值存进data列族中，并且该值的字段名设为money
              put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("money"), Bytes.toBytes(money))
              //接着将偏移量信息存进offset列族中。注意存的时候不需要分区中的每条数据都记录偏移量信息，只需要分区的最后一条数据记录偏移量信息即可
              // !iter.hasNext为true的话即表示已经是分区的最后一条数据了
              if (!iter.hasNext) {
                //拿出偏移量信息
                val offsetRange: OffsetRange = offsetRanges(TaskContext.get().partitionId())
                put.addColumn(Bytes.toBytes("offset"), Bytes.toBytes("gid"), Bytes.toBytes(groupId))
                put.addColumn(Bytes.toBytes("offset"), Bytes.toBytes("topic"), Bytes.toBytes(offsetRange.topic))
                put.addColumn(Bytes.toBytes("offset"), Bytes.toBytes("partition"), Bytes.toBytes(offsetRange.partition))
                put.addColumn(Bytes.toBytes("offset"), Bytes.toBytes("offset"), Bytes.toBytes(offsetRange.untilOffset))
              }
              hbasePutList.add(put)

              //当hbasePutList中每达到100个Put对象就执行一次写入，即每一条数据执行一次写入。这样做的目的是防止一次写入过多导致出现一些意外情况
              if (hbasePutList.size()==100){
                //执行写入
                htable.put(hbasePutList)
                //清空hbasePutList
                hbasePutList.clear()
              }
            })
            //最后还要再执行一次写入收尾
            htable.put(hbasePutList)
          }
        })
      }
    })


  }

}
