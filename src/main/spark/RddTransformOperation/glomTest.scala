package RddTransformOperation

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * @author sober 2020-06-24-11:59
 *
 *         glom算子：将每一个分区形成一个数组，形成新的RDD的类型是RDD[Array[T]]
 */
object glomTest {
  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkSession.builder().master("local[2]").appName("glomTest").getOrCreate().sparkContext

    val rdd1: RDD[Int] = sc.parallelize(1 to 16, 4)

    val rdd2: RDD[Array[Int]] = rdd1.glom()

    rdd2.collect().foreach(arr=>{
      println("==========")
      for (data <- arr)
        print(data+"  ")
      println()
    })

    sc.stop()
  }

}
