package com.helmet.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.helmet.entity.GoodsType;
import com.helmet.entity.Log;
import com.helmet.service.GoodTypeService;
import com.helmet.service.GoodsService;
import com.helmet.service.LogService;

@Controller
@RequestMapping("/admin/goodsType")
public class GoodsTypeController {
	
	@Resource
	private GoodTypeService goodTypeService;
	
	@Resource
	private LogService logService;
	
	@Resource
	private GoodsService goodsService;
	
	
	/**
	 * 获取GoodsType的树
	 * 
	 * @return
	 */
	@ResponseBody
	@PostMapping("/getTree")
	@RequiresPermissions(value={"商品管理","进货入库","退货出库"})
	public String getGoodsTypeTree() {
		return loadGoodsTypeTree(-1).toString();
	}
	
	/**
	 * 获取父节点下的一层孩子节点
	 * @param parentId
	 * @return
	 */
	public JsonArray getOneFloorGoodsType(Integer parentId){
		JsonArray jsonArray=new JsonArray();
		List<GoodsType> goodsTypes=goodTypeService.geGoodsTypesByParentId(parentId);
		for (GoodsType goodsType : goodsTypes) {
			JsonObject jsonObject=new JsonObject();
			jsonObject.addProperty("id", goodsType.getGoodsTypeId());
			jsonObject.addProperty("text", goodsType.getName());
			if(goodsType.getState()==1){
				jsonObject.addProperty("state", "closed");
			}else {
				jsonObject.addProperty("state", "open");
			}
			jsonObject.addProperty("iconCls", goodsType.getIcon());
			JsonObject attrObject=new JsonObject();
			//添加的属性是为了页面判断，使删除的按钮可用或者不可用,state=1 : 父节点       state=0　：叶子节点
			if(goodsType.getState()==1){
				attrObject.addProperty("state", 1);
			}else {
				attrObject.addProperty("state", 0);
			}
			jsonObject.add("attributes", attrObject);
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	
	/**
	 * 递归获取整棵树
	 * @param parentId
	 * @return
	 */
	public JsonArray loadGoodsTypeTree(Integer parentId){
		JsonArray jsonArray=this.getOneFloorGoodsType(parentId);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonObject jsonObject=(JsonObject) jsonArray.get(i);
			//如果是open状态，证明是叶子节点，进行下一次循环
			if ("open".equals(jsonObject.get("state"))) {
				continue;
			}else {
				//给该节点添加孩子熟悉，递归获取该节点的孩子节点
				jsonObject.add("children", loadGoodsTypeTree(jsonObject.get("id").getAsInt()));
			}
		}
		return jsonArray;
	}
	
	
	/**
	 * 保存商品类型
	 * @param goodsTypeName
	 * @param parentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions(value="商品管理")
	public Map<String, Object> save(String goodsTypeName,Integer parentId){
		Map<String, Object> resultMap=new HashMap<>();
		GoodsType goodsType=new GoodsType();
		goodsType.setName(goodsTypeName);
		goodsType.setIcon("icon-folder");
		goodsType.setParentId(parentId);
		goodsType.setState(0);
		goodTypeService.save(goodsType);
		logService.log(new Log(Log.ADD_ACTION, "新添加了一个商品类型:"+goodsType.toString()));
		//获取父节点的商品类型信息
		GoodsType parentGoodsType =goodTypeService.getGoodsTypeById(parentId);
		//如果新添加的节点的父亲节点以前是一个子节点，则需要把state改为1 变为父节点
		if(parentGoodsType.getState()==0){
			parentGoodsType.setState(1);
			goodTypeService.save(parentGoodsType);
			logService.log(new Log(Log.UPDATE_ACTION, "修改了一个商品类型的state状态"+parentGoodsType.toString()));
		}
		resultMap.put("success", true);
		return resultMap;
	}
	
	/**
	 * 根据Id删除商品类型
	 * @param goodsTypeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions(value="商品管理")
	public Map<String, Object> delete(Integer goodsTypeId){
		Map<String, Object> resultMap=new HashMap<>();
		//判断需要删除的商品类型下是否有商品
		Boolean haveGoods=goodsService.isGoodsTypeHaveGoods(goodsTypeId);
		if (haveGoods) {
			resultMap.put("success", false);
			resultMap.put("errorInfo", "该商品类型下有商品，无法删除，请先删除对应的商品");
			return resultMap;
		}
		//获取需要删除的商品类型实体
		GoodsType goodsType=goodTypeService.getGoodsTypeById(goodsTypeId);
		//通过需要删除的商品类型的父节点Id,获取到该商品类型的父节点拥有的所有孩子节点
		List<GoodsType> goodsTypes=goodTypeService.geGoodsTypesByParentId(goodsType.getParentId());
		//如果父节点只有一个孩子节点，证明要删除的节点是唯一子节点，删除之后需要改变父节点的状态为0，变为子节点
		if (goodsTypes.size()==1) {
			//获取该父节点实体
			GoodsType parentGoodsType=goodTypeService.getGoodsTypeById(goodsType.getParentId());
			//改变其状态
			parentGoodsType.setState(0);
			//保存
			goodTypeService.save(parentGoodsType);
		}
		logService.log(new Log(Log.DELETE_ACTION, "删除了一个商品类型"+goodsType));
		goodTypeService.delete(goodsTypeId);
		resultMap.put("success", true);
		return resultMap;
	}
}
