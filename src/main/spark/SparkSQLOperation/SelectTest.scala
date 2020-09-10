package SparkSQLOperation

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * @author sober 2020-07-19-22:15
 */
object SelectTest {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[2]").appName("SchemaTest").getOrCreate()
    val df1: DataFrame = spark.read.option("multiLine", true).option("mode", "PERMISSIVE").json("TestData\\flight-data.json")
    df1.select("DEST_COUNTRY_NAME").show(10)
    df1.select("ORIGIN_COUNTRY_NAME", "DEST_COUNTRY_NAME").show(10)

    //利用selectExpr构建复杂表达式来创建新的DataFrame。如下在df1中增加一个新列withinCountry，该列描述了destination和origin是否相同
    val df2: DataFrame = df1.selectExpr("*", "(DEST_COUNTRY_NAME==ORIGIN_COUNTRY_NAME) as withinCountry")
    df2.show(3)

    //使用select语句，我们还可以利用系统预定义好的聚合函数来指定在整个DataFrame上的聚合操作
    val df3: DataFrame = df2.selectExpr("avg(count)", "count(distinct(DEST_COUNTRY_NAME))")
    df3.show(3)


  }

}
