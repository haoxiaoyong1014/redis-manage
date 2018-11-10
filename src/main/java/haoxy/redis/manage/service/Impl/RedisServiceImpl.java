package haoxy.redis.manage.service.Impl;

import haoxy.redis.manage.model.*;
import haoxy.redis.manage.resInfo.InfoCode;
import haoxy.redis.manage.resInfo.RespInfo;
import haoxy.redis.manage.service.RedisService;
import haoxy.redis.manage.utils.ConvertPageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.*;

import static haoxy.redis.manage.utils.ResUtils.getHashInfo;
import static haoxy.redis.manage.utils.ResUtils.getListInfo;
import static haoxy.redis.manage.utils.ResUtils.getStringInfo;

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
        switch (type) {
            case "hash":
                return getHashInfo(key, respInfo, list, redisTemplate);
            case "string":
                return getStringInfo(key, respInfo, list, redisTemplate);
            case "list":
                return getListInfo(key, respInfo, list, redisTemplate);
            default:
                respInfo.setContent("暂不支持此类型");
                break;
        }
        return respInfo;
    }


}
