package RddTransformOperation

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * @author sober 2020-06-26-9:33
 *         repartition算子：指定新的分区数，对原RDD中的数据打乱重组。
 *
 *         repartition与coalesce的区别：repartition其实就是调用了coalesce，只不过是将coalesce的第二个参数shuffle的值设为了true。
 *         所以repartition涉及shuffle，也就是数据打乱重组，而coalesce在默认情况下第二个参数shuffle的值是false，所以coalesce不涉及shuffle。
 *
 */
object repartitionTest {
  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkSession.builder().master("local[2]").appName("repartitionTest").getOrCreate().sparkContext

    val rdd1: RDD[Int] = sc.parallelize(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16), 4) //4个分区

    val rdd2: RDD[Int] = rdd1.repartition(2)

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
