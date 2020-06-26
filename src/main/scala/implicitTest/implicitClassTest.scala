package implicitTest

/**
 * @author sober 2020-06-26-15:22
 *
 *        隐式类：隐式类非常强大，同样可以扩展类的功能，比使用隐式转换函数丰富类功能更加的方便，在集合中隐式类发挥重要的作用
 *        隐式类有如下几个特点：
 *        1.其所带的构造参数有且只能有一个
 *        2.隐式类必须被定义在 “类” 或 “伴生对象” 或 “包对象” 里，即隐式类不能是顶级的
 *        3.隐式类不能是case class
 *        4.作用域内不能有与之相同名称的标识符
 */
object implicitClassTest {
  def main(args: Array[String]): Unit = {

    //定义一个隐式类
    implicit class N(val obj:M){
      def sayHello(): Unit ={
        println("hello")
      }
    }

    val m = new M
    m.sayOK()
    m.sayHello()

    //隐式类底层实现与使用隐式转换函数丰富类功能的底层实现类似

  }

}

//应以要给普通类
class M{
  def sayOK(): Unit ={
    println("OK")
  }
}
