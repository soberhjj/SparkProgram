package SparkSQLOperation

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

/**
 * @author huangJunJie 2021-05-03-22:30
 */
object UDFDemo1 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("UDFDemo1")
      .master("local[2]")
      .getOrCreate()

    val df = spark.createDataFrame(List(("福建省", "福州市", "台江区"), ("福建省", "莆田市", "荔城区"), ("福建省", "厦门市", "思明区")))
    val schema = StructType(
      List(
        StructField("province", StringType, false),
        StructField("city", StringType, false),
        StructField("district", StringType, false)
      )
    )
    //根据定义的的schema，新建DataFrame
    val address = spark.createDataFrame(df.rdd, schema)

    address.createTempView("v_address")

    //先来使用下SparkSQL的内置函数concat_ws
    //concat_ws(',',province,city,district)表示将province、city、district这3个字段的值拼接起来，且用逗号分隔
    spark.sql("select concat_ws(',',province,city,district) from v_address").show()

    //接下来自定义一个类似concat_ws函数功能的函数，并将其注册。注册了才能再sql语句中使用
    val func = (split: String, s1: String, s2: String, s3: String) => {
      s1 + split + s2 + split + s3
    }
    spark.udf.register("my_concat_ws",func)
    spark.sql("select my_concat_ws(',',province,city,district) from v_address").show()

    spark.stop()
  }

}
