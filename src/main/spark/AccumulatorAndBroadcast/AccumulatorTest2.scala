package AccumulatorAndBroadcast

import java.util

import org.apache.spark.sql.SparkSession
import org.apache.spark.util.AccumulatorV2

/**
  * @author sober 2020-06-09-19:23
  */
object AccumulatorTest2{
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().master("local[2]").appName("AccumulatorTest2").getOrCreate()
    val sc = sparkSession.sparkContext

    //创建自定义累加器对象
    val myAcc = new MyAccumulator
    //创建完自定义累加器对象还需要将其注册
    sc.register(myAcc)

    //创建元素类型为字符串类型的RDD
//    val strings =
//      """The happiest of people don't necessarily
//have the best of everything;
//they just make the most of everything
//that comes along their way.""".split(" ")
    val rdd = sc.parallelize(List("hello","world","how","why","who","good","cool","handsome"))

    rdd.foreach(x=>myAcc.add(x))

    println(myAcc.value)

    sparkSession.stop()
  }
}

//自定义累加器(继承AccumulatorV2并实现其抽象方法)
class MyAccumulator extends AccumulatorV2[String, util.ArrayList[String]] {
  val list = new util.ArrayList[String]()

  //判断当前的累加器是否为初始化状态
  override def isZero: Boolean = {
    list.isEmpty
  }

  //复制累加器
  override def copy(): AccumulatorV2[String, util.ArrayList[String]] = {
    new MyAccumulator
  }

  //重置累加器
  override def reset(): Unit = {
    list.clear()
  }

  //向累加器中增加数据
  override def add(v: String): Unit = {
    if (v.contains("h")) //如果传递的参数字符串中包含字母h,则加入到list中
      list.add(v)
  }

  //合并两个同类累加器
  override def merge(other: AccumulatorV2[String, util.ArrayList[String]]): Unit = {
    list.addAll(other.value)
  }

  //获取累加器的结果
  override def value: util.ArrayList[String] = {
    list
  }
}

