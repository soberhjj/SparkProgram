package RddOperation

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * @author sober 2020-06-26-16:00
 *
 *         reduceByKey(func: (V, V) => V)算子：将key相同的value根据func所定义的操作进行聚合
 *         注意 reduceByKey返回的RDD的类型与源RDD的类型是一致的，比如源RDD的类型是RDD[(Int, Char)]，那么reduceByKey返回的RDD的类型也必须是RDD[(Int, Char)]
 *
 *
 *         reduceByKey在shuffle之前有combine(预聚合)操作，而groupByKey直接进行shuffle
 */
object reduceByKeyTest {
  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkSession.builder().master("local[2]").appName("groupByKeyTest").getOrCreate().sparkContext

    val rdd1: RDD[(Int, Char)] = sc.parallelize(Array((1, 'a'), (1, 'b'), (2, 'c'), (3, 'd'), (4, 'e'), (3, 'f'), (4, 'g'), (2, 'h')))
    val rdd2: RDD[(Int, Char)] = rdd1.reduceByKey((x, y) => {
      (x+y).toChar
    })
    val tuples: Array[(Int, Char)] = rdd2.collect()
    tuples.foreach(data=>{
      println("=================")
      println(data._1+" : " +data._2)
    })

    sc.stop()

  }

}
