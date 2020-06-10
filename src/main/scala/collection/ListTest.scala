package collection

/**
  * @author sober 2020-06-10-12:49
  *
  *          java中的List是一个接口，而scala中的List是个具体类，该类是Seq特质的子特质LinearSeq下的类。
  *          List是scala.collection.immutable.List，即List是不可变的，如果要使用可变的List，则使用ListBuffer。
  */
object ListTest {
  def main(args: Array[String]): Unit = {
    //创建List对象
    val list = List("hello","world","good")

    //创建Nil对象，Nil表示空的List
    val nil = Nil

    //访问List中的元素
    println(list(1)) //访问第2个元素
    println(list.head) //访问List的头节点

    println("--------------")

    //往List中加入元素。注意List是不可变的，往某个List中加入新元素，那么会返回一个包含了原List和这个新元素的新的List，原List是不变的
    val list1 = list.+:("new head") //方法 +: 是在链表的表头加上新元素
    val list2 = list.:+("new tail") //方法 :+ 是在链表的表尾加上新元素
    println(list)
    println(list1)
    println(list2)



  }

}
