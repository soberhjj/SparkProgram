package advance_function_program

/**
 * @author huangJunJie 2021-01-30-11:43
 *
 *         需求：给出一个集合val list=List(1,2,3,4,"abc")，将集合list中的所有数字加1，非数字忽略，结果放入一个新的集合，新集合应该是（2，3，4，5）
 *
 *         思路: 通过 偏函数 来实现
 *
 *         PartialFunction1中的实现方式是为了引出偏函数
 *
 */
object PartialFunction2 {
  def main(args: Array[String]): Unit = {
    val list = List(1, 2, 3, 4, "abc")

    //定义一个偏函数，PartialFunction[Any, Int] 表示偏函数接收的参数类型是Any，返回类型是Int
    val partialFun = new PartialFunction[Any, Int] {
      //用isDefinedAt对每个元素进行判断，如果判断结果为ture，就会调用apply作用与该元素上，如果为false，则过滤忽略掉该元素
      override def isDefinedAt(x: Any): Boolean = x.isInstanceOf[Int]
      //apply构造器，对传入的值进行指定操作，并将结果放入新的集合
      override def apply(v1: Any): Int = v1.asInstanceOf[Int] + 1
    }

    //使用偏函数。注意使用偏函数不能使用map，使用map运行时会报错，应该使用collect
    val newlist = list.collect(partialFun)
    print(newlist)

  }

}
