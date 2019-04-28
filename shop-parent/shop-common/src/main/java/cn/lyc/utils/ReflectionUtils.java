package cn.lyc.utils;

import java.io.ObjectInputStream.GetField;
import java.lang.reflect.Field;
import java.sql.Timestamp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReflectionUtils {
	
	/**
	 * 功能描述：（封装当前类和父类的所有属性 拼接属性sql）
	 */
	
	public static String fatherAndSonField(Object oj) {
		if(oj == null) {
			return null;
		}
		//获取class 文件
		Class classInfo = oj.getClass();
		//获取当前类属性sql
		Field[] sonFields = classInfo.getDeclaredFields();
		String s1 = getField(sonFields);
		Field[] panretFields = classInfo.getSuperclass().getDeclaredFields();
		String s2 = getField(panretFields);
		return s1 + "," + s2;
	}
	

	/**
	 * 功能描述：（获取到属性值）
	 */
	public static String fatherAndSonFieldValue(Object oj) {
		if(oj == null) {
			return null;
		}
		//获取class文件
		Class classInfo = oj.getClass();
		//获取当前类属性sql
		Field[] sonFields = classInfo.getDeclaredFields();
		String s1 = getFieldValue(oj, sonFields);
		Field[] panretFields = classInfo.getSuperclass().getDeclaredFields();
		String s2 = getFieldValue(oj, panretFields);
		return s1 + "," + s2;
	}
	


	private static String getField(Field[] declaredFields) {
		StringBuffer sf = new StringBuffer();
		for (int i = 0; i < declaredFields.length; i++) {
			sf.append(declaredFields[i].getName());
			if(i < declaredFields.length - 1) {
				sf.append(",");
			}
		}
		return sf.toString();
	}
	
	private static String getFieldValue(Object oj, Field[] declaredFields) {
		StringBuffer sf = new StringBuffer();
		for (int i = 0; i < declaredFields.length; i++) {
			//获取到属性值
			try {
				Field field = declaredFields[i];
				//运行操作私有属性
				field.setAccessible(true);
				Object value = field.get(oj);
				//标识类型是否为string类型
				boolean flag = false;
				if(value != null && (value instanceof String || value instanceof Timestamp)){
					flag = true;
				}
				if(flag) {
					sf.append("'");
					sf.append(value);
					sf.append("'");
				}else {
					sf.append(value);
				}
				if(i < declaredFields.length - 1) {
					sf.append(",");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return sf.toString();
	}

}
