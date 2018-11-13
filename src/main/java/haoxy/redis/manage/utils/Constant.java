package haoxy.redis.manage.utils;

import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("rawtypes")
public interface Constant {


    public static final CopyOnWriteArrayList<Map<String, Object>> redisServerCache = new CopyOnWriteArrayList<Map<String, Object>>();

    /**
     * redis properties key
     **/
    public static final String REDISPROPERTIES_SERVER_NUM_KEY = "redis.server.num";
    public static final String REDISPROPERTIES_LANGUAGE_KEY = "redis.language";

    public static final String REDISPROPERTIES_HOST_PROFIXKEY = "host";
    public static final String REDISPROPERTIES_NAME_PROFIXKEY = "name";
    public static final String REDISPROPERTIES_PORT_PROFIXKEY = "port";
    public static final String REDISPROPERTIES_PASSWORD_PROFIXKEY = "password";

    public static final String REDISPROPERTIES_ONE_PROFIXKEY = "1";
}
