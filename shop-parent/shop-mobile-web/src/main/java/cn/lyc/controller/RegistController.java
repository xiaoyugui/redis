package cn.lyc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.lyc.base.controller.BaseController;
import cn.lyc.common.api.ShopResult;
import cn.lyc.entity.User;
import cn.lyc.feign.UserFeign;

@Controller
public class RegistController extends BaseController{
	
	private static final String LOCAREGIST = "locaRegist";
	private static final String LGOIN = "login";
	
	@Autowired
	private UserFeign userFeign;
	
	@RequestMapping("/locaRegist")
	public String locaRegist() {
		return LOCAREGIST;
	}
	
	@RequestMapping("/regist")
	public String regist(User user,HttpServletRequest request) {
		try {
			ShopResult registMap = userFeign.regist(user);
			Integer code = (Integer) registMap.getStatus();
			if(!code.equals(200)) {
				String msg = (String) registMap.getMsg();
				return setError(request, msg, LOCAREGIST);
			}
			//注册成功
			return LGOIN;
		} catch (Exception e) {
			return setError(request, "注册失败！", LOCAREGIST);
		}
	}

	
	
}
