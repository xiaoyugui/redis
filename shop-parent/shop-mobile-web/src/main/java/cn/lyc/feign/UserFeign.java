package cn.lyc.feign;

import org.springframework.cloud.openfeign.FeignClient;

import cn.lyc.api.service.IUserService;

@FeignClient("member")
public interface UserFeign extends IUserService{

}
