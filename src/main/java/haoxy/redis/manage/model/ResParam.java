package haoxy.redis.manage.model;

import java.util.*;

/**
 * Created by haoxy on 2018/11/3.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public class ResParam {

    private Map<Object,Object> name;

    private Long total;


    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Map<Object, Object> getName() {
        return name;
    }

    public void setName(Map<Object, Object> name) {
        this.name = name;
    }

    public ResParam() {
    }

}
