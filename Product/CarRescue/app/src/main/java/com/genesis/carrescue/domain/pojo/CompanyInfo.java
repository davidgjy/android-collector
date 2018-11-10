package com.genesis.carrescue.domain.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by KG on 16/10/19.
 */
public class CompanyInfo implements Serializable {
    private int id;
    private String userPhone;
    private String businessLisence;
    private String businessLisenceFileId;
    private String companyName;
    private String companyAddress;
    private String contactPhone;
    private String subContactPhone;
    private double averageOutputValue;
    private int carNumber;
    private int accountId;
    private Date createTime;
    private Date lastActiveTime;
    private int status;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUserPhone() {
        return userPhone;
    }
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    public String getBusinessLisence() {
        return businessLisence;
    }
    public void setBusinessLisence(String businessLisence) {
        this.businessLisence = businessLisence;
    }
    public String getBusinessLisenceFileId() {
        return businessLisenceFileId;
    }
    public void setBusinessLisenceFileId(String businessLisenceFileId) {
        this.businessLisenceFileId = businessLisenceFileId;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getCompanyAddress() {
        return companyAddress;
    }
    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }
    public String getContactPhone() {
        return contactPhone;
    }
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    public String getSubContactPhone() {
        return subContactPhone;
    }
    public void setSubContactPhone(String subContactPhone) {
        this.subContactPhone = subContactPhone;
    }
    public double getAverageOutputValue() {
        return averageOutputValue;
    }
    public void setAverageOutputValue(double averageOutputValue) {
        this.averageOutputValue = averageOutputValue;
    }
    public int getCarNumber() {
        return carNumber;
    }
    public void setCarNumber(int carNumber) {
        this.carNumber = carNumber;
    }
    public int getAccountId() {
        return accountId;
    }
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getLastActiveTime() {
        return lastActiveTime;
    }
    public void setLastActiveTime(Date lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
