import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author sober 2020-06-17-9:50
  */
object WordCount {
  def main(args: Array[String]): Unit = {

    //创建SparkConf对象，设定Spark计算框架的运行（部署）环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("WordCount")

    //创建Spark上下文对象
    val sc: SparkContext = new SparkContext(sparkConf)

    //读取文件,将文件内容一行一行的读取出来
    val lines: RDD[String] = sc.textFile("src\\main\\spark\\word.txt")

    //将一行一行的数据分解为一个一个的单词
    val words: RDD[String] = lines.flatMap(_.split(" "))

    //为了统计方便，将单词数据进行结构的转换
    val wordToOne: RDD[(String, Int)] = words.map((_,1))

    //对转换结构后的数据进行分组聚合
    val wordToSum: RDD[(String, Int)] = wordToOne.reduceByKey(_+_)

    //收集统计后的数据到Driver
    val result: Array[(String, Int)] = wordToSum.collect()

    //打印到控制台
    result.foreach(println(_))

    //释放上下文对象
    sc.stop()

  }

}
