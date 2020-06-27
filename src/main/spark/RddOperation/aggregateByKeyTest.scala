package RddOperation

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * @author sober 2020-06-25-14:04
 *
 *         aggregateByKey(zeroValue: U)(seqOp: (U, V) => U,combOp: (U, U) => U)算子：
 *         参数zeroValue表示的是初始值，参数seqOp表示的是分区内运算，参数combOp表示的分区间运算
 *
 */
object aggregateByKeyTest {
  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkSession.builder().master("local[2]").appName("aggregateByKeyTest").getOrCreate().sparkContext

    val rdd1: RDD[(Char, Int)] = sc.parallelize(Array(('a', 3), ('a', 2), ('c', 4), ('b', 4), ('c', 6), ('c', 8)), 2)
    val arr1: Array[Array[(Char, Int)]] = rdd1.glom().collect()
    arr1.foreach(data => println(data.mkString(",")))

    //取出每个分区内相同key对应的最大值，然后将取出的这些值根据key进行相加（分区间）
    val rdd2: RDD[(Char, Int)] = rdd1.aggregateByKey(Int.MinValue)(math.max(_, _), _ + _)
    val arr2: Array[Array[(Char, Int)]] = rdd2.glom().collect()
    arr2.foreach(data => println(data.mkString(",")))

    sc.stop()
  }

}
