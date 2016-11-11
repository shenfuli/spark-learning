package org.apache.tool

import org.slf4j.{Logger, LoggerFactory}
import redis.clients.jedis.Jedis

/**
  * Created by fuli.shen on 2016/11/10.
  */
class RedisTool(host: String, port: Int = 6379, authCode: String) {
  private val logger: Logger = LoggerFactory.getLogger(classOf[RedisTool])

  def write(key: Array[Byte], value: Array[Byte]) {
    val jedis: Jedis = new Jedis(host, port)
    try {
      jedis.auth(authCode)
      jedis.set(key, value)
    }
    catch {
      case e: Exception => {
        logger.error("Failed to write date to Redis.")
        logger.error(e.getMessage)
      }
    } finally {
      if (null != jedis) {}
      jedis.close()
    }
  }

  /**
    * @param key
    * @return 如果出现认证或数据读取异常返回Null
    */
  def read(key: Array[Byte]): Array[Byte] = {
    val jedis: Jedis = new Jedis(host, port)
    var value: Array[Byte] = null
    try {
      jedis.auth(authCode)
      value = jedis.get(key)
    }
    catch {
      case e: Exception => {
        logger.error("Failed to read data from Redis.")
        logger.error(e.getMessage)
        return null
      }
    } finally {
      if (null != jedis) {
        jedis.close()
      }
    }
    return value
  }
}
