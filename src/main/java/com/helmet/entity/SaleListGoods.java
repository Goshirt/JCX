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
 * 销售单商品实体
 * 
 * @author Helmet
 * 2018年9月8日
 */
@Entity
@Table(name="t_saleListGoods")
public class SaleListGoods {
	
	@Id
	@GeneratedValue
	private Integer saleListGoodsId;    //编号
	
	@ManyToOne
	@JoinColumn(name="saleListId")
	private SaleList saleList;    //销售单
	
	@Column(length=30)
	private String code;    //商品码
	
	@Column(length=100)
	private String name;   //商品名称

	@Column(length=50)
	private String model;    //商品型号
	
	@ManyToOne
	@JoinColumn(name="goodsTypeId")
	private GoodsType goodsType;    //商品类型
	
	@Transient
	private Integer goodsTypeId;    //不映射到数据库，为了方便保存销售单中的商品

	
	private Integer goodsId;    //商品id
	
	@Column(length=10)
	private String unit;   //商品单位
	
	private float price;   //商品的销售单价
	
	private int num;    //商品的销售数量
	
	private float total;    //销售总金额

	public Integer getSaleListGoodsId() {
		return saleListGoodsId;
	}

	public void setSaleListGoodsId(Integer saleListGoodsId) {
		this.saleListGoodsId = saleListGoodsId;
	}

	public SaleList getSaleList() {
		return saleList;
	}

	public void setSaleList(SaleList saleList) {
		this.saleList = saleList;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setGoodsName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public GoodsType getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(GoodsType goodsType) {
		this.goodsType = goodsType;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public Integer getGoodsTypeId() {
		return goodsTypeId;
	}

	public void setGoodsTypeId(Integer goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}

	@Override
	public String toString() {
		return "SaleListGoods [saleListGoodsId=" + saleListGoodsId + ", saleList=" + saleList + ", code="
				+ code + ", name=" + name + ", model=" + model + ", goodsType=" + goodsType + ", goodsTypeId="
				+ goodsTypeId + ", goodsId=" + goodsId + ", unit=" + unit + ", price=" + price + ", num=" + num
				+ ", total=" + total + "]";
	}

	
}
