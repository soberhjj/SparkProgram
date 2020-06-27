package RddActionOperation

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * @author sober 2020-06-27-17:17
 *
 *         fold(zeroValue: T)(op: (T, T) => T)算子：fold算子是aggregate算子的简化操作，
 *         当aggregate算子中的参数seqOp和参数combOp一致时，那么此时的aggregate就是fold。
 *
 *
 */
object foldTest {
  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkSession.builder().master("local[2]").appName("aggregateTest").getOrCreate().sparkContext

    val rdd1: RDD[Int] = sc.parallelize(1 to 10, 2)

    val res1: Int = rdd1.fold(0)(_ + _)
    val res2: Int = rdd1.fold(10)(_ + _)

    println(res1)
    println(res2)

    sc.stop()
  }

}
