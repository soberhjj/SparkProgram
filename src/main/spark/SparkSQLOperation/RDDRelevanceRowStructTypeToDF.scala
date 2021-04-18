package SparkSQLOperation

import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}

/**
 * @author huangJunJie 2021-04-18-10:52
 *
 *         RDD关联 spark中Row class，然后再加上自定义的StructType（即schema信息） 将RDD转成DataFrame
 */
object RDDRelevanceRowStructTypeToDF {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("RDDRelevanceRowStructTypeToDF")
      .master("local[2]")
      .getOrCreate()

    val sc = spark.sparkContext
    val lines = sc.textFile("src\\main\\spark\\SparkSQLOperation\\user.txt")
    //关联Row
    val rowRDD = lines.map(line => {
      val fields = line.split(" ")
      val f1 = fields(0)
      val f2 = fields(1).toInt
      val f3 = fields(2)
      Row(f1, f2, f3)
    })

    //定义StructType（即schema信息）,即用来描述Row里面的类型信息
    val schema = StructType(
      List(
        StructField("name", StringType, false),
        StructField("age", IntegerType, false),
        StructField("address", StringType, false)
      )
    )

    //将RDD转成DataFrame
    val df1 = spark.createDataFrame(rowRDD, schema)
    df1.printSchema()
    df1.show()

    spark.stop()


  }

}
