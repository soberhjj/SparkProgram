package sparkstreaming.KeepExactlyOnce.AggregateComputeTask

import java.sql.Connection
import java.util.Properties

import com.alibaba.druid.pool.DruidDataSourceFactory
import javax.sql.DataSource

/**
 * @author huangJunJie 2021-05-09-12:24
 */
object DruidConnectPoolUtils {

  private val props = new Properties()

  props.put("driverClassName", "com.mysql.cj.jdbc.Driver")
  props.put("url", "jdbc:mysql://localhost:3306/sparkstreaming?characterEncoding=UTF-8")
  props.put("username", "root")
  props.put("password", "hjj19970829")

  private val source: DataSource = DruidDataSourceFactory.createDataSource(props)

  def getConnection: Connection = {
    source.getConnection
  }


}
