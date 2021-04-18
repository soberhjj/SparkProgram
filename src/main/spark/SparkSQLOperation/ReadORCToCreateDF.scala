package SparkSQLOperation

import org.apache.spark.sql.SparkSession

/**
 * @author huangJunJie 2021-04-18-11:16
 *
 *         读取orc 创建DataFrame 再将DataFrame输出为orc
 *         orc和parquet一样，也是一种列式存储，可压缩的数据存储方式，orc是hive的最爱。SparkSql也提供了对ORC file的支持
 *
 */
object ReadORCToCreateDF {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("ReadORCToCreateDF")
      .master("local[2]")
      .getOrCreate()

    val df = spark.read.orc("")
    df.printSchema()
    df.show()
    df.write.orc("")

    spark.stop()
  }

}
