package RddTransformOperation

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * @author sober 2020-06-17-23:31
  *
  *         flatMap算子：对RDD中的每个元素执行指定操作，然后再对每个元素的操作结果进行扁平化
  */
object flatMapTest {
  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkSession.builder().master("local[2]").appName("flatMapTest").getOrCreate().sparkContext

    val arr: Array[List[Int]] = Array(List(1,2,3),List(4,5,6),List(7,8,9))

    val rdd1: RDD[List[Int]] = sc.parallelize(arr)

    val rdd2: RDD[Int] = rdd1.flatMap(datas=>datas)

    rdd2.collect().foreach(println)

    sc.stop()


  }

}
