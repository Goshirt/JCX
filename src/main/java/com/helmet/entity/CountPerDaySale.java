package com.helmet.entity;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

/**
 * 统计每天销售的实体
 * @author Helmet
 * @site www.helmet.xin
 * @create 2019-03-03 17:18
 */
@Entity
@Table(name = "t_countPerDaySale")
public class CountPerDaySale {
    @Id
    @GeneratedValue
    private Integer countPerDaySaleId;
    //当天销售总金额
    @Column
    private String saleTotalMoney;
    //成本
    @Column
    private String costTotalMoney;
    //利润
    @Column
    private String profits;


    public String getSaleTotalMoney() {
        return saleTotalMoney;
    }

    public void setSaleTotalMoney(String saleTotalMoney) {
        this.saleTotalMoney = saleTotalMoney;
    }

    public String getCostTotalMoney() {
        return costTotalMoney;
    }

    public void setCostTotalMoney(String costTotalMoney) {
        this.costTotalMoney = costTotalMoney;
    }

    public String getProfits() {
        return profits;
    }

    public void setProfits(String profits) {
        this.profits = profits;
    }


}
