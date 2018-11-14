package haoxy.redis.manage.model;

/**
 * Created by haoxy on 2018/11/1.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public class PageInfo {

    private int pageSize;

    private int pageNow;

    private String cond;

    private String num;

    private String refresh;


    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    public String getCond() {
        return cond;
    }

    public void setCond(String cond) {
        this.cond = cond;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public int getPageNow() {
        return pageNow;
    }

    public void setPageNow(int pageNow) {
        this.pageNow = pageNow;
    }

    public PageInfo() {
    }

    public PageInfo(int pageSize, int pageNow) {
        this.pageSize = pageSize;
        this.pageNow = pageNow;
    }
}
