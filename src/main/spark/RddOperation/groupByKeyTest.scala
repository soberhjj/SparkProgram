package RddOperation

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * @author sober 2020-06-26-15:51
 *         groupByKey算子：把同一个key的数据放在同一个分组里。返回的是RDD的类型是RDD[(K, Iterable[V])]
 */
object groupByKeyTest {
  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkSession.builder().master("local[2]").appName("groupByKeyTest").getOrCreate().sparkContext

    val rdd1: RDD[(Int, Char)] = sc.parallelize(Array((1, 'a'), (1, 'b'), (2, 'c'), (3, 'd'), (4, 'e'), (3, 'f'), (4, 'g'), (2, 'h')))
    val rdd2: RDD[(Int, Iterable[Char])] = rdd1.groupByKey()

    val tuples: Array[(Int, Iterable[Char])] = rdd2.collect()
    tuples.foreach(data=>{
      println("=================")
      println(data._1+" : " +data._2.mkString(","))
    })

    sc.stop()
  }

}
