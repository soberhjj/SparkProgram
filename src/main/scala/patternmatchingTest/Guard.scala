package patternmatchingTest

/**
 * @Author: sober  2020-07-30 17:24
 *
 *         模式匹配中的守卫：如果想要匹配某个范围的数据，可以在模式匹配中增加条件守卫。例如要匹配0-7，如果不用守卫，
 *         那么需要写8个case，用了守卫就可以简化这样的代码
 */
object Guard {
  def main(args: Array[String]): Unit = {
    for (ch <- "+-3!%") {
      var sign = 0
      var digit = 0
      ch match {
        case '+' => sign = 1
        case '-' => sign = -1
        //如果case _ 后出现守卫条件，那么这个case _ 不表示默认匹配，表示的就是守卫
        case _ if (ch.toString.equals("3")||ch.toString.equals("!")) => digit = 3
        case _ => sign = 2  //默认匹配
      }
      println(ch + " " + sign + " " + digit)
    }
  }

}
