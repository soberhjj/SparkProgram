package SparkSQLOperation

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

/**
 * @author huangJunJie 2021-04-18-11:16
 *
 *         读取csv数据 创建DataFrame 再将DataFrame输出为csv
 */
object ReadCSVToCreateDF {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("ReadCSVToCreateDF")
      .master("local[2]")
      .getOrCreate()

    /**
     * 读取csv数据来创建DataFrame。如果csv是不带header的，需要先定义好schema。
     */
    val schema = StructType(
      List(
        StructField("name", StringType, false),
        StructField("age", IntegerType, false),
        StructField("address", StringType, false)
      )
    )
    val df = spark.read.schema(schema).csv("src\\main\\spark\\SparkSQLOperation\\user.csv")

    df.printSchema()
    df.show()

    //将DataFrame输出为csv
    //df.write.mode("overwrite").csv("src\\main\\spark\\SparkSQLOperation\\user_out.csv")

    /**
     * 读取csv数据来创建DataFrame。如果csv是带header的，开通过开启header和字段类型推断来由spark自动完识别出schema。
     */
    val df1 = spark
      .read
      .option("header",true)
      .option("inferSchema",true)
      .csv("src\\main\\spark\\SparkSQLOperation\\user_with_header.csv")

    df1.printSchema()
    df1.show()
    //将DataFrame输出为csv
    //df1.write.mode("overwrite").csv("src\\main\\spark\\SparkSQLOperation\\user_with_header_out.csv")


    spark.stop()
  }

}
