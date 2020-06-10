package collection

import scala.collection.mutable.ListBuffer

/**
  * @author sober 2020-06-10-15:04
  *
  *         ListBuffer是Seq特质的子特质Buffer下的具体类。
  *         ListBuffer是scala.collection.mutable.ListBuffer，即ListBuffer是可变的  *
  */
object ListBufferTest {
  def main(args: Array[String]): Unit = {
    //创建ListBuffer对象
    val listBuffer = ListBuffer("hello","world","good","hi")

    //访问List中的元素
    println(listBuffer(0))

    //向ListBuffer中加入新元素
    listBuffer.append("yes","no")
    println(listBuffer)

    listBuffer.+=("very")
    println(listBuffer)

    //删除某个元素
    listBuffer.remove(listBuffer.length-1)//删除最后一个元素
    println(listBuffer)

    //清空
    listBuffer.clear()
    println(listBuffer)






  }

}
