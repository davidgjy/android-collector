package com.genesis.carrescue.domain.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by KG on 16/10/26.
 */
public class ContractInfo implements Serializable {
    private int id;
    private int rescueId;
    private int carStoreId;
    private String contractNo;
    private String contactFileId;
    private int startRange;
    private double startFee;
    private double unitPrice;
    private Date startTime;
    private Date terminalTime;
    private Date createTime;
    private int status;
    private String carStoreName;
    private String carStoreUserPhone;
    private String carStoreCompanyName;
    private String carStoreCompanyAddress;
    private String rescueUserPhone;
    private String rescueCompanyName;
    private String rescueCompanyAddress;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRescueId() {
        return rescueId;
    }

    public void setRescueId(int rescueId) {
        this.rescueId = rescueId;
    }

    public int getCarStoreId() {
        return carStoreId;
    }

    public void setCarStoreId(int carStoreId) {
        this.carStoreId = carStoreId;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContactFileId() {
        return contactFileId;
    }

    public void setContactFileId(String contactFileId) {
        this.contactFileId = contactFileId;
    }

    public int getStartRange() {
        return startRange;
    }

    public void setStartRange(int startRange) {
        this.startRange = startRange;
    }

    public double getStartFee() {
        return startFee;
    }

    public void setStartFee(double startFee) {
        this.startFee = startFee;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getTerminalTime() {
        return terminalTime;
    }

    public void setTerminalTime(Date terminalTime) {
        this.terminalTime = terminalTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCarStoreName() {
        return carStoreName;
    }

    public void setCarStoreName(String carStoreName) {
        this.carStoreName = carStoreName;
    }

    public String getCarStoreUserPhone() {
        return carStoreUserPhone;
    }

    public void setCarStoreUserPhone(String carStoreUserPhone) {
        this.carStoreUserPhone = carStoreUserPhone;
    }

    public String getCarStoreCompanyName() {
        return carStoreCompanyName;
    }

    public void setCarStoreCompanyName(String carStoreCompanyName) {
        this.carStoreCompanyName = carStoreCompanyName;
    }

    public String getCarStoreCompanyAddress() {
        return carStoreCompanyAddress;
    }

    public void setCarStoreCompanyAddress(String carStoreCompanyAddress) {
        this.carStoreCompanyAddress = carStoreCompanyAddress;
    }

    public String getRescueUserPhone() {
        return rescueUserPhone;
    }

    public void setRescueUserPhone(String rescueUserPhone) {
        this.rescueUserPhone = rescueUserPhone;
    }

    public String getRescueCompanyName() {
        return rescueCompanyName;
    }

    public void setRescueCompanyName(String rescueCompanyName) {
        this.rescueCompanyName = rescueCompanyName;
    }

    public String getRescueCompanyAddress() {
        return rescueCompanyAddress;
    }

    public void setRescueCompanyAddress(String rescueCompanyAddress) {
        this.rescueCompanyAddress = rescueCompanyAddress;
    }
}
