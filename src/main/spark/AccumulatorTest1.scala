import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * @author sober 2020-06-08-22:27
  */
object AccumulatorTest1 {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().master("local[2]").appName("AccumulatorTest1").getOrCreate()
    val sc = sparkSession.sparkContext

    //创建一个元素为Int类型的RDD
    var rdd1: RDD[Int] = sc.parallelize(List(1, 2, 3, 4, 5, 6), 2)

    //使用RDD的算子来计算该RDD中的数值和
    var res: Int = rdd1.reduce(_ + _)
    println(res)

    //使用累加器来计算该RDD中的数值和
    val  acc= sc.longAccumulator
    rdd1.foreach(x => acc.add(x))
    println(acc.value)

    sparkSession.stop()
  }

}
