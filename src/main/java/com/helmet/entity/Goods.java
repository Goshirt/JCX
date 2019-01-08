package com.helmet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 货物实体
 * 
 * @author Helmet
 * 2018年8月21日
 */
@Entity
@Table(name="t_goods")
public class Goods {
	
	@Id
	@GeneratedValue
	private Integer goodsId;
	
	@Column(length=20)
	private String code;//商品码
	
	private int inventoryQuantity;  //库存量
	
	private int minNum;  //库存最低下限
	
	@Column(length=100)
	private String model; //物品型号
	
	@Column(length=50)
	private String name; // 物品名称
	
	@Column(length=200)
	private String producer;  //生产厂商
	
	
	private float purchasingPrice; //进货价,每次有新的进货，要计算平均值
	
	@Column(length=1000)
	private String remarks;  //备注
	
	
	private float sellingPrice; //出售价
	
	@Column(length=10)
	private String unit;  //物品单位
	
	private int state;  //物品状态    0:初始化状态     1:期初库存入库状态      2: 有进货单或者销售单据
	
	
	private float lastPuchasingPrice;  //物品上一次进货价
	
	@Transient
	private String codeOrName;  //商品码或者商品名称，不映射到数据库，只为了方便封装，查询
	
	@Transient
	private Integer saleTotalNum;  //商品的销售总数
	
	@ManyToOne
	@JoinColumn(name="goodsTypeId")
	private GoodsType goodsType; //物品类别
	
	
	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getInventoryQuantity() {
		return inventoryQuantity;
	}

	public void setInventoryQuantity(int inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}

	public int getMinNum() {
		return minNum;
	}

	public void setMinNum(int minNum) {
		this.minNum = minNum;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public float getPurchasingPrice() {
		return purchasingPrice;
	}

	public void setPurchasingPrice(float purchasingPrice) {
		this.purchasingPrice = purchasingPrice;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public float getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(float sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public float getLastPuchasingPrice() {
		return lastPuchasingPrice;
	}

	public void setLastPuchasingPrice(float lastPuchasingPrice) {
		this.lastPuchasingPrice = lastPuchasingPrice;
	}

	public GoodsType getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(GoodsType goodsType) {
		this.goodsType = goodsType;
	}

	public String getCodeOrName() {
		return codeOrName;
	}

	public void setCodeOrName(String codeOrName) {
		this.codeOrName = codeOrName;
	}
	
	

	public Integer getSaleTotalNum() {
		return saleTotalNum;
	}

	public void setSaleTotalNum(Integer saleTotalNum) {
		this.saleTotalNum = saleTotalNum;
	}

	@Override
	public String toString() {
		return "Goods [goodsId=" + goodsId + ", code=" + code + ", inventoryQuantity=" + inventoryQuantity + ", minNum="
				+ minNum + ", model=" + model + ", name=" + name + ", producer=" + producer + ", purchasingPrice="
				+ purchasingPrice + ", remarks=" + remarks + ", sellingPrice=" + sellingPrice + ", unit=" + unit
				+ ", state=" + state + ", lastPuchasingPrice=" + lastPuchasingPrice + ", codeOrName=" + codeOrName
				+ ", goodsType=" + goodsType + "]";
	}
	
	
}
