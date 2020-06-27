package RddActionOperation

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * @author sober 2020-06-27-16:58
 *
 *         reduce(f: (T, T) => T)算子：通过函数f聚集RDD中的所有元素，先聚合分区内数据，再聚合分区间数据
 */
object reduceTest {
  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkSession.builder().master("local[2]").appName("reduceTest").getOrCreate().sparkContext

    val rdd1: RDD[Int] = sc.parallelize(1 to 10, 1)
    val res: Int = rdd1.reduce(_ + _)
    println(res)

    val rdd2: RDD[(String, Int)] = sc.parallelize(Array(("a", 1), ("a", 2), ("a", 3), ("b", 4), ("b", 5), ("b", 6)))
    val tuple: (String, Int) = rdd2.reduce((x, y) => {
      (x._1 + y._1, x._2 + y._2)
    })
    println(tuple)

    sc.stop()
  }

}
