package implicitTest

/**
 * @author sober 2020-06-26-15:03
 *
 *         隐式值：也叫隐式变量，将某个形参变量标记为implicit，编译器会在方法省略隐式参数的情况下去搜索作用域内的隐式值作为缺省参数
 */
object implicitValueTest {
  def main(args: Array[String]): Unit = {

    implicit val str: String = "jack"
    implicit val num: Int = 1

    def hello(implicit name: String) = {
      println("hello! " + name)
    }

    def sum(implicit num: Int = 10): Unit = {
      println("总数是 " + num)
    }

    hello("Rose")
    hello //省略隐式参数时编译器自动搜索作用域内的与隐式参数同类型和的隐式值作为缺省参数。注意不要带()

    sum(100)
    sum //省略隐式参数时编译器自动搜索作用域内的与隐式参数同类型和的隐式值作为缺省参数

    //注意当形参同时又有隐式值和默认值时，隐式值优先级更高。如上，执行sum输出的是1而不是10
    //形参的隐式值，默认值，传值这三个的优先级顺序是：传值 > 隐式值 > 默认值

  }

}
