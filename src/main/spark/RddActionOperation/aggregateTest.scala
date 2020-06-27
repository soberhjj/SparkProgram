package RddActionOperation

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * @author sober 2020-06-27-17:17
 *
 *         aggregate(zeroValue: U)(seqOp: (U, T) => U, combOp: (U, U) => U)算子：
 *         作用：参数zeroValue表示的是初始值，aggregate会先通过seqOp函数对每个分区内的元素执行指定操作，然后再通过combOp函数
 *              对每个分区执行完seqOp函数后的结果数据 执行指定操作。 所以初始值
 *
 *         根据上述作用，可以分析得出 -> 初始值的作用次数为 “RDD的分区数+1”
 *
 */
object aggregateTest {
  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkSession.builder().master("local[2]").appName("aggregateTest").getOrCreate().sparkContext

    val rdd1: RDD[Int] = sc.parallelize(1 to 10, 2)

    val res1: Int = rdd1.aggregate(0)(_ + _, _ + _)
    val res2: Int = rdd1.aggregate(10)(_ + _, _ + _)

    println(res1)
    println(res2)

    sc.stop()
  }

}
