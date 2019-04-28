package cn.lyc.common.api;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.mapper.Mapper;



public class ShopResult implements Serializable{
	
	//定义jackson对象
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	//响应业务状态
	private Integer status;
	
	//响应消息
	private String msg;
	
	//响应中的数据
	private Object data;
	
	
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static ObjectMapper getMapper() {
		return MAPPER;
	}
	
	
	public static ShopResult build(Integer status, String msg, Object data) {
		return new ShopResult(status, msg, data);
	}
	
	public static ShopResult ok(Object data) {
		return new ShopResult(data);
	}
	
	public static ShopResult ok() {
		return new ShopResult(null);
	}
	
	public ShopResult() {
		
	}
	
	public static ShopResult build(Integer status, String msg) {
		return new ShopResult(status, msg, null);
	}
	
	public ShopResult(Integer status, String msg, Object data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}
	
	public ShopResult(Object data) {
		this.status = 200;
		this.msg = "Ok";
		this.data = data;
	}
	
	/**
	 * 将json 结果转化为ChuuwResult对象
	 */
	public static ShopResult formatToPojo(String jsonData,Class<?> clazz) {
		try {
			if(clazz == null) {
				return MAPPER.readValue(jsonData, ShopResult.class);
			}
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			JsonNode data = jsonNode.get("data");
			Object obj = null;
			if(clazz != null) {
				if(clazz != null) {
					obj = MAPPER.readValue(data.traverse(), clazz);
				}else if(data.isTextual()) {
					obj = MAPPER.readValue(data.asText(), clazz);
				}
			}
			return build(jsonNode.get("status").intValue(),jsonNode.get("msg").asText(), obj);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 将json串转化为ChuuwResult对象
	 */
	public static ShopResult format(String json) {
		try {
			return MAPPER.readValue(json, ShopResult.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Object 是集合转化
	 */
	public static ShopResult formatToList(String jsonData,Class<?> clazz) {
		try {
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			JsonNode data = jsonNode.get("data");
			Object obj = null;
			if(data.isArray() && data.size() > 0) {
				obj = MAPPER.readValue(data.traverse(), MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
			}
			return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
		} catch (Exception e) {
			return null;
		}
	}

}
