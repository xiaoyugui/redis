package cn.lyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.lyc.adapter.MessageAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SMSMailboxService implements MessageAdapter{
	
	@Autowired
	private JavaMailSender mailSender;//自动注入的Bean
	@Value("${spring.mail.username}")
	private String emailFrom;

	@Override
	public void distribute(JSONObject jsonObject) {
		String mail=jsonObject.getString("mail");
		String userName=jsonObject.getString("userName");
		log.info("###消费者收到的消息... mail:{},userName:{}",mail,userName);
		//发送邮件
		//开始发送邮件
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(emailFrom);
		message.setTo(mail);
		message.setSubject("欢迎" + userName +",商城会员注册成功");
		message.setText("恭喜您"+ userName + ",成为商城的新用户！谢谢您的光临！");
		log.info("###发送短信邮箱 mail:{}", mail);
		mailSender.send(message);
	}

}
