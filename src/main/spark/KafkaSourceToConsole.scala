import com.alibaba.fastjson.JSONObject
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

/**
 * @author huangJunJie 2021-02-03-21:35
 */
object KafkaSourceToConsole {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder().appName("KafkaSourceToConsole").master("local[5]").getOrCreate()

    spark.sparkContext.setLogLevel("WARN")

    val kafkaSource: DataFrame = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "192.168.204.101:9092")
      .option("subscribe", "topic1")
      .load()

    import spark.implicits._
    val df: Dataset[String] = kafkaSource.selectExpr("CAST(value AS STRING)").as[String]

//    val ds: Dataset[info] = df.map(message => new JSONObject().getObject(message, classOf[info]))

    val afterDuplicates: Dataset[String] = df.dropDuplicates("value")

    afterDuplicates.writeStream
      .outputMode("append")
      .format("console")
      .option("checkpointLocation", "E:\\IDEA\\Projects\\SparkProgram\\src\\main\\resource")
      .start().awaitTermination()
  }
}

case class info(eventTime: Long, unqueId: Long, amount: Long)
