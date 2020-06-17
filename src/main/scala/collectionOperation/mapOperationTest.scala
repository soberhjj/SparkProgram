package collectionOperation

/**
  * @author sober 2020-06-11-14:05
  *
  *         map操作的本质：
  *         对调用map的那个集合中的元素依次遍历，
  *         每遍历一个元素就对该元素执行传入map方法的参数函数function以得到一个新的值，
  *         最终将所有新的值放入一个新的集合并返回
  *
  *         实现：将List(1,2,3,4,5,6,7,8,9)中的数值全部乘2
  */
object MapMethodTest {
  def main(args: Array[String]): Unit = {

    val list = List(1, 2, 3, 4, 5, 6, 7, 8, 9)

    //传统方式
    var newlist = List[Int]()
    for (i <- list) {
      newlist = newlist.:+(i * 2)
    }
    println(newlist)
    println("====================")

    //使用map操作
    val newlistBymap: List[Int] = list.map(2 * _)
    println(newlistBymap)
    println("====================")

    //传统方式的优点是处理方式比较直接好理解，缺点是不够简洁高效，且没有体现出函数式编程特点（集合=>函数=>新集合=>函数...），不利于处理复杂的数据处理业务


    //测试模拟实现的map操作
    val myList = MyList()
    println(myList.map(x=>2*x))

  }

}

//深刻理解map操作的机制-模拟实现
class MyList {
  val list1 = List(1, 2, 3, 4, 5)
  var list2 = List[Int]() //注意当定义没有初始值的类字段时，要给出具体类型，因为无法类型推断

  def map(f:Int=>Int):List[Int]={
    for (i<-list1){
      list2=list2 :+ f(i)
    }
    list2
  }
}
object MyList{
  def apply(): MyList = new MyList()
}
