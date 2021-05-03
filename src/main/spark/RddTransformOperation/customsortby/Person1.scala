package RddTransformOperation.customsortby

/**
 * @author huangJunJie 2021-05-03-11:13
 *
 *         extends Comparable[T] 重写compareTo方法(即定义比较规则)
 *         case class默认可序列化
 */
case class Person1(name: String, age: Int, address: String) extends Comparable[Person1]  {
  override def compareTo(o: Person1): Int = {
    this.age.compareTo(o.age)
  }
}
