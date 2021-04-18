package oop_in_scala

/**
 * @author huangJunJie 2021-03-26-22:44
 */
object ConstructorDemo {
  def main(args: Array[String]): Unit = {
    val person1 = new Person("张三", 23)
    val person2 = new Person("李四", 24, "北京")
  }
}

class Person(inName: String, inAge: Int) {
  var name: String = inName
  var age: Int = inAge
  var address: String = _

  def this(name: String, age: Int, address: String) {
    this(name, age) //辅助构造器必须先调用主构造器（无论是直接还是间接）
    this.address = address
  }
}


