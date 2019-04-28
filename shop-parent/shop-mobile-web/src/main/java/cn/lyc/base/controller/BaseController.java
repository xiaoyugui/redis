package cn.lyc.base.controller;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.lyc.feign.UserFeign;

@Controller
public class BaseController {
	
	@Autowired
	private UserFeign userFeign;
	
	public String setError(HttpServletRequest request, String msg, String address) {
		request.setAttribute("error", msg);
		return address;
	}

}
