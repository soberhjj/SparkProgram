package sparkstreaming.KeepExactlyOnce.NonAggregateComputeTask

import java.sql.{DriverManager, ResultSet}

import org.apache.kafka.common.TopicPartition

import scala.collection.mutable

/**
 * @author huangJunJie 2021-05-12-23:44
 */
object HBaseOffsetUtils {

  /**
   * 通过jdbc连接phoenix进行查询
   * @param groupId 消费者组id
   * @return
   */
  def queryHistoryOffsetFromHBase(groupId: String): Map[TopicPartition, Long] = {

    val offsets = new mutable.HashMap[TopicPartition, Long]()

    val connection = DriverManager.getConnection("jdbc:phoenix:192.168.204.101,192.168.204.102,192.168.204.103:2181")//为什么这里支持jdbc:phoenix协议呢？因为引入了phoenix-core这个依赖包

    //phoenix查询的sql语句中如果表名、字段名等用小写的话，需要加双引号
    val pstm = connection.prepareStatement("select \"topic\",\"partition\",max(\"offset\") from \"t_orders\" where \"gid\"= ? group by \"topic\",\"partition\"")
    pstm.setString(1,groupId)
    val rs: ResultSet = pstm.executeQuery()
    while (rs.next()){
      val topic = rs.getString(1)
      val partition = rs.getString(2).toInt
      val offset = rs.getLong(3)

      offsets.put(new TopicPartition(topic,partition),offset)
    }

    offsets.toMap
  }
}
