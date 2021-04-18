package SparkSQLOperation

import org.apache.spark.sql.SparkSession

/**
 * @author huangJunJie 2021-04-18-11:06
 *
 *         RDD 关联scala中的元组 ，再指定每个字段的名称 ，将RDD转成DataFrame
 */
object RDDRelevanceTupleToDF {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("RDDRelevanceTupleToDF")
      .master("local[2]")
      .getOrCreate()

    val sc = spark.sparkContext
    val lines = sc.textFile("src\\main\\spark\\SparkSQLOperation\\user.txt")

    //关联元组
    val data = lines.map(line => {
      val fields = line.split(" ")
      val f1 = fields(0)
      val f2 = fields(1).toInt
      val f3 = fields(2)
      (f1, f2, f3)
    })

    import spark.implicits._
    val df = data.toDF("name", "age", "address")

    df.printSchema()
    df.show()
    spark.stop()
  }


}
