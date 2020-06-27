package collectionOperationTest

/**
  * @author sober 2020-06-11-14:52
  *
  *         flatMap操作的本质：
  *         flatMap操作相对于map操作来说就是多了个扁平化的操作。flat即压扁，压平，扁平化。
  *         flatMap效果就是将集合中的每个元素的所有子元素都执行传入flatMap方法的参数函数function以得到一个新的值，
  *         最终将所有新的值放入一个新的集合并返回
  */
object flatMapOperationTest {
  def main(args: Array[String]): Unit = {

    val name: List[String] = List("hello", "world", "from", "good")
    //实现：将name中的字符串扁平化并全部转为大写
    val res: List[Char] = name.flatMap(s => s.toUpperCase)
    println(res)

  }

}
