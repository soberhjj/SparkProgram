package RddTransformOperation

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * @author sober 2020-06-26-9:54
 *         sortBy(f: (T) => K, ascending: Boolean = true,numPartitions: Int = this.partitions.length)算子：
 *         使用f先对数据进行处理，再按照处理后的数据进行排序，默认为正序
 *
 */
object sortByTest {
  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkSession.builder().master("local[2]").appName("sortByTest").getOrCreate().sparkContext

    val rdd1: RDD[Char] = sc.parallelize(List('B', 'A', 'G', 'R','D','E','K','L'))
    val rdd2: RDD[Char] = rdd1.sortBy(x => x, false) //降序排列

    val afterSort: Array[Char] = rdd2.collect()
    println(afterSort.mkString(","))

    sc.stop()
  }

}
