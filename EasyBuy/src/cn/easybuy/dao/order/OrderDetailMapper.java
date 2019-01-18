package cn.easybuy.dao.order;
import cn.easybuy.entity.News;
import cn.easybuy.entity.OrderDetail;
import cn.easybuy.param.NewsParams;
import cn.easybuy.param.OrderDetailParam;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 订单详细
 * Created by bdqn on 2016/5/8.
 */
public interface OrderDetailMapper{

    public void add(OrderDetail detail) throws Exception;

	public void deleteById(OrderDetail detail) throws Exception;
	
	public OrderDetail getOrderDetailById(Integer id)throws Exception; 
	
	public List<OrderDetail> getOrderDetailList(@Param("orderId") Integer orderId)throws Exception;
	
	public Integer queryOrderDetailCount()throws Exception; 
}
