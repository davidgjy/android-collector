package com.genesis.carrescue.domain.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by KG on 16/11/4.
 */
public class CarInfo implements Serializable {
    private int id;
    private String licensePlateNumber;
    private String insuranceFileId;
    private Date purchaseTime;
    private Date insuranceTerminalTime;
    private Date MOTTestTime;
    private Date createTime;
    private int rescueId;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getInsuranceFileId() {
        return insuranceFileId;
    }

    public void setInsuranceFileId(String insuranceFileId) {
        this.insuranceFileId = insuranceFileId;
    }

    public Date getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Date purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public Date getInsuranceTerminalTime() {
        return insuranceTerminalTime;
    }

    public void setInsuranceTerminalTime(Date insuranceTerminalTime) {
        this.insuranceTerminalTime = insuranceTerminalTime;
    }

    public Date getMOTTestTime() {
        return MOTTestTime;
    }

    public void setMOTTestTime(Date MOTTestTime) {
        this.MOTTestTime = MOTTestTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getRescueId() {
        return rescueId;
    }

    public void setRescueId(int rescueId) {
        this.rescueId = rescueId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

