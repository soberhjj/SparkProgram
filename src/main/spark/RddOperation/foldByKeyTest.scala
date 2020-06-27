package RddOperation

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * @author sober 2020-06-25-14:04
 *
 *         foldByKey(zeroValue: V)(func: (V, V) => V)算子：
 *         foldByKey算子是aggregateByKey算子的简化操作，当aggregateByKey中参数seqOp和参数combOp一致时，
 *         那么此时的aggregateByKey就是foldByKey。
 *
 */
object foldByKeyTest {
  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkSession.builder().master("local[2]").appName("foldByKeyTest").getOrCreate().sparkContext

    val rdd1: RDD[(Char, Int)] = sc.parallelize(Array(('a', 3), ('a', 2), ('c', 4), ('b', 4), ('c', 6), ('c', 8)), 2)
    val arr1: Array[Array[(Char, Int)]] = rdd1.glom().collect()
    arr1.foreach(data => println(data.mkString(",")))


    val rdd2: RDD[(Char, Int)] =rdd1.foldByKey(0)(_+_)
    val arr2: Array[Array[(Char, Int)]] = rdd2.glom().collect()
    arr2.foreach(data => println(data.mkString(",")))

    sc.stop()
  }

}
