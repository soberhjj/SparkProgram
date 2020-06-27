package RddTransformOperation

import org.apache.spark.{HashPartitioner, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * @author sober 2020-06-26-10:22
 *         partitionBy(partitioner: Partitioner)：根据指定的分区器进行分区
 */
object partitionByTest {
  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkSession.builder().master("local[2]").appName("partitionByTest").getOrCreate().sparkContext

    val rdd1: RDD[(Int, Char)] = sc.parallelize(Array((1, 'a'), (2, 'b'), (3, 'c'), (4, 'd'), (5, 'e'), (6, 'f')),3)

    val rdd2 = rdd1.partitionBy(new HashPartitioner(2))

    val arr: Array[Array[(Int, Char)]] = rdd2.glom().collect()
    arr.foreach(data=>println(data.mkString(",")))

    sc.stop()

  }

}
