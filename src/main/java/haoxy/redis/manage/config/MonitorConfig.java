package haoxy.redis.manage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * Created by haoxy on 2018/10/31.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@Configuration
@PropertySources(@PropertySource("classpath:/redis.properties"))
public class MonitorConfig {
}
