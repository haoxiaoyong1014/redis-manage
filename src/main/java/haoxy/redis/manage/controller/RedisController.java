package haoxy.redis.manage.controller;

import com.alibaba.fastjson.JSON;
import haoxy.redis.manage.model.PageInfo;
import haoxy.redis.manage.model.ServerInfo;
import haoxy.redis.manage.resInfo.RespInfo;
import haoxy.redis.manage.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by haoxiaoyong on 2018/10/31.
 * RedidController
 */
@Controller
@RequestMapping(value = "redis")
public class RedisController {

    @Value("${redis.name}")
    private String defaultServerName;

    @Value("${redis.index}")
    private String DEFAULT_DBINDEX;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询所有的key
     *
     * @param pageInfo
     * @return
     */
    @RequestMapping(value = "/keys")
    @ResponseBody
    public String redisKeys(@RequestBody PageInfo pageInfo) {
        RespInfo respInfo = redisService.selectKeys(pageInfo);
        return JSON.toJSONString(respInfo);
    }

    /**
     * 根据 key获取 value
     */
    @RequestMapping(value = "getValue")
    @ResponseBody
    public String getValue(String key, String type) {
        RespInfo respInfo = redisService.selectValueByKey(key, type);
        return JSON.toJSONString(respInfo);
    }

    /**
     * add server
     */
    @RequestMapping(value = "add_server")
    @ResponseBody
    public String addServer(@RequestBody ServerInfo serverInfo){
        RespInfo respInfo=redisService.addServer(serverInfo);
        return JSON.toJSONString(respInfo);
    }

    /**
     * 向 redis中加入值
     */
    @RequestMapping(value = "add")
    public void add() {
        List<Object>list=new ArrayList<>();
        list.add("ig3");
        list.add("rng3");
        list.add("hh3");
        redisTemplate.opsForList().leftPushAll("list_keys",list);
    }


}
