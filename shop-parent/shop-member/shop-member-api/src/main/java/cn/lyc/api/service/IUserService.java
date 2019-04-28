package cn.lyc.api.service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.lyc.common.api.ShopResult;
import cn.lyc.entity.User;

@RequestMapping("/member")
public interface IUserService {
	/**
	 *
	 * @methodDesc: 功能描述:(注册服务)
	 * @param: @param
	 *             User
	 * @param: @return
	 */
	@PostMapping("/regist")
	//@RequestMapping(name="/regist",method=RequestMethod.POST)
	public ShopResult regist(@RequestBody User user);
	
	//登录
	@PostMapping("/login")
	//@RequestMapping(name="/regist",method=RequestMethod.POST)
	public ShopResult login(@RequestBody User user);
}