package RddTransformOperation

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * @author sober 2020-06-27-14:01
 *
 *         sortByKey算子：作用在(K,V)的RDD上即pairRDD，该pairRDD中的元素的key必须实现Ordered接口。sortByKey算子返回一个
 *         按照key排序后的新的pairRDD
 */
object sortByKeyTest {
  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkSession.builder().master("local[2]").appName("sortByKeyTest").getOrCreate().sparkContext

    val rdd1: RDD[(Int, String)] = sc.parallelize(Array((3, "aa"), (4, "bb"), (2, "cc"), (5, "dd"), (1, "ee"), (6, "ff")))

    //按照key的正序进行排序。sortByKey算子默认情况下就是按照正序进行排序
    val rdd2: RDD[(Int, String)] = rdd1.sortByKey()
    //按照key的倒序进行排序
    val rdd3: RDD[(Int, String)] = rdd1.sortByKey(false)

    println("==========正序排列===========")
    rdd2.collect().foreach(println)
    println("==========倒序排列===========")
    rdd3.collect().foreach(println)

    sc.stop()

  }

}
