package collectionOperationTest

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
  * @author sober 2020-06-16-9:39
  */
object exercise {
  def main(args: Array[String]): Unit = {
    //练习1：将字符串"AAAAAAAAAAABBBBBBBBCCCCCCCDDDDDDDD"中各个字符，通过左折叠即foldLet存放到一个ArrayBuffer中
    val sentense = "AAAAAAAAAAABBBBBBBBCCCCCCCDDDDDDDD"
    val arr: ArrayBuffer[Char] = new ArrayBuffer[Char]()
    val chars: ArrayBuffer[Char] = sentense.foldLeft(arr)(putArray)
    println(chars)
    println("===============")

    //练习2：统计字符串"AAAAAAAAAAABBBBBBBBCCCCCCCDDDDDDDD"中各个字母出现的次数，并将结果存储在一个Map集合中
    val resMap = mutable.Map[Char, Int]()
    sentense.foldLeft(resMap)(charCount)
    println(resMap)
    println("===============")

    //练习3：统计各个单词出现的次数，将结果存储在Map集合中，并按出现次数进行排序
    val wordList: List[String] = List("hello", "world", "hi", "good", "boy", "girl", "hello", "hello", "world", "hello", "world", "hi", "hi", "good", "man", "woman")
    val wordCountResMap: mutable.Map[String, Int] = mutable.Map[String, Int]()
    wordList.foldLeft(wordCountResMap)((wordCountResMap: mutable.Map[String, Int], str: String) => {
      wordCountResMap += (str -> (wordCountResMap.getOrElse(str, 0) + 1))
    })
    //上面匿名函数和下面charCount这个方法的实现逻辑是一样的

    //将mutable.Map[String, Int]集合转换为Seq[(String, Int)]集合，转换得到的Seq集合中的元素是元组，以元组的第2个元素进行排序（这样也就是以每个单词的出现次数进行排序）
    val afterSort: Seq[(String, Int)] = wordCountResMap.toSeq.sortBy(_._2)//sortBy默认是从小到大排序，如何从大到小排序？
    println(afterSort)
    println("===============")


  }

  def putArray(arr: ArrayBuffer[Char], c: Char): ArrayBuffer[Char] = {
    //将c放入arr中
    arr.append(c)
    arr
  }

  def charCount(resMap: mutable.Map[Char, Int], c: Char): mutable.Map[Char, Int] = {
    resMap += (c -> (resMap.getOrElse(c, 0) + 1))
  }

}
