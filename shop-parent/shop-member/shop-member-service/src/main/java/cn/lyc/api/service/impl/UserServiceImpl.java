
package cn.lyc.api.service.impl;

import java.util.UUID;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.lyc.api.service.IUserService;
import cn.lyc.common.api.ShopResult;
import cn.lyc.common.constants.Constants;
import cn.lyc.common.constants.DBTableName;
import cn.lyc.common.constants.MQInterfaceType;
import cn.lyc.common.redis.BaseRedisService;
import cn.lyc.dao.IUserDAO;
import cn.lyc.entity.User;
import cn.lyc.mq.producer.RegisterMailboxProducer;
import cn.lyc.utils.DateUtils;
import cn.lyc.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController // 响应以json格式输出
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDAO userDao;

	@Value("${messages.queue}")
	private String MESSAGES_QUEUE;
	
	@Autowired
	private RegisterMailboxProducer registerMailboxProducer;
	
	@Autowired
	private BaseRedisService baseRedisService;

	@Override
	// @RequestBody表示请求以json格式传入
	public ShopResult regist(@RequestBody User user) {
		if (StringUtils.isEmpty(user.getUserName())) {
			return ShopResult.build(400, "用户名称不能为空!");
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			return ShopResult.build(400, "密码不能为空!");
		}
		try {
			user.setCreated(DateUtils.getTimestamp());
			user.setUpdated(DateUtils.getTimestamp());
			String phone = user.getPhone();
			String password = user.getPassword();
			user.setPassword(md5PassSalt(phone, password));
			userDao.save(user, DBTableName.TABLE_MB_USER);

			// 队列
			Destination activeMQQueue = new ActiveMQQueue(MESSAGES_QUEUE);
			// 组装报文格式
			String mailMessage = mailMessage(user.getEmail(), user.getUserName());
			log.info("###regist() 注册发送邮件报文mailMessage:{}", mailMessage); // mq
			registerMailboxProducer.send(activeMQQueue, mailMessage);

			return ShopResult.ok();
		} catch (Exception e) {
			log.error("###regist() ERROR:", e);
			return ShopResult.build(500, "注册失败");
		}
	}

	public String md5PassSalt(String phone, String password) {
		String newPass = MD5Util.MD5(phone + password);
		return newPass;

	}

	
	/**
{
 "header":{
   "interfaceType":"接口类型"
  }
 "content":{
     "mail":"",
     "username":""
  }
}
	 * @param email
	 * @param userName
	 * @return
	 */
	private String mailMessage(String email, String userName) {
		JSONObject root = new JSONObject();
		JSONObject header = new JSONObject();
		header.put("interfaceType", MQInterfaceType.SMS_MAIL);
		JSONObject content = new JSONObject();
		content.put("mail", email);
		content.put("userName", userName);
		root.put("header", header);
		root.put("content", content);
		return root.toJSONString();
	}

	@Override
	public ShopResult login(User user) {
		//往数据库进行查找数据
		String phone = user.getPhone();
		String password = user.getPassword();
		String newPassword = md5PassSalt(phone, password);
		User userPhoneAndPwd = userDao.getUserPhoneAndPwd(phone, newPassword);
		if(userPhoneAndPwd == null) {
			return ShopResult.build(400, "账号或密码错误");
		}
		//生成对应的token
		String token = UUID.randomUUID().toString();
		Integer userId = userPhoneAndPwd.getId();
		//key为自定义令牌,用户的userId作为value 存放在redis中
		baseRedisService.set(token, userId + "", Constants.USER_TOKEN_TERMVALIDITY);
		return new ShopResult(token);
	}
	
	

}
