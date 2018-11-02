package haoxy.redis.manage.model;

/**
 * Created by haoxy on 2018/11/1.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public class PageInfo {

    private int pageSize;

    private int pageNow;

    private int totalCount; // 总的记录条数

    private int totalPageCount; // 总的页数


    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
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
