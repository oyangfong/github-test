package cn.easybuy.dao.order;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.easybuy.entity.UserAddress;
import cn.easybuy.param.UserAddressParam;

/**
 * Created by bdqn on 2016/5/12.
 * addObject(UserAddress userAddress)
 * getRowList(params)
 */
public interface UserAddressMapper{
	
	public List<UserAddress> queryUserAddressList(UserAddressParam param);
	
	public Integer add(UserAddress userAddress);
	
	public UserAddress getUserAddressById(@Param("addressId") Integer addressId);

}
