package RddActionOperation

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * @author sober 2020-06-27-17:47
 *
 *         countByKey算子：针对(K,V)类型的RDD，计算该RDD中每种key的出现次数，计算结果以 Map[K, Int]的数据类型返回到Driver端
 */
object countByKeyTest {
  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkSession.builder().master("local[2]").appName("countByKeyTest").getOrCreate().sparkContext

    val rdd1: RDD[(Int, Int)] = sc.parallelize(List((1, 2), (1, 3), (1, 4), (2, 3), (2, 4), (3, 6), (4, 7), (4, 8)),2)
    //统计每种key的个数
    val resMap: collection.Map[Int, Long] = rdd1.countByKey()

    val iterator: Iterator[Int] = resMap.keysIterator
    for (i<-iterator){
      println((i,resMap.get(i)))
    }

    sc.stop()

  }

}
