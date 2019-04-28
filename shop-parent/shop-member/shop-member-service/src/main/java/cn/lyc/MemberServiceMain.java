package cn.lyc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.lyc.dao")
public class MemberServiceMain {

	public static void main(String[] args) {
		SpringApplication.run(MemberServiceMain.class);

	}

}
