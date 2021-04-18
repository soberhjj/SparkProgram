package SparkSQLOperation

import org.apache.spark.sql.SparkSession

/**
 * @author huangJunJie 2021-04-18-9:59
 *
 *         RDD关联case class，然后将RDD转成DataFrame
 */
object RDDRelevanceCaseClassToDF {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("RDDRelevanceCaseClassToDF")
      .master("local[2]")
      .getOrCreate()

    val sc = spark.sparkContext
    val lines = sc.textFile("src\\main\\spark\\SparkSQLOperation\\user.txt")

    val users = lines.map(line => {
      val fields = line.split(" ")
      val f1 = fields(0)
      val f2 = fields(1).toInt
      val f3 = fields(2)
      User(f1, f2, f3)
    })

    import spark.implicits._
    //注意调用toDF要导入隐式转换
    val df1 = users.toDF()

    df1.printSchema()
    df1.show()

    spark.stop()


  }

}

case class User(name: String, age: Int, address: String)
