package SparkSQLOperation

import org.apache.spark.sql.SparkSession

/**
 * @author huangJunJie 2021-04-18-11:16
 *
 *         读取json 创建DataFrame 再将DataFrame输出为json
 */
object ReadJsonToCreateDF {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("ReadJsonToCreateDF")
      .master("local[2]")
      .getOrCreate()

    //读取json数据来创建DataFrame
    val df = spark.read.json("src\\main\\spark\\SparkSQLOperation\\user.json")
    df.printSchema()
    df.show()

    //将DataFrame输出为json，若文件已存在则覆盖
    df.write.mode("overwrite").json("src\\main\\spark\\SparkSQLOperation\\out_user.json")

    /**
     * 读和写还有另一种写法，如下。其实上面那种写法在底层也是用的如下这种写法。
     */
    //读
    val df1 = spark.read.format("json").load("src\\main\\spark\\SparkSQLOperation\\user.json")
    //写
    df1.write.mode("overwrite").format("json").save("src\\main\\spark\\SparkSQLOperation\\out_user.json")

    spark.stop()
  }

}
