package haoxy.redis.manage.service.Impl;

import haoxy.redis.manage.model.*;
import haoxy.redis.manage.resInfo.InfoCode;
import haoxy.redis.manage.resInfo.RespInfo;
import haoxy.redis.manage.service.RedisService;
import haoxy.redis.manage.utils.ConvertPageUtil;
import haoxy.redis.manage.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

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

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedisTemplate redisTemplate;
    public static final CopyOnWriteArrayList<Map<String, Object>> redisServerCache = new CopyOnWriteArrayList<Map<String, Object>>();

    @Override
    public RespInfo selectKeys(PageInfo pageInfo) {
        RespInfo respInfo = new RespInfo();
        ResParam resParam = new ResParam();
        ScanOptions options = ScanOptions.scanOptions().match("*").build();
        RedisConnection connection = null;
        RedisConnectionFactory factory = null;
        if (redisServerCache.get(0) != null) {
            RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
            standaloneConfiguration.setHostName((String) redisServerCache.get(0).get("host"));
            standaloneConfiguration.setPassword(RedisPassword.of((String) redisServerCache.get(0).get("password")));
            standaloneConfiguration.setPort((Integer) redisServerCache.get(0).get("port"));
            template();
            factory = new LettuceConnectionFactory(standaloneConfiguration);
            ((LettuceConnectionFactory) factory).afterPropertiesSet();
            connection = factory.getConnection();
        } else {
            factory = redisTemplate.getConnectionFactory();
            connection = factory.getConnection();
        }
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

    @Override
    public RespInfo addServer(ServerInfo serverInfo) {
        RespInfo respInfo = new RespInfo();
        //if (!StringUtil.isEmpty(serverInfo.getHost()) && !StringUtil.isEmpty(serverInfo.getPassword())) {
        ResParam resParam = new ResParam();
        Map<String, Object> redisServerMap = new HashMap<String, Object>();
        //RedisConnectionFactory redisConnectionFactory = new LettuceConnectionFactory();
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        String host = (String) (redisServerCache.get(0) == null ? serverInfo.getHost() : redisServerCache.get(0).get("host"));
        standaloneConfiguration.setHostName(host);
        String password = (String) (redisServerCache.get(0) == null ? serverInfo.getPassword() : redisServerCache.get(0).get("password"));
        standaloneConfiguration.setPassword(RedisPassword.of(password));
        Integer port = (Integer) (redisServerCache.get(0) == null ? serverInfo.getPort() : redisServerCache.get(0).get("port"));
        standaloneConfiguration.setPort(port);
        redisServerMap.put("host", host);
        redisServerMap.put("password", password);
        redisServerMap.put("port", port);
        redisServerCache.add(redisServerMap);
        template();
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(standaloneConfiguration);
        lettuceConnectionFactory.afterPropertiesSet();
        RedisConnection connection = lettuceConnectionFactory.getConnection();
        ScanOptions options = ScanOptions.scanOptions().match("*").build();
        Long aLong = connection.dbSize();
        Cursor<byte[]> cursor = connection.scan(options);
        List<BodyInfo> result = new ArrayList<>(serverInfo.getPageSize());
        // Map<Object,Object>resMap=new HashMap<>(pageInfo.getPageSize());
        int tmpIndex = 0;
        int startIndex = (serverInfo.getPageNow() - 1) * serverInfo.getPageSize();
        int end = serverInfo.getPageNow() * serverInfo.getPageSize();
        ConvertPageUtil.convertPage(lettuceConnectionFactory, connection, cursor, result, tmpIndex, startIndex, end, redisTemplate);
        resParam.setName(result);
        resParam.setTotal(aLong);
        respInfo.setContent(resParam);
        respInfo.setStatus(InfoCode.SUCCESS);
        return respInfo;
    }

    private void template() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setValueSerializer(new StringRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
    }
}
