package oop_in_scala

/**
 * @author huangJunJie 2021-03-26-21:02
 */
object CatDemo {
  def main(args: Array[String]): Unit = {
    val cat = new Cat;
    //给对象属性赋值
    cat.name = "小白"
    cat.age = 2
    cat.color = "white"

    /**
     * 为什么能这样赋值呢？原因如下：
     * 在Cat类中比如当我们声明 var name: String 这个成员变量时，实际上底层对应的是 private String name
     * 同时会生成两个该成员变量对应的public方法，
     * 分别为 public String name(){return this.name} ,这个name()方法其实就是getter方法，
     * 还有一个方法是 public void name_$eq(String x$1){this.name = x$1}，这个name_$eq(String x$1)方法其实就是setter方法。
     * 所以上面的 cat.name="小白" 实际上是调用了 name_$eq(String x$1)这个方法给name这个成员变量赋值，即 name_$eq("小白")。
     */
    printf("小猫的信息如下：%s %d %s", cat.name, cat.age, cat.color) //这里的cat.name实际上就是调用的name()这个方法
  }
}

class Cat {
  /**
   * 声明成员变量（即属性）
   * 注意在scala需要加下划线 _ 来表示给成员变量赋予默认值
   */
  var name: String = "hello" //声明成员变量时直接给一个初始值
  var age: Int = _ // 下划线 _ 表示给age一个默认的值，Int类型的默认值是0
  var color: String = _ // 下划线 _ 表示给color一个默认的值，String类型的默认值是 null
}

class User {

  var username: String = _

  private var age: Int = _ //如果给属性增加private修饰符，那么属性无法在外部访问，因为底层生成的age()和age_$eq(Int x$1)即getter方法和setter方法都是私有的

  val email: String = "hello" //如果声明的属性使用val，那么在底层，属性不仅是私有的，并且是使用final修饰的，底层只提供emial()即getter方法，而没有email_$eq(String x$1)即setter方法

}
