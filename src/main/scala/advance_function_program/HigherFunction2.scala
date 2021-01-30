package advance_function_program

/**
 * @author huangJunJie 2021-01-30-12:50
 *
 *         高阶函数返回另一个函数
 */
object HigherFunction2 {
  def main(args: Array[String]): Unit = {

    def handsome(x:Int)={
      (y:Int) => x-y  //这是一个匿名函数
    }

    val f: Int => Int = handsome(1)

    print(f(0))
  }
}