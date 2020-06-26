package RddOperation

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * @author sober 2020-06-26-10:05
 *
 *         union(other: RDD[T])算子：对源RDD和参数RDD求并集后返回一个新的RDD
 */
object unionTest {
  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkSession.builder().master("local[2]").appName("unionTest").getOrCreate().sparkContext

    val rdd1 = sc.parallelize(1 to 5)

    val rdd2 = sc.parallelize(5 to 10)

    val rdd3 = rdd1.union(rdd2)

    println(rdd1.collect().mkString(","))
    println("===========")
    println(rdd2.collect().mkString(","))
    println("===========")
    println(rdd3.collect().mkString(","))
    println("===========")

    sc.stop()
  }

}
