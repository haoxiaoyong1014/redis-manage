package haoxy.redis.manage.model;

import java.util.*;

/**
 * Created by haoxy on 2018/11/3.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public class ResParam {

    private List<BodyInfo> name;

    private Long total;


    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<BodyInfo> getName() {
        return name;
    }

    public void setName(List<BodyInfo> name) {
        this.name = name;
    }

    public ResParam() {
    }

}
