package collectionOperationTest

/**
  * @author sober 2020-06-16-9:13
  */
object foldOperationTest {
  def main(args: Array[String]): Unit = {
    val ints1 = List(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val res: Int = ints1.foldLeft(100)((A, B) => A + B)
    print(res)

  }

}
