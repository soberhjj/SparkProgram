package patternmatchingTest

/**
 * @Author: sober  2020-07-30 17:07
 *
 *         匹配基本数据类型
 */
object PatternBasicDataType {
  def main(args: Array[String]): Unit = {
    val oper = '/'
    val n1 = 20
    val n2 = 10
    var res = 0
    oper match {
      case '+' => res = n1 + n2    //每个case中，不用break语句，只要匹配到一个case，则自动结束不会跳入下一个case
      case '-' => res = n1 - n2    //符号 => 等价于 java swtich中的符号 :
      case '*' => res = n1 * n2

      case '/' => res = n1 / n2
      case '/' => res = n1 % n2   //可以有多个相同的匹配模式，但是一旦匹配到只会执行第一个

      case 11 => println(" ")    //可以在match中使用其它类型，而不仅仅是字符

      case 1.2 => {              // =>后面的代码块到下一个case,是作为一个整体执行,代码块用{}括起来
        println("hello")
        println("this is 1.2")
      }

      case _ => println("oper error")  //如果所有case都不匹配，那么会执行case _ 分支，类似于Java switch表达式中的default语句，即默认匹配
    }
    println("res=" + res)

  }

}
