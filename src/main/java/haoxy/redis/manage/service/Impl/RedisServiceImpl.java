package haoxy.redis.manage.service.Impl;

import haoxy.redis.manage.model.InfoCode;
import haoxy.redis.manage.model.PageInfo;
import haoxy.redis.manage.model.ResParam;
import haoxy.redis.manage.model.RespInfo;
import haoxy.redis.manage.service.RedisService;
import haoxy.redis.manage.utils.ConvertPageUtil;
import haoxy.redis.manage.utils.RKey;
import org.springframework.beans.factory.annotation.Autowired;
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
        Cursor<byte[]> cursor = connection.scan(options);
       // Map<Object,Object> result = new HashMap<>(pageInfo.getPageSize());
        //List<Object> result = resParam.getName(pageInfo.getPageSize());

        List<Object>result=new ArrayList<>(pageInfo.getPageSize());
        int tmpIndex = 0;
        int startIndex = (pageInfo.getPageNow() - 1) * pageInfo.getPageSize();
        int end = pageInfo.getPageNow() * pageInfo.getPageSize();
        ConvertPageUtil.convertPage(factory, connection, cursor, result, tmpIndex, startIndex, end);
        resParam.setName(result);
        respInfo.setContent(resParam);
        respInfo.setStatus(InfoCode.SUCCESS);
        return respInfo;
    }
}
