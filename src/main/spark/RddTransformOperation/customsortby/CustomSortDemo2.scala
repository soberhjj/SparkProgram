package RddTransformOperation.customsortby

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author huangJunJie 2021-05-03-11:14
 */
object CustomSortDemo2 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("CustomSortDemo1").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val rawdata = sc.parallelize(List("张三,21,北京", "李四,19,上海", "王五,20,福建"))
    val persons = rawdata.map(data => {
      val fields = data.split(",")
      Person2(fields(0), fields(1).toInt, fields(2))
    })

    //Person2没有定义大小比较规则，可以给sortBy一个Ordering类型的隐式值用于对Person2对象进行比较排序
    implicit val orderPerson=new Ordering[Person2] {
      override def compare(x: Person2, y: Person2): Int = {
        x.age.compareTo(y.age)
      }
    }
    val sorted = persons.sortBy(x => x)

    println(sorted.collect().toBuffer)
    sc.stop()
  }

}
