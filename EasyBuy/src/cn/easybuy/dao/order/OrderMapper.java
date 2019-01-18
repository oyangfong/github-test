package cn.easybuy.dao.order;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.easybuy.entity.News;
import cn.easybuy.entity.Order;
import cn.easybuy.param.NewsParams;
import cn.easybuy.param.OrderParams;

/***
 * 订单处理的dao层
 * getRowCount
 * getRowList(Params params)
 * getById(Integer id)
 * addObject(Params params)
 */
public interface OrderMapper{

	public void add(Order order) ;

	public void deleteById(Integer id);
	
	public Order getOrderById(Integer id) ;
	
	public List<Order> getOrderList(@Param("userId") Integer userId,@Param("from") Integer currentPageNo,@Param("pageSize") Integer pageSize) ;
	
	public Integer count(@Param("userId") Integer userId);
	
	//通过snumber获得订单id
	Integer getIdBySNumber(@Param("snumber") String snumber);
}
