package com.genesis.carrescue.domain.pojo;

/**
 * Created by KG on 16/10/12.
 */
public class ResponseData<T> {
    private String jsonrpc = "2.0";
    private T result;
    private int id;

    public String getJsonrpc() {
        return jsonrpc;
    }
    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public T getResult() {
        return result;
    }
    public void setResult(T result) {
        this.result = result;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
