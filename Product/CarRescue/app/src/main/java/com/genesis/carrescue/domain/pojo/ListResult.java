package com.genesis.carrescue.domain.pojo;

import java.util.List;

/**
 * Created by KG on 16/11/3.
 */
public class ListResult<T> {
    protected List<T> results;
    protected int count;

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
