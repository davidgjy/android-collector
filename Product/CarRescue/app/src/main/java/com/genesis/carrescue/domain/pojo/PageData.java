package com.genesis.carrescue.domain.pojo;

/**
 * Created by KG on 16/10/31.
 */
public class PageData {
    private int skip;
    private int limit;

    public int getSkip() {
        return skip;
    }
    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }
}
