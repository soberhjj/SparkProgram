package AccumulatorAndBroadcast

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * @author sober 2020-06-10-16:35
  */
object BroadcastTest {
  def main(args: Array[String]): Unit = {

    val sparkSession= SparkSession.builder().config("spark.broadcast.blockSize",4096).master("local[2]").getOrCreate()
    //对参数spark.broadcast.blockSize的解释：Driver将序列化后的对象（即广播变量）切分成许多小块，然后将每小块发送给executor。该参数就是设置分成的小块的大小。默认是4MB。

    val sc = sparkSession.sparkContext

    val array: Array[Int] = (0 until 1000000).toArray

    for (i<-1 to 3){
      println(s"第 $i 次使用广播变量")
      println("===================")

      val startTime = System.nanoTime()
      val barr1: Broadcast[Array[Int]] = sc.broadcast(array)
      val res: RDD[Int] = sc.parallelize(1 to 10).map(_=>barr1.value.length)
      res.collect().foreach(println(_))
      println("Iteration %d took %.0f milliseconds".format(i, (System.nanoTime - startTime) / 1E6))


    }
    sparkSession.stop()
  }

}
