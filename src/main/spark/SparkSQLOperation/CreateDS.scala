package SparkSQLOperation

import org.apache.spark.sql.SparkSession

/**
 * @author huangJunJie 2021-05-03-15:57
 *
 *         创建Dataset的方式
 *         1.RDD调用toDS()将RDD转换为Dataset
 *         2.DataFrame调用as[T]转为Dataset
 *         3.通过 SparkSession.createDataset() 直接创建
 */
object CreateDS {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("CreateDS")
      .master("local[2]")
      .getOrCreate()

    val sc = spark.sparkContext
    val peoples = sc.parallelize(List(People("张三", 18, "北京"), People("李四", 19, "上海")))

    import spark.implicits._
    //RDD调用toDS()转为Dataset
    val ds = peoples.toDS()
    ds.show()

    //DataFrame调用as[T]转为Dataset
    val df = peoples.toDF()
    val ds1 = df.as[People]
    ds1.show()

    //通过 SparkSession.createDataset() 直接创建
    val ds2 = spark.createDataset(List(People("张三", 18, "北京"), People("李四", 19, "上海")))
    ds2.show()







    spark.close()
  }

}
