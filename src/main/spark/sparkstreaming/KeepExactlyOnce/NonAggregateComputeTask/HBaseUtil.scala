package sparkstreaming.KeepExactlyOnce.NonAggregateComputeTask

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory}


/**
 * @author huangJunJie 2021-05-10-22:00
 *
 *         HBase的工具类，用来创建HBase连接
 */
object HBaseUtil {
  /**
   * @param zkQuorum zookeeper的IP地址，多个时需要用逗号隔开
   * @param port zookeeper的端口号
   * @return
   */
  def getConnection(zkQuorum: String, port: Int): Connection = synchronized {
    val conf = HBaseConfiguration.create()
    conf.set("hbase.zookeeper.quorum",zkQuorum)
    conf.set("hbase.zookeeper.property.clientPort",port.toString)
    ConnectionFactory.createConnection(conf)
  }
}
