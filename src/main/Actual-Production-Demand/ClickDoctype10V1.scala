import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Row, SparkSession}

import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks

/**
 * @author huangJunJie 2021-10-04-15:51
 */
object ClickDoctype10V1 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local[2]")
      .appName("example_one")
      .enableHiveSupport()
      .getOrCreate()

    val res = spark.sparkContext.longAccumulator("res")

    val rdd: RDD[Row] = spark.sql("select * from aaa").rdd
    val set: Set[String] = rdd.map(row => {
      val file1 = row.get(0).toString
      val file2 = row.get(1).toString
      file1 + ":" + file2
    }).collect().toSet


    val full = spark.sparkContext.broadcast(set)

    val origin = spark.sql("select * from bbb").rdd
    val value = origin.mapPartitions(partition => {
      val fulldata = full.value
      partition.map(row => {
        if (row.get(0).toString.startsWith("0;0")) {
          val strings1 = row.get(0).toString.split(";")
          val strings2 = row.get(1).toString.split(";")
          val loop = new Breaks
          loop.breakable {
            for (i <- 0 to strings1.length - 1) {
              val line = strings1(i) + ":" + strings2(i)
              if (!fulldata.contains(line)) {
                res.add(1L)
                loop.break
              }
            }
          }
        } else {
          val strings1 = row.get(0).toString.split(",")
          val strings2 = row.get(1).toString.split(";")
          val loop = new Breaks
          loop.breakable {
            for (i <- 0 to 9) {
              val line = strings1(i) + ":" + strings2(i)
              if (!fulldata.contains(line)) {
                res.add(1L)
                loop.break
              }
            }
          }
        }
      })
    })

    value.repartition(1).collect()

    val savedata = ListBuffer[Long]()
    savedata += res.value
    val resRdd = spark.sparkContext.parallelize(savedata,1)
    resRdd.saveAsTextFile("C:\\Users\\sober\\Desktop\\res.txt")
  }
}
