package RddOperation

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * @author sober 2020-06-17-22:48
  *
  *         mapPartition算子：对RDD的每个分区执行指定操作。
 *          mapPartition算子每次处理一个分区的数据，这个分区的数据处理完毕后，原RDD中该分区的数据才能释放，可能导致OOM
  */
object mapPartitionTest {
  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkSession.builder().master("local[2]").appName("mapPartitionTest").getOrCreate().sparkContext

    val rdd1: RDD[Int] = sc.parallelize(1 to 10)

    val rdd2: RDD[Int] = rdd1.mapPartitions(datas=>{
      datas.map(_*2)  //注意这个map是scala中的map操作，而不是spark中的map算子
    })

    rdd2.collect().foreach(println(_))

    sc.stop()
  }

}
