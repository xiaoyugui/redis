
package cn.lyc.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.lyc.common.mybatis.IBaseDAO;
import cn.lyc.entity.User;

@Mapper
public interface IUserDAO extends IBaseDAO {
	
	
	@Select("select ID,USERNAME,PASSWORD,phone,EMAIL, created,updated from shop_user WHERE PHONE=#{phone}")
	public User getUserPhoneAndPwd(@Param("phone") String userName, @Param("password") String password);
	
	@Select("select ID,USERNAME,PASSWORD,phone,EMAIL, created,updated from shop_user WHERE id=#{id}")
	public User getUserInfo(@Param("id") Long id);

}
