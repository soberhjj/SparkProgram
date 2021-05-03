package RddTransformOperation.customsortby

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author huangJunJie 2021-05-03-11:14
 */
object CustomSortDemo1 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("CustomSortDemo1").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val rawdata = sc.parallelize(List("张三,21,北京", "李四,19,上海", "王五,20,福建"))
    val persons = rawdata.map(data => {
      val fields = data.split(",")
      Person1(fields(0), fields(1).toInt, fields(2))
    })

    val sorted = persons.sortBy(x => x)

    println(sorted.collect().toBuffer)
    sc.stop()
  }

}
