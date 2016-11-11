package org.apache.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * byte[]形式的数据的写入读出Redis
 */
public class JRedisTool {

    private final static Logger logger = LoggerFactory.getLogger(JRedisTool.class);

    private String host = null;
    private int port = 6379;
    private String authCode = null;

    public JRedisTool(String redisHost, int redisPort, String authCode) {

        this.host = redisHost;
        this.port = redisPort;
        this.authCode = authCode;

    }
    public void write(byte[] key, byte[] value) {
        Jedis jedis = new Jedis(host, port);
        try {
            jedis.auth(authCode);
            jedis.set(key, value);
        } catch (Exception e) {
            logger.error("Failed to write date to Redis.");
            logger.error(e.getMessage());
        }
        jedis.disconnect();
    }

    /**
     * @param key
     * @return 如果出现认证或数据读取异常返回Null
     */
    public byte[] read(byte[] key) {
        Jedis jedis = new Jedis(host, port);
        byte[] modelBytes = null;
        try {
            jedis.auth(authCode);
            modelBytes = jedis.get(key);
        } catch (Exception e) {
            logger.error("Failed to read data from Redis.");
            logger.error(e.getMessage());
            return null;
        }
        jedis.disconnect();
        return modelBytes;
    }
}
