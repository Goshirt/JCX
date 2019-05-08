package com.helmet.entity;

/**
 * 统计每天销售的实体
 * @author Helmet
 * @site www.helmet.xin
 * @create 2019-03-03 17:18
 */
public class CountPerMonthSale {

    //当天销售总金额
    private float saleTotalMoney;
    //成本
    private float costTotalMoney;
    //利润
    private float profits;

    private String saleMonth;

    public float getSaleTotalMoney() {
        return saleTotalMoney;
    }

    public void setSaleTotalMoney(float saleTotalMoney) {
        this.saleTotalMoney = saleTotalMoney;
    }

    public float getCostTotalMoney() {
        return costTotalMoney;
    }

    public void setCostTotalMoney(float costTotalMoney) {
        this.costTotalMoney = costTotalMoney;
    }

    public float getProfits() {
        return profits;
    }

    public void setProfits(float profits) {
        this.profits = profits;
    }

    public String getSaleMonth() {
        return saleMonth;
    }

    public void setSaleMonth(String saleMonth) {
        this.saleMonth = saleMonth;
    }
}
