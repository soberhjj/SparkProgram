package RddTransformOperation

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * @author sober 2020-06-25-14:04
 *
 *         coalesce算子：缩小分区数量。本质是分区合并，并没有打乱数据。
 */
object coalesceTest {
  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkSession.builder().master("local[2]").appName("coalesceTest").getOrCreate().sparkContext

    val rdd1 = sc.parallelize(1 to 16, 4) //4个分区

    val rdd2 = rdd1.coalesce(3)

    val arr1: Array[Array[Int]] = rdd1.glom().collect()

    val arr2: Array[Array[Int]] = rdd2.glom().collect()

    arr1.foreach(data => {
      println(data.mkString(","))
    })

    println("===============")

    arr2.foreach(data => {
      println(data.mkString(","))
    })

    sc.stop()
  }

}
