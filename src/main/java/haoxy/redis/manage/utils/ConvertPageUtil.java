package haoxy.redis.manage.utils;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisConnectionUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by haoxy on 2018/11/2.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public class ConvertPageUtil {
    public static void convertPage(RedisConnectionFactory factory, RedisConnection connection, Cursor<byte[]> cursor, Map<Object,Object> result, int tmpIndex, int startIndex, int end) {
        while (cursor.hasNext()) {
            if (tmpIndex >= startIndex && tmpIndex < end) {
                //result.put(new String(cursor.next()),connection.type(cursor.next()).code());
                result.put(new String(cursor.next()),connection.type(cursor.next()).code());
                tmpIndex++;
                continue;
            }
            // 获取到满足条件的数据后,就可以退出了
            if (tmpIndex >= end) {
                break;
            }
            tmpIndex++;
            cursor.next();
        }
        try {
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            RedisConnectionUtils.releaseConnection(connection, factory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
