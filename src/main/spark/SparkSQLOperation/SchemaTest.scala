package SparkSQLOperation

import org.apache.spark.sql.{DataFrame, Row, SparkSession}

/**
 * @author sober 2020-07-19-21:21
 */
object SchemaTest {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[2]").appName("SchemaTest").getOrCreate()

    val df1: DataFrame = spark.read.option("multiLine", true).option("mode", "PERMISSIVE").json("TestData\\data1.json")

    val row: Row = df1.first()
    println(row)

    val newRow:Row=Row("putian","hjj","male")
    println(newRow(1))




  }

}
