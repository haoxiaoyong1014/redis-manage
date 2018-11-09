package haoxy.redis.manage.model;

/**
 * Created by haoxy on 2018/11/8.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public class BodyInfo {

    private String type;

    private String name;

    private String keyAndValue;

    public String getKeyAndValue() {
        return keyAndValue;
    }

    public void setKeyAndValue(String keyAndValue) {
        this.keyAndValue = keyAndValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
