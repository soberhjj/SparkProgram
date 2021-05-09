package sparkstreaming.KeepExactlyOnce

import java.sql.{Connection, PreparedStatement, ResultSet}

import org.apache.kafka.common.TopicPartition

import scala.collection.mutable

/**
 * @author huangJunJie 2021-05-09-14:07
 */
object KafkaOffsetUtils {

  def queryHistoryOffsetFromMysql(groupId: String): Map[TopicPartition, Long] = {

    val offsetMap = new mutable.HashMap[TopicPartition, Long]()

    //查询数据库
    var connection: Connection = null
    var pstm: PreparedStatement = null
    var resultSet: ResultSet = null
    try {
      connection = DruidConnectPoolUtils.getConnection
      pstm = connection.prepareStatement("select topic_partition , offset from kafka_offset where gid = ?")
      pstm.setString(1, groupId)
      resultSet = pstm.executeQuery()
      //将查询Mysql返回的结果集封装进上面定义的offsetMap
      while (resultSet.next()) {
        val topic_partition = resultSet.getString(1)
        val offset = resultSet.getLong(2)
        val fields: Array[String] = topic_partition.split("_")
        val topic = fields(0)
        val partition = fields(1).toInt
        offsetMap(new TopicPartition(topic, partition)) = offset
      }
    } catch {
      case e: Exception => {
        throw new RuntimeException("查询历史偏移量出现异常")
      }
    } finally {
      if (resultSet != null) {
        resultSet.close()
      }
      if (pstm != null) {
        pstm.close()
      }
      if (connection != null) {
        connection.close()
      }
    }
    offsetMap.toMap
  }
}
