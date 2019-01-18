package cn.easybuy.service.order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import cn.easybuy.dao.order.*;

import cn.easybuy.dao.product.ProductMapper;
import cn.easybuy.utils.*;
import cn.easybuy.entity.Order;
import cn.easybuy.entity.OrderDetail;
import cn.easybuy.entity.User;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

	@Resource
	private OrderMapper orderMapper;
	@Resource
	private OrderDetailMapper orderDetailMapper;
	@Resource
	private ProductMapper productMapper;
    /**
     * 结算购物车物品包含以下步骤：
     * 1.生成订单
     * 2.生成订单明细
     * 3.更新商品表，减库存
     * 注意加入事物的控制
     */

    @Override
    public Order payShoppingCart(ShoppingCart cart, User user, String address) {
        Order order = new Order();
        try {
            /*ProductMapper productDao = new ProductDaoImpl(connection);*/
            //增加订单
            order.setUserId(user.getId());
            order.setLoginName(user.getLoginName());
            order.setUserAddress(address);
            order.setCreateTime(new Date());
            order.setCost(cart.getTotalCost());
            order.setSerialNumber(StringUtils.randomUUID());
            order.setCreateTime(new Date());
            orderMapper.add(order);
            int id1=orderMapper.getIdBySNumber(order.getSerialNumber());
            //增加订单对应的明细信息
            for (ShoppingCartItem item : cart.getItems()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(id1);
                orderDetail.setCost(item.getCost());
                orderDetail.setProduct(item.getProduct());
                orderDetail.setQuantity(item.getQuantity());
                orderDetail.setProductId(item.getProduct().getId());
                orderDetailMapper.add(orderDetail);
                
                //更新商品表的库存
                productMapper.updateStock(item.getProduct().getId(), item.getQuantity());
            }
        } catch (Exception e) {
            e.printStackTrace();
            order = null;
        } 
        return order;
    }

    @Override
    public List<Order> getOrderList(Integer userId, Integer currentPageNo, Integer pageSize) {
        List<Order> orderList = new ArrayList<Order>();
        try {
        	int total = count(userId);
    		Pager pager = new Pager(total, pageSize, currentPageNo);
            orderList = orderMapper.getOrderList(userId, (pager.getCurrentPage() - 1) * pager.getRowPerPage(), pager.getRowPerPage());
            for(Order order:orderList){
            	order.setOrderDetailList(getOrderDetailList(order.getId()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return orderList;
        }
    }

    @Override
    public int count(Integer userId) {
        Integer count=0;
        try {
            count=orderMapper.count(userId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return count;
        }
    }

    /**
     * 调用dao接口：OrderDetailMapper的方法实现
     */
    @Override
    public List<OrderDetail> getOrderDetailList(Integer orderId) {
        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
        try {
            orderDetailList = orderDetailMapper.getOrderDetailList(orderId);
            for (OrderDetail orderDetail : orderDetailList) {
            	orderDetail.setProduct(productMapper.getProductById(orderDetail.getProductId()));
			}
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return orderDetailList;
        }
    }
}
