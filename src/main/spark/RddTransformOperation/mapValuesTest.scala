package RddTransformOperation

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * @author sober 2020-06-27-14:15
 *
 *         mapValues(f: V => U)算子：对pairRDD中每个元素的value进行操作
 */
object mapValuesTest {
  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkSession.builder().master("local[2]").appName("mapValuesTest").getOrCreate().sparkContext

    val rdd1: RDD[(Int, String)] = sc.parallelize(Array((1, "a"), (2, "b"), (3, "c"), (4, "d")))
    val rdd2: RDD[(Int, String)] = rdd1.mapValues(_ + " haha") //对rdd1中的每个元素的value加上字符串"haha"

    rdd2.collect().foreach(println)

    sc.stop()
  }

}
