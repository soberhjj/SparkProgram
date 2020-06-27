package RddTransformOperation

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * @author sober 2020-06-24-15:15
 *
 *         groupBy算子：分组，按照传入函数的返回值进行分组。将相同的Key对应的值放入一个迭代器
 */
object groupByTest {
  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkSession.builder().master("local[2]").appName("groupByTest").getOrCreate().sparkContext

    val rdd1: RDD[Int] = sc.parallelize(List(1, 2, 3, 4))

    val rdd2: RDD[(Int, Iterable[Int])] = rdd1.groupBy(x => x % 2)

    rdd2.collect().foreach(data=>{
      println("=============")
      for (i<-data._2)
        print(i+"  ")
      println()
    })

    sc.stop()
  }

}
