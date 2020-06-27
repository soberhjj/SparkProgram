package RddTransformOperation

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * @author sober 2020-06-25-10:17
 *
 *         sample(withReplacement,fraction,seed)算子：以指定的随机种子随机抽样出数量为fraction的数量，
 *         fraction是一个double类型的参数，值在0-1之间，例如0.3表示抽出30%
 *         withReplacement表示抽出的数据是否放回，true为有放回的抽样，false为无放回的抽样
 *         seed用于指定随机数生成器种子
 *
 *         实现：通过抽样取出一部分样本，在对样本做wordCount并排序，最后取出次数出现最多的key
 */
object sampleTest {
  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkSession.builder().master("local[2]").appName("sampleTest").getOrCreate().sparkContext

    val rdd1: RDD[String] = sc.parallelize(List("A", "B", "C", "D", "E", "F", "G", "H", "A", "B", "C", "D", "E", "F", "G", "H",
      "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "B", "B", "B", "B", "B", "B", "B",
      "B", "D", "D", "D", "D", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A"), 2)

    val rdd2: RDD[(String, Int)] = rdd1.map((_, 1))

    val rdd3: RDD[(String, Int)] = rdd2.sample(false, 0.4)

    val rdd4: RDD[(String, Int)] = rdd3.reduceByKey(_ + _)

    val rdd5: RDD[(Int, String)] = rdd4.map(x => (x._2, x._1))

    val rdd6: RDD[(Int, String)] = rdd5.sortBy(x => (x._1), false)

    val tuples: Array[(Int, String)] = rdd6.take(3)

    tuples.foreach(data=>{
      println(data._1 + " " +data._2)
    })

    sc.stop()
  }

}
