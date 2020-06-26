package implicitTest

/**
 * @author sober 2020-06-26-11:31
 *
 *         隐式转换函数是以implicit关键字声明的带单个参数的函数。隐式函数将会自动应用，将值从一种类型转换为另一种类型。
 *         注意隐式转换函数在作用域内生效。
 */
object implicitFunctionTest {
  def main(args: Array[String]): Unit = {

    //编写要给隐式函数进行Double类型到Int类型的转换
    implicit def doubleToInt(d: Double): Int = {
      d.toInt
    }
    val num: Int = 3.5
    println(num)
  }

}
