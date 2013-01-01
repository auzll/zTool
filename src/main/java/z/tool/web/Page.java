/**
 * https://github.com/auzll/zTool
 */
package z.tool.web;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 分页参数
 * @author auzll@163.com
 */
public class Page {
    /** 每页默认最多显示15条记录 */
    public static final int DEFAULT_PAGE_MAX = 15;
    
    private int count;
    private Map<?, ?> others;
    private int page = 1;
    private int pageMax = DEFAULT_PAGE_MAX;
    private List<?> recordList = Collections.emptyList();;
    private int totalPage = 1;
    
    @SuppressWarnings({"rawtypes"})
    private static final Page EMPTY_PAGE = new Page() {
        @Override public void setCount(int count) {};
        @Override public void setOthers(Map others) {};
        @Override public void setPage(int page) {};
        @Override public void setPageMax(int pageMax) {};
        @Override public void setRecordList(List recordList) {};
        @Override public Page resetPage() { return this;}
    };
    
    public static Page emptyPage() {
        return EMPTY_PAGE;
    }

    public int getCount() {
        return count;
    }

    public int getNextPage() {
        if (page < totalPage) {
            return page + 1;
        }
        return totalPage;
    }

    public Map<?, ?> getOthers() {
        return others;
    }

    public int getPage() {
        return page;
    }

    public int getPageMax() {
        return pageMax;
    }

    public int getPrePage() {
        if (page > 1) {
            return page - 1;
        }
        return 1;
    }

    public List<?> getRecordList() {
        return recordList;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public boolean hasNextPage() {
        if (page < totalPage) {
            return true;
        }
        return false;
    }

    public boolean hasPrePage() {
        if (page > 1) {
            return true;
        }
        return false;
    }

    public Page resetPage() {
        setCount(0);
        setOthers(Collections.emptyMap());
        setRecordList((List<?>) Collections.emptyList());
        return this;
    }

    public void setCount(int count) {
        this.count = count;
        totalPage = count / pageMax;
        if (count % pageMax > 0) {
            totalPage++;
        }
        if (totalPage < 1) {
            totalPage = 1;
        }
    }

    public void setOthers(Map<?, ?> others) {
        this.others = others;
    }

    public void setPage(int page) {
        if (page < 1) {
            page = 1;
        }
        this.page = page;
    }

    public void setPageMax(int pageMax) {
        if (pageMax < 1) {
            pageMax = DEFAULT_PAGE_MAX;
        }
        this.pageMax = pageMax;
    }

    public void setRecordList(List<?> recordList) {
        this.recordList = recordList;
    }

    @Override
    public String toString() {
        return new StringBuilder("{count:").append(count).append(",page:")
                .append(page).append(",pageMax:").append(pageMax)
                .append(",recordList:").append(recordList).append(",others:")
                .append(others).append("}").toString();
    }
}
