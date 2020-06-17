package RddOperation

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * @author sober 2020-06-17-22:43
  *
  *         map算子：对RDD中的每个元素执行指定操作
  */
object mapTest {
  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkSession.builder().master("local[2]").appName("mapTest").getOrCreate().sparkContext

    val rdd1: RDD[Int] = sc.parallelize(1 to 10)

    val rdd2: RDD[Int] = rdd1.map(_*2)  //将rdd1中的每个数乘2

    rdd2.collect().foreach(println(_))

    sc.stop()

  }

}
