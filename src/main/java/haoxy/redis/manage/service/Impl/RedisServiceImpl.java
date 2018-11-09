package haoxy.redis.manage.service.Impl;

import haoxy.redis.manage.model.*;
import haoxy.redis.manage.service.RedisService;
import haoxy.redis.manage.utils.ConvertPageUtil;
import haoxy.redis.manage.utils.RKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Set;

/**
 * Created by haoxy on 2018/11/1.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public RespInfo selectKeys(PageInfo pageInfo) {
        RespInfo respInfo = new RespInfo();
        ResParam resParam = new ResParam();
        ScanOptions options = ScanOptions.scanOptions().match("*").build();
        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
        RedisConnection connection = factory.getConnection();
        Long aLong = connection.dbSize();
        Cursor<byte[]> cursor = connection.scan(options);
        List<BodyInfo> result = new ArrayList<>(pageInfo.getPageSize());
        // Map<Object,Object>resMap=new HashMap<>(pageInfo.getPageSize());
        int tmpIndex = 0;
        int startIndex = (pageInfo.getPageNow() - 1) * pageInfo.getPageSize();
        int end = pageInfo.getPageNow() * pageInfo.getPageSize();
        ConvertPageUtil.convertPage(factory, connection, cursor, result, tmpIndex, startIndex, end, redisTemplate);
        resParam.setName(result);
        resParam.setTotal(aLong);
        respInfo.setContent(resParam);
        respInfo.setStatus(InfoCode.SUCCESS);
        return respInfo;
    }

    @Override
    public RespInfo selectValueByKey(String key, String type) {
        RespInfo respInfo = new RespInfo();
        List<BodyInfo> list = new ArrayList<>();
        BodyInfo bodyInfo = null;
        if (type.equals("hash")) {
            Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
            for (Map.Entry<Object, Object> entry : entries.entrySet()) {
                bodyInfo = new BodyInfo();
                bodyInfo.setKeyAndValue(entry.getKey() + ":" + entry.getValue());
                list.add(bodyInfo);

            }
            respInfo.setContent(list);
            return respInfo;
        }
        String value = (String)redisTemplate.opsForValue().get(key);
        bodyInfo = new BodyInfo();
        bodyInfo.setKeyAndValue(value);
        list.add(bodyInfo);
        respInfo.setContent(list);
        respInfo.setStatus(InfoCode.SUCCESS);
        return respInfo;
    }
}
