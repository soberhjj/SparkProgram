package collection

import scala.collection.mutable.ArrayBuffer

/**
  * @author sober 2020-06-10-8:52
  *
  *         ArrayBuffer是长度可变的数组，类似java的ArrayList
  */
object ArrayBufferTest {
  def main(args: Array[String]): Unit = {
    val arrayBuffer: ArrayBuffer[Any] = ArrayBuffer[Any](1,2,3,"hello",6,7)

    for (i<-arrayBuffer)
      println(i)

    println("---------------")

    for (i<-0 until  arrayBuffer.size)
      println(arrayBuffer(i))

    println("---------------")

    //使用append追加数据
    arrayBuffer.append("world")
    arrayBuffer.append(12)
    for (i<-arrayBuffer)
      println(i)

    println("---------------")

    //删除元素
    arrayBuffer.remove(arrayBuffer.size-1)
    for (i<-arrayBuffer)
      println(i)

  }

}
