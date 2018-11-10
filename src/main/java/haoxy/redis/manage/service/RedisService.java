package haoxy.redis.manage.service;

import haoxy.redis.manage.model.PageInfo;
import haoxy.redis.manage.resInfo.RespInfo;

/**
 * Created by haoxy on 2018/11/1.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public interface RedisService {
    RespInfo selectKeys(PageInfo pageInfo);

    RespInfo selectValueByKey(java.lang.String key,String type);
}
