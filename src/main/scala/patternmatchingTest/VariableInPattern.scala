package patternmatchingTest

/**
 * @Author: sober  2020-07-30 17:35
 *
 *         模式中的变量：如果在case关键字后跟变量名，那么匹配的表达式的值会赋给那个变量
 */
object VariableInPattern {
  def main(args: Array[String]): Unit = {
    val ch = 'V'
    ch match {
      case '+' => println("other~")
      case mychar => println("wise~" + mychar)  //ch的值赋给变量mychar
      case _ => println ("say~~")
    }

  }

}
