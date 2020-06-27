package collectionOperationTest

/**
  * @author sober 2020-06-16-8:56
  */
object reduceLeftOperationTest {
  def main(args: Array[String]): Unit = {
    val ints = List(1, 2, 3, 4, 5, 6, 7, 8, 9)
    //计算ints中的数字和
    val sum: Int = ints.reduceLeft((A, B) => A + B)
    println(sum)
  }

}
