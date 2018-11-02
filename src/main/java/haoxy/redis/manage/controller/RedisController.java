package haoxy.redis.manage.controller;

import com.alibaba.fastjson.JSON;
import haoxy.redis.manage.model.PageInfo;
import haoxy.redis.manage.model.RespInfo;
import haoxy.redis.manage.service.RedisService;
import haoxy.redis.manage.utils.RKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

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
     *
     */

}
