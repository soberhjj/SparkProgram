package RddOperation

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * @author sober 2020-06-17-23:04
  *
  *         mapPartitionWithIndex算子：对RDD的每个分区（并且会获得该分区的分区号）执行指定操作
  *
  */
object mapPartitionWithIndexTest {
  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkSession.builder().master("local[2]").appName("mapPartitionWithIndexTest").getOrCreate().sparkContext

    val rdd1: RDD[Int] = sc.parallelize(1 to 10)

//    val rdd2: RDD[(Int, String)] = rdd1.mapPartitionsWithIndex { //使用模式匹配进行类型匹配
//      case (num, datas) => {
//        datas.map((_, "分区号：" + num))
//      }
//    }

    val rdd2: RDD[(Int, String)] = rdd1.mapPartitionsWithIndex((num,datas)=>datas.map((_,"分区号："+num))) //与上面注释部分效果一样

    rdd2.collect().foreach(println(_))

    sc.stop()
  }

}
