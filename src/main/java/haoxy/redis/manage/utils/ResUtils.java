package haoxy.redis.manage.utils;

import haoxy.redis.manage.model.BodyInfo;
import haoxy.redis.manage.resInfo.InfoCode;
import haoxy.redis.manage.resInfo.RespInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;
import java.util.Map;

/**
 * Created by haoxy on 2018/11/10.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public class ResUtils {

    public static RespInfo getStringInfo(String key, RespInfo respInfo, List<BodyInfo> list,RedisTemplate redisTemplate) {
        BodyInfo bodyInfo;
        String value = (String) redisTemplate.opsForValue().get(key);
        bodyInfo = new BodyInfo();
        bodyInfo.setKeyAndValue(value);
        list.add(bodyInfo);
        respInfo.setContent(list);
        respInfo.setStatus(InfoCode.SUCCESS);
        return respInfo;
    }

    public static RespInfo getHashInfo(String key, RespInfo respInfo, List<BodyInfo> list,RedisTemplate redisTemplate) {
        BodyInfo bodyInfo;
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
        for (Map.Entry<Object, Object> entry : entries.entrySet()) {
            bodyInfo = new BodyInfo();
            bodyInfo.setKeyAndValue(entry.getKey() + ":" + entry.getValue());
            list.add(bodyInfo);
        }
        respInfo.setContent(list);
        return respInfo;
    }

    public static RespInfo getListInfo(String key, RespInfo respInfo, List<BodyInfo> list,RedisTemplate redisTemplate) {
        BodyInfo bodyInfo;
        List<Object> range = redisTemplate.opsForList().range(key, 0, -1);
        for (Object obj : range) {
            bodyInfo = new BodyInfo();
            bodyInfo.setKeyAndValue(obj + "");
            list.add(bodyInfo);
        }
        respInfo.setContent(list);
        return respInfo;
    }
    public static void template() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setValueSerializer(new StringRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
    }
}
