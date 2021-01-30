package advance_function_program

/**
 * @author huangJunJie 2021-01-30-11:15
 *
 *         需求：给出一个集合val list=List(1,2,3,4,"abc")，将集合list中的所有数字加1，非数字忽略，结果放入一个新的集合，新集合应该是（2，3，4，5）
 *
 *          思路: 通过filter + map 来实现
 */
object PartialFunction1 {
  def main(args: Array[String]): Unit = {
    val list = List(1, 2, 3, 4, "abc")

    //先过滤，过滤后元素的类型为Any，需要将Any转为Int，然后再加1
    val newlist = list.filter(f1).map(f2).map(f3)

    print(newlist)


  }


  //过滤条件
  def f1(n: Any): Boolean = {
    n.isInstanceOf[Int]
  }

  //将Any类型转为Int类型
  def f2(n: Any): Int = {
    n.asInstanceOf[Int]
  }

  //加1
  def f3(n:Int):Int={
    n+1
  }


}
