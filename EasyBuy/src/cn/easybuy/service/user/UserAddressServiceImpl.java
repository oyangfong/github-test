package cn.easybuy.service.user;

import cn.easybuy.dao.order.OrderDetailMapper;
import cn.easybuy.dao.order.OrderMapper;
import cn.easybuy.dao.order.UserAddressMapper;
import cn.easybuy.dao.product.ProductMapper;
import cn.easybuy.entity.UserAddress;
import cn.easybuy.param.UserAddressParam;
import cn.easybuy.utils.DataSourceUtil;
import cn.easybuy.utils.Params;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

/**
 * Created by bdqn on 2016/5/12.
 */
@Service("userAddressService")
public class UserAddressServiceImpl implements UserAddressService {
	
	@Resource
	private UserAddressMapper userAddressMapper;
	
    /**
     * 查询用户地址列表
     *
     * @param id
     * @return
     * @throws Exception
     */
    public List<UserAddress> queryUserAdressList(Integer id) throws Exception{
        List<UserAddress> userAddressList = null;
        try {
            UserAddressParam params = new UserAddressParam();
            params.setUserId(id);
            userAddressList = userAddressMapper.queryUserAddressList(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userAddressList;
    }
    /**
     * 添加用户地址
     *
     * @param id
     * @param address
     * @return
     */
    @Override
    public Integer addUserAddress(Integer id, String address,String remark) {
        Integer userAddressId = null;
        try {
            UserAddress userAddress=new UserAddress();
            userAddress.setUserId(id);
            userAddress.setAddress(address);
            userAddress.setRemark(remark);
            userAddressId = userAddressMapper.add(userAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userAddressId;
    }

    @Override
    public UserAddress getUserAddressById(Integer id) {
        UserAddress userAddress= null;
        try {
            userAddress = (UserAddress) userAddressMapper.getUserAddressById(id);
        }  catch (Exception e) {
            e.printStackTrace();
        }finally {
            return  userAddress;
        }
    }
}
