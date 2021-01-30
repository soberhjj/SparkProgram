package advance_function_program

/**
 * @author huangJunJie 2021-01-30-12:50
 *
 *         一个函数作为一个高阶函数的形参
 */
object HigherFunction1 {
  def main(args: Array[String]): Unit = {

    def test(f: Double => Double, n1: Double) = {
      f(n1)
    }


    def f(x:Double)={
      x*2
    }

    print(test(f,3))

  }


}
