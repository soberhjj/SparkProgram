package collectionTest

import scala.util.Random

/**
  * @author sober 2020-06-10-8:15
  *
  *         scala中的数组就是对java中的数组进行了一层包装，其实质就是java数组
  */
object ArrayTest {
  def main(args: Array[String]): Unit = {
    //声明数组但不直接赋值
    val arr1: Array[Int] = new Array[Int](10) //中括号中指定数组元素的类型，小括号指定数组大小

    println(arr1.length)
    for (i <- 0 to 9)
      arr1(i) = i //scala中数组元素的赋值和访问都是使用小括号定位

    for (i <- arr1)
      println(i)

    println("--------------")

    //声明数组并直接赋值
    val arr2: Array[Int] = Array(1, 2, 3, 4, 5)
    for (i <- arr2)
      println(i)

    println("--------------")


    /*
       二维数组
    */
    val arr3: Array[Array[Double]] = Array.ofDim[Double](3, 4) //该二维数组由3个一维数组组成，每个一维数组中含4个元素

    //进行赋值，随机Double类型的数（保留两位小数）
    for (i <- 0 until arr3.length)
      for (j <- 0 until arr3(i).length)
        arr3(i)(j) = String.format("%.2f",Double.box(new Random().nextDouble())).toDouble

    //遍历
    for (i <- arr3) {
      for (j <- i) {
        print(j + "\t\t")
      }
      println()
    }
  }
}
