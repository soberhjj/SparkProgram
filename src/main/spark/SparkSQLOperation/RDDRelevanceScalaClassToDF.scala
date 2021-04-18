package SparkSQLOperation

import org.apache.spark.sql.SparkSession

import scala.beans.BeanProperty

/**
 * @author huangJunJie 2021-04-18-10:20
 *
 *         RDD关联 普通的scala class，然后将RDD转成DataFrame
 */
object RDDRelevanceScalaClassToDF {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("RDDRelevanceScalaClassToDF")
      .master("local[2]")
      .getOrCreate()

    val sc = spark.sparkContext
    val lines = sc.textFile("src\\main\\spark\\SparkSQLOperation\\user.txt")

    val persons = lines.map(line => {
      val fields = line.split(" ")
      val f1 = fields(0)
      val f2 = fields(1).toInt
      val f3 = fields(2)
      new Person(f1, f2, f3) //case class不需要new ，普通class需要new
    })

    //注意如果RDD中的对象不是case class对象而是普通对象，那么是不能通过toDF()将RDD转成DataFrame，要用如下方式进行转换
    val df1 = spark.createDataFrame(persons, classOf[Person])

    df1.printSchema()
    df1.show()

    spark.stop()
  }

}

//注意对于普通的scala类，字段必须加上val或var进行修饰，否则这个字段是不作为这个类的成员变量的
//@BeanProperty注解的作用就是成员变量的getter/setter方法
class Person(@BeanProperty val name: String, @BeanProperty val age: Int, @BeanProperty val address: String)
