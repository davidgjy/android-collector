package com.genesis.android.butler.domain.base;

/**
 * Created by KG on 16/10/18.
 */
public class BasePost {
    protected String jsonrpc = "2.0";
    protected String method;
    protected int id;

    public String getJsonrpc() {
        return jsonrpc;
    }
    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
