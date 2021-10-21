import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Row, SparkSession}

/**
 * @author huangJunJie 2021-10-21-14:45
 */
object ClickDoctype10V2 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local[2]")
      .appName("example_two")
      .enableHiveSupport()
      .getOrCreate()


    val rdd1 = spark.sql("select * from bbb").rdd
    val rdd2: RDD[String] = rdd1.mapPartitions(partition => {
      partition.map(row => {
        if (row.get(0).toString.startsWith("0;0")) {
          val strings1 = row.get(0).toString.split(";")
          val strings2 = row.get(1).toString.split(";")
          val buffer = new StringBuffer()
          for (i <- 0 to strings1.length - 1) {
            val line: String = strings1(i) + ":" + strings2(i) + ","
            buffer.append(line)
          }
          buffer.subSequence(0, buffer.length() - 1).toString
        } else {
          val strings1 = row.get(0).toString.split(",")
          val strings2 = row.get(1).toString.split(";")
          val buffer = new StringBuffer()
          for (i <- 0 to strings1.length - 1) {
            val line: String = strings1(i) + ":" + strings2(i) + ","
            buffer.append(line)
          }
          buffer.subSequence(0, buffer.length() - 1).toString
        }
      })
    })

    import spark.implicits._
    val df1 = rdd2.toDF()
    //    df1.show(false)

    import org.apache.spark.sql.functions._
    val df2 = df1.withColumn("splitCol", split(col("value"), ",")).select(
      col("splitCol").getItem(0).as("col0"),
      col("splitCol").getItem(1).as("col1"),
      col("splitCol").getItem(2).as("col2"),
      col("splitCol").getItem(3).as("col3"),
      col("splitCol").getItem(4).as("col4"),
      col("splitCol").getItem(5).as("col5"),
      col("splitCol").getItem(6).as("col6"),
      col("splitCol").getItem(7).as("col7"),
      col("splitCol").getItem(8).as("col8"),
      col("splitCol").getItem(9).as("col9")
    ).drop("splitCol").drop("value")

    //    df2.show(false)

    df2.createOrReplaceTempView("waitDeal")
//    spark.sql("select * from waitDeal limit 10").show()

//    spark.sql("select count(*) from waitDeal").show()


    val rdd3: RDD[Row] = spark.sql("select * from aaa").rdd
    val rdd4: RDD[String] = rdd3.map(row => {
      val file1 = row.get(0).toString
      val file2 = row.get(1).toString
      file1 + ":" + file2
    })

    val df3 = rdd4.toDF()
//    df3.show(false)

    df3.createOrReplaceTempView("full")

//    spark.sql("select count(*) from full").show()


    spark.sql("SELECT count(*) FROM\n(SELECT h.* FROM\n(SELECT g.* FROM\n(SELECT f.* FROM\n(SELECT e.* FROM\n(SELECT d.* FROM\n(SELECT c.* FROM\n(SELECT b.* FROM\n(SELECT a.* FROM\n(SELECT waitDeal.* FROM waitDeal \nJOIN full ON waitDeal.col0=full.value) a\nJOIN full ON a.col1=full.value) b\nJOIN full ON b.col2=full.value) c\nJOIN full ON c.col3=full.value) d\nJOIN full ON d.col4=full.value) e\nJOIN full ON e.col5=full.value) f\nJOIN full ON f.col6=full.value) g\nJOIN full ON g.col7=full.value) h\nJOIN full ON h.col8=full.value) i\nJOIN full ON i.col9=full.value")
  }
}
