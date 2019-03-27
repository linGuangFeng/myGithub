package com.ec.pojo.page;

import java.io.Serializable;
import java.util.List;

//分页结果对象
public class PageResult<T> implements Serializable {
    private long total;//总条数
    private List<T> rows;//结果

    public PageResult(long total, List<T> list) {
        this.total = total;
        this.rows = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
