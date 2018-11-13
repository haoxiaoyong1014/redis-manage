package haoxy.redis.manage.service.Impl;

import haoxy.redis.manage.model.*;
import haoxy.redis.manage.resInfo.InfoCode;
import haoxy.redis.manage.resInfo.RespInfo;
import haoxy.redis.manage.service.RedisService;
import haoxy.redis.manage.utils.Constant;
import haoxy.redis.manage.utils.ConvertPageUtil;
import haoxy.redis.manage.utils.ResUtils;
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
public class RedisServiceImpl implements RedisService, Constant {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public RespInfo selectKeys(PageInfo pageInfo) {
        RespInfo respInfo = new RespInfo();
        ResParam resParam = new ResParam();
        ScanOptions options = null;
        RedisConnection connection = null;
        RedisConnectionFactory factory = null;
        if (redisServerCache.get(0) != null) {
            RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
            standaloneConfiguration.setHostName((String) redisServerCache.get(0).get(REDISPROPERTIES_HOST_PROFIXKEY));
            standaloneConfiguration.setPassword(RedisPassword.of((String) redisServerCache.get(0).get(REDISPROPERTIES_PASSWORD_PROFIXKEY)));
            standaloneConfiguration.setPort(Integer.parseInt(String.valueOf(redisServerCache.get(0).get(REDISPROPERTIES_PORT_PROFIXKEY))));
            ResUtils.template();
            factory = new LettuceConnectionFactory(standaloneConfiguration);
            ((LettuceConnectionFactory) factory).afterPropertiesSet();
            connection = factory.getConnection();
        } else {
            factory = redisTemplate.getConnectionFactory();
            connection = factory.getConnection();
        }
        if (pageInfo.getCond() == null) {
            options = ScanOptions.scanOptions().match("*").build();

        } else {
            options = pageInfo.getNum() == REDISPROPERTIES_ONE_PROFIXKEY ?
                    ScanOptions.scanOptions().match(pageInfo.getCond() + "*").build() :
                    ScanOptions.scanOptions().match("*" + pageInfo.getCond() + "*").build();
        }
        Long aLong = connection.dbSize();
        Cursor<byte[]> cursor = connection.scan(options);
        System.out.println("打印的 alone= "+aLong);
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
        // String host = (String) (redisServerCache.get(0) == null ? serverInfo.getHost() : redisServerCache.get(0).get("host"));
        standaloneConfiguration.setHostName(serverInfo.getHost());
        //String password = (String) (redisServerCache.get(0) == null ? serverInfo.getPassword() : redisServerCache.get(0).get("password"));
        standaloneConfiguration.setPassword(RedisPassword.of(serverInfo.getPassword()));
        //Integer port = (redisServerCache.get(0) == null ? serverInfo.getPort() : Integer.parseInt((String) redisServerCache.get(0).get("port")));
        standaloneConfiguration.setPort(serverInfo.getPort());
        redisServerMap.put(REDISPROPERTIES_HOST_PROFIXKEY, serverInfo.getHost());
        redisServerMap.put(REDISPROPERTIES_PASSWORD_PROFIXKEY, serverInfo.getPassword());
        redisServerMap.put(REDISPROPERTIES_PORT_PROFIXKEY, serverInfo.getPort());
        redisServerCache.clear();
        redisServerCache.add(redisServerMap);
        ResUtils.template();
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


}
