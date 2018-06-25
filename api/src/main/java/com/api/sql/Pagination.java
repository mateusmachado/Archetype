package com.api.sql;

import lombok.Data;

import java.util.List;

@Data
public class Pagination<T> {

    private long total;
    private List<T> content;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
