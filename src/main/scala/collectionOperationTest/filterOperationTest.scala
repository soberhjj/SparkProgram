package collectionOperationTest

/**
  * @author sober 2020-06-16-8:43
  */
object filterOperationTest {
  def main(args: Array[String]): Unit = {
    val list = List("Alice", "Bob", "Nick", "Alon", "Ankle")
    //过滤出首字母为"A"的元素
    val startWithA: List[String] = list.filter(x => x.startsWith("A"))
    println(startWithA)
  }

}
