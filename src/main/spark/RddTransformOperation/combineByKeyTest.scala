package RddTransformOperation

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * @author sober 2020-06-25-14:04
 *
 *         combineByKeyTest(createCombiner: V => C,mergeValue: (C, V) => C,mergeCombiners: (C, C) => C,numPartitions: Int)算子：
 *         参数createCombiner函数对分区内的所有的key其对应的value执行指定操作
 *         执行完createCombiner函数后，参数mergeValue函数对分区内相同的key对应的value值执行指定操作
 *         参数mergeCombiners函数对各个分区相同的key对应的value做聚合 *
 *
 */
object combineByKeyTest {
  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkSession.builder().master("local[2]").appName("combineByKeyTest").getOrCreate().sparkContext

    val rdd1: RDD[(Char, Int)] = sc.parallelize(Array(('a', 18), ('a', 10), ('c', 24), ('b', 34), ('c', 26), ('c', 58)), 2)

    //需求：计算rdd1中每种key的value值的平均数（思路：计算每种key的value值总和以及该key出现的次数，最后用总和除以次数即可）
    val rdd2: RDD[(Char, (Int, Int))] = rdd1.combineByKey((_, 1), (acc: (Int, Int), v) => (acc._1 + v, acc._2 + 1), (acc1: (Int, Int), acc2: (Int, Int)) => (acc1._1 + acc2._1, acc1._2 + acc2._2))

    val rdd3: RDD[(Char, Int)] = rdd2.map(data => {
      val average: Int = (data._2._1) / (data._2._2)
      (data._1, average)
    })

    rdd3.collect().foreach(println)

    sc.stop()
  }

}
