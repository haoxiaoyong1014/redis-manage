package haoxy.redis.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by haoxiaoyong on 2018/10/31.
 * RedidController
 */
@Controller
@RequestMapping(value = "redis")
public class RedisController{

    @RequestMapping(method = RequestMethod.GET)
    public String home() {
        ServletRequestAttributes attributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();


        return "";
    }
}
