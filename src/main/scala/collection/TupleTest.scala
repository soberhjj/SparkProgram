package collection

/**
  * @author sober 2020-06-10-12:01
  *
  *         元组也是可以理解为一个容器，它可以存放各种相同或不同数据类型的数据。
  *         简而言之，元组就是将多个无关的数据封装为一个整体。
  */
object TupleTest {
  def main(args: Array[String]): Unit = {
    //创建元组
    val tuple1 = (1,3,5,"hello",2.8)

    //访问元组。可采用下划线+顺序号的方式，如_1 。也可通过索引（productElement）访问
    println(tuple1._1)
    println(tuple1.productElement(0))

    println("----------")

    //遍历元组，注意元组的遍历需要使用迭代器
    for(i<-tuple1.productIterator)
      println(i)

  }

}
