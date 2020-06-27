package RddTransformOperation

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * @author sober 2020-06-27-14:29
 *
 *         cogroup(other: RDD[(K, W)])算子：在类型为(K,V)的RDD上调用，参数类型为(K, W)，cogroup后返回新的RDD的类型为(K,(Iterable[V], Iterable[W]))
 */
object cogroupTest {
  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkSession.builder().master("local[2]").appName("cogroupTest").getOrCreate().sparkContext

    //创建两个pairRDD，并将两个pairRDD中key相同的数据聚合到一个迭代器
    val rdd1: RDD[(Int, String)] = sc.parallelize(Array((1, "a"), (2, "b"), (3, "c"), (4, "d"),(1, "e"), (2, "f"), (3, "g"), (4, "h")))
    val rdd2: RDD[(Int, Int)] = sc.parallelize(Array((1, 4), (2, 5), (3, 6), (4, 7),(1, 8), (2, 9), (3, 10), (4, 11)))

    val rdd3: RDD[(Int, (Iterable[String], Iterable[Int]))] = rdd1.cogroup(rdd2)
    rdd3.collect().foreach(println)

    sc.stop()
  }
}
