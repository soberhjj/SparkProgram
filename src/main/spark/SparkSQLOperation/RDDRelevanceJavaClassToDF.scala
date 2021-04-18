package SparkSQLOperation

import org.apache.spark.sql.SparkSession

import scala.beans.BeanProperty

/**
 * @author huangJunJie 2021-04-18-10:20
 *
 *         RDD关联 java class，然后将RDD转成DataFrame
 *         和 RDD关联 scala class，然后将RDD转成DataFrame 是一样的 只不过是把scala class换成java class
 */
object RDDRelevanceJavaClassToDF {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("RDDRelevanceJavaClassToDF")
      .master("local[2]")
      .getOrCreate()

    val sc = spark.sparkContext
    val lines = sc.textFile("src\\main\\spark\\SparkSQLOperation\\user.txt")

    val teachers = lines.map(line => {
      val fields = line.split(" ")
      val f1 = fields(0)
      val f2 = fields(1).toInt
      val f3 = fields(2)
      new Teacher(f1, f2, f3)
    })

    val df1 = spark.createDataFrame(teachers, classOf[Teacher])

    df1.printSchema()
    df1.show()

    spark.stop()
  }

}


