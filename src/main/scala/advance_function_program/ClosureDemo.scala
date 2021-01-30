package advance_function_program

/**
 * @author huangJunJie 2021-01-30-13:59
 *
 *         编写一个程序，具体要求如下“
 *         1.编写一个函数 makeSuffix(suffix:String) 可以接收一个文件后缀名（比如 .jpg）,并返回一个闭包
 *         2.调用闭包，闭包中的函数接收一个传入的文件名，如果该文件名没有指定后缀，则用闭包中的文件名后缀作为传入的这个文件名的后缀（即给该文件加上和后缀）
 *         如果传入的文件名已带有后缀，那么不做处理（即不用再加后缀了）
 */
object ClosureDemo {
  def main(args: Array[String]): Unit = {

    def makeSuffix(suffix: String) = {
      //匿名函数
      (fileName: String) => if (fileName.endsWith(suffix)) fileName else fileName + suffix
    }

    val f: String => String = makeSuffix(".jpg")

    println(f("hello"))
    print(f("china.jpg"))
  }

}
