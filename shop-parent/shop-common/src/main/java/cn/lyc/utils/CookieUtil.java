package cn.lyc.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	
	private CookieUtil() {
		
	}
	
	/**
	 * 添加cookie
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		if(maxAge > 0) {
			cookie.setMaxAge(maxAge);
		}
		response.addCookie(cookie);
	}

}
