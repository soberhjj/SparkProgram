package SparkSQLOperation

import org.apache.spark.sql.SparkSession

/**
 * @author huangJunJie 2021-04-17-16:49
 */
object SqlStyleAndDSLStyle {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("SqlStyleAndDSLStyle")
      .master("local[2]")
      .getOrCreate() //SparkSession是单例的，如果当前应用中已有了SparkSession实例则直接拿过来用，如果没有则创建一个。

    /**
     * Sql风格的SparkSql编程（以WordCount为例）
     */
    val lines = spark.read.textFile("src\\main\\spark\\SparkSQLOperation\\words.txt")

    //整理数据（切分压平）
    //调用DataFrame、DataSet的一些算子时（如下面的flatMap算子）需要导入隐式转换
    import spark.implicits._
    val words = lines.flatMap(_.split(" "))

    //注册视图
    words.createTempView("words")
    //用SparkSession的sql方法执行sql语句，注意sql方法是一个transform算子，是懒执行的
    val res = spark.sql("select value word , count(*) word_count from words group by value")
    res.show()




    /*
     * DSL风格的SparkSql编程（以WordCount为例）
     */
    val lines1 = spark.read.textFile("src\\main\\spark\\SparkSQLOperation\\words.txt")
    //整理数据（切分压平）
    //调用DataFrame、DataSet的一些算子时（如下面的flatMap算子）需要导入隐式转换
    import spark.implicits._
    val words1 = lines1.flatMap(_.split(" "))
    val df = words1.groupBy($"value" as "word").count().withColumnRenamed("count","word_count")
    df.show()

    //要进行分组聚合，还可以如下这么进行（即调用agg算子，给该算子传入聚合函数）
    //调用agg算子，要传入聚合函数，要使用聚合函数前一定要先导入聚合函数
    import org.apache.spark.sql.functions._
    val df1 = words1.groupBy($"value" as "word").agg(count("*") as "word_count")  //注意这里的agg()是算子，而agg()里的参数count()是聚合函数，这里的count()和上面的count()是不一样的，上面的count()是算子，这里的count()是聚合函数
    df1.show()

    spark.stop()
  }

}
