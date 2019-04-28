package cn.lyc.common.mybatis;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import cn.lyc.utils.ReflectionUtils;

public class BaseProvider {
	
	/**
	 * @methodDesc:功能描述：（自定义封装sql语句）
	 * @param map
	 * @return
	 */
	public String save(Map<String, Object> map) {
		//实体类
		Object oj = map.get("oj");
		//表名称
		String table = (String)map.get("table");
		//生成添加的sql语句。使用反射机制
		//步骤：使用反射机制加载这个类所有属性
		//INSERT INTO `shop_user`
		SQL sql = new SQL() {
			{
				INSERT_INTO(table);
				VALUES(ReflectionUtils.fatherAndSonField(oj),ReflectionUtils.fatherAndSonFieldValue(oj));
			}
		};
		return sql.toString();
	}

}
