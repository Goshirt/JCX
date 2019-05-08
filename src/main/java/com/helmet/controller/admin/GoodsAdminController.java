package com.helmet.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.helmet.entity.Goods;
import com.helmet.entity.Log;
import com.helmet.service.CustomerReturnListGoodsService;
import com.helmet.service.GoodsService;
import com.helmet.service.LogService;
import com.helmet.service.SaleListGoodsService;
import com.helmet.util.StringUtil;

/**
 * 商品Controller
 * 
 * @author Helmet
 * 2018年8月24日
 */
@Controller
@RequestMapping("/admin/goods")
public class GoodsAdminController {
	
	@Resource
	private GoodsService goodsService;
	
	@Resource
	private LogService logService;
	
	@Resource
	private SaleListGoodsService saleListGoodsService;
	
	@Resource
	private CustomerReturnListGoodsService customerReturnListGoodsService;
	/**
	 * 获取商品集
	 * @param goods 查询条件
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/goodsList")
	@ResponseBody
//	@RequiresPermissions(value={"商品管理","进货入库","退货出库"})
	public Map<String, Object> getGoodsList(Goods goods,@RequestParam(value="page",required=false)Integer page,@RequestParam(value="rows",required=false)Integer pageSize) {
		Map<String, Object> resultMap=new HashMap<>();
		List<Goods> goodsList=goodsService.getGoodsList(goods, page, pageSize, Direction.ASC, "id");
		Long count=goodsService.count(goods);
		resultMap.put("rows", goodsList);
		resultMap.put("total", count);
		resultMap.put("success", true);
		return resultMap;
	}
	
	
	/**
	 * 保存商品
	 * @param goods
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	@RequiresPermissions(value="商品管理")
	public Map<String, Object> save(Goods goods){
		Map<String, Object> resultMap=new HashMap<>();
		if (goods!=null) {
			if (goods.getGoodsId()==null) {
				logService.log(new Log(Log.ADD_ACTION, "添加一个商品"+goods.toString()));
				goods.setLastPuchasingPrice(goods.getPurchasingPrice());//设置上次的进货价为当前的进货价
			}else {
				logService.log(new Log(Log.UPDATE_ACTION, "更改了商品信息"+goods.toString()));
			}
		}
		goodsService.save(goods);
		resultMap.put("success", true);
		return resultMap;
	}
	
	/**
	 * 根据goodsId删除商品
	 * @param goodsId
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@RequiresPermissions(value="商品管理")
	public Map<String, Object> delete(Integer goodsId){
		Map<String, Object> resultMap=new HashMap<>();
		Goods goods=goodsService.getGoodsByGoodsId(goodsId);
		if (goods.getState()==1) {
			resultMap.put("success", false);
			resultMap.put("errorInfo", "该商品已经期初入库，不能删除");
		}else if (goods.getState()==2) {
			resultMap.put("success", false);
			resultMap.put("errorInfo", "该商品已经发生单据，不能删除");
		}else {
			logService.log(new Log(Log.DELETE_ACTION, "删除一个商品"+goodsService.getGoodsByGoodsId(goodsId)));
			goodsService.delete(goodsId);
			resultMap.put("success", true);
		}
		return resultMap;
	}
	
	/**
	 * 获取最大的商品码
	 * @return
	 */
	@RequestMapping("/genGoodsCode")
	@ResponseBody
	@RequiresPermissions(value="商品管理")
	public String genGoodsCode(){
		String maxCode=goodsService.getMaxCode();
		if (StringUtil.isNotEmpty(maxCode)) {
			Integer code=Integer.parseInt(maxCode)+1;
			String codeStr=code.toString();
			int length=codeStr.length();
			//控制商品码的长度为4，并且表现形式为0001-9999
			for (int i = 4; i >length; i--) {
				codeStr="0"+codeStr;
			}
			return codeStr;
		}
		else {
			return "0001";
		}
	}
	
	
	/**
	 * 根据商品码或者商品名字获分页获取库存为0（inventoryQuantity=0）/未入库的商品
	 * @param codeOrName
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	@RequestMapping("/getGoodsNoInv")
	@ResponseBody
	@RequiresPermissions(value="期初库存")
	public Map<String, Object> getGoodsListWithNoInventoryQuantity(String codeOrName, @RequestParam(value="page",required=false)Integer page, @RequestParam(value="rows",required=false)Integer pageSize,
			Direction direction, String... properties) {
		Map<String, Object> resultMap=new HashMap<>();
		List<Goods> goodsList=goodsService.getGoodsListWithNoInventoryQuantity(codeOrName, page, pageSize, Direction.ASC, "id");
		Long count=goodsService.countGoodsListWithNoInventoryQuantity(codeOrName);
		resultMap.put("rows", goodsList);
		resultMap.put("total", count);
		return resultMap;
	}
	
	
	/**
	 * 根据商品码或者商品名字分页获取库存大于0（inventoryQuantity>0）/已入库的商品
	 * @param codeOrName
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	@RequestMapping("/getGoodsHaveInv")
	@ResponseBody
	@RequiresPermissions(value="期初库存")
	public Map<String, Object> getGoodsListWithHaveInventoryQuantity(String codeOrName, @RequestParam(value="page",required=false)Integer page, @RequestParam(value="rows",required=false)Integer pageSize,
			Direction direction, String... properties) {
		Map<String, Object> resultMap=new HashMap<>();
		List<Goods> goodsList=goodsService.getGoodsListWithHaveInventoryQuantity(codeOrName, page, pageSize, Direction.ASC, "id");
		Long count=goodsService.countGoodsListWithHaveInventoryQuantity(codeOrName);
		resultMap.put("rows", goodsList);
		resultMap.put("total", count);
		return resultMap;
	}
	
	
	/**
	 * 商品入库存 、 修改仓库中商品的价格或者数量
	 * @param goodsId
	 * @param num
	 * @param price
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveStock")
	@ResponseBody
	@RequiresPermissions(value="期初库存")
	public Map<String, Object> saveStock(Integer goodsId,int num,float price)throws Exception{
		Map<String, Object> resultMap=new HashMap<>();
		Goods goods=goodsService.getGoodsByGoodsId(goodsId);
		goods.setInventoryQuantity(num);
		goods.setPurchasingPrice(price);
		goods.setLastPuchasingPrice(price);
		goods.setState(1);
		goodsService.save(goods);
		logService.log(new Log(Log.UPDATE_ACTION, "商品入库存 、 修改仓库中商品的价格或者数量"+goods.toString()));
		resultMap.put("success", true);
		return resultMap;
	}
	
	/**
	 * 删除商品 
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteStock")
	@ResponseBody
	@RequiresPermissions(value="期初库存")
	public Map<String, Object> deleteStock(Integer goodsId)throws Exception{
		Map<String, Object> resultMap=new HashMap<>();
		Goods goods=goodsService.getGoodsByGoodsId(goodsId);
		if (goods.getState()==2) {
			resultMap.put("errorInfo", "商品已经产生单据，无法删除");
			resultMap.put("success", false);
		}else if (goods.getState()==2) {
			resultMap.put("errorInfo", "商品已经期初入库，无法删除");
			resultMap.put("success", false);
		}else{
			logService.log(new Log(Log.DELETE_ACTION, "删除商品"+goods.toString()));
			goodsService.delete(goodsId);
			resultMap.put("success", true);
		}
		return resultMap;
	}
	
	/**
	 * 根据条件查询当前的库存
	 * @param goods
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	@RequestMapping("/getInventoryNum")
	@ResponseBody
	@RequiresPermissions(value="当前库存查询")
	public Map<String, Object> getInventoryNum(Goods goods , @RequestParam(value="page",required=false)Integer page, @RequestParam(value="rows",required=false)Integer pageSize,Direction direction, String... properties ) throws Exception{
		Map<String, Object> resultMap = new HashMap<>();
		List<Goods> goodsList = goodsService.getInventoryGoodsList(goods, page, pageSize, Direction.DESC, "goodsId");
		for (Goods gd : goodsList) {
			gd.setSaleTotalNum(saleListGoodsService.getSaleTotalNum(gd.getGoodsId())-customerReturnListGoodsService.getReturnTotalNum(gd.getGoodsId()));
		}
		long total = goodsService.countInventoryGoodsList(goods);
		resultMap.put("rows", goodsList);
		resultMap.put("total", total);
		return resultMap;
	}
	/**
	 * 查询库存报警商品
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAlarmGoods")
	@ResponseBody
	@RequiresPermissions(value="库存报警")
	public Map<String, Object> getAlarmGoods() throws Exception{
		Map<String, Object> resultMap = new HashMap<>();
		List<Goods> goodsList = goodsService.getAlarmGoods();
		resultMap.put("rows", goodsList);
		return resultMap;
	}
	
}
