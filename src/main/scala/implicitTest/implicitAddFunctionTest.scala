package implicitTest

/**
 * @author sober 2020-06-26-11:50
 */
object implicitAddFunctionTest {
  def main(args: Array[String]): Unit = {
    val a = new A
    a.method1

    //通过隐式转换函数来丰富类A的功能。
    implicit def f1(obj: A): B = {
      new B
    }

    //其底层就是通过隐式转换函数创建一个B类对象，然后A类对象调用method2这个方法时，其实是通过这个B类对象去调用B类中的method2方法。
    a.method2
  }
}

class A {
  def method1: Unit = {
    println("这是类A中的方法：method1")
  }
}

class B {
  def method2: Unit = {
    println("这是类B中的方法：method2")
  }
}
