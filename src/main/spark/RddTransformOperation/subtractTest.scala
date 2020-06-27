package RddTransformOperation

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

/**
 * @author sober 2020-06-26-10:10
 *
 *         ubtract(other: RDD[T])算子：去除两个RDD中相同的元素，保留源RDD中剩余的数据以新RDD返回
 */
object subtractTest {
  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkSession.builder().master("local[2]").appName("subtractTest").getOrCreate().sparkContext
    val rdd1 = sc.parallelize(List(1, 2, 3, 4, 5))
    val rdd2 = sc.parallelize(List(6, 7, 8, 4, 5))
    val rdd3 = rdd1.subtract(rdd2)

    println(rdd1.collect().mkString(","))
    println("===========")
    println(rdd2.collect().mkString(","))
    println("===========")
    println(rdd3.collect().mkString(","))
    println("===========")


    sc.stop()
  }

}
