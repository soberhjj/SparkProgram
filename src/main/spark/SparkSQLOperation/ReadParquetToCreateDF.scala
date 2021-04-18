package SparkSQLOperation

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

/**
 * @author huangJunJie 2021-04-18-11:16
 *
 *         读取Parquet 创建DataFrame 再将DataFrame输出为Parquet
 *
 *         相对与CSV file、JSON file而言，Parquet file是SparkSQL的最爱。
 *         原因是：Parquet file是最节省空间的。JSON file中每条json串都包含了元信息（即字段名啥的），所以JSON file是包含非常多冗余数据的
 *         ，CSV file的数据冗余相对与JSON file要少很多，但是CSV file还是没有Parquet file优秀，
 *         因为Parquet file是一种特殊的数据存储格式（列式存储），同时它还支持压缩，所以Parquet file的存储非常紧凑，即是最节省空间的。
 *         而且因为Parquet file是列示存储的，所以可以做到你要读哪列，那么就当当读那一列就可以了，而不需要读取整个文件。
 *         所以读写Parquet file是最为高效的。
 *
 */
object ReadParquetToCreateDF {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("ReadParquetToCreateDF")
      .master("local[2]")
      .getOrCreate()

    val df = spark.read.parquet("src\\main\\spark\\SparkSQLOperation\\aa.parquet")
    df.printSchema()
    df.show()
    df.write.parquet("src\\main\\spark\\SparkSQLOperation\\aa_out.parquet")

    spark.stop()
  }

}
