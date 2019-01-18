package cn.easybuy.service.product;
import java.sql.Connection;
import java.util.List;

import javax.annotation.Resource;

import cn.easybuy.dao.product.ProductMapper;
import cn.easybuy.utils.DataSourceUtil;
import cn.easybuy.utils.Pager;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.easybuy.entity.Product;
/**
 * 商品的业务类
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {
	
	private Logger logger = Logger.getLogger(ProductServiceImpl.class);
	
	@Resource
	private ProductMapper productMapper;
	
	@Override
	public boolean add(Product product) {
		Integer count=0;
		try {
			count=productMapper.add(product);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return count>0;
		}
	}

	@Override
	public boolean update(Product product) {
		Integer count=0;
		try {
			count=productMapper.update(product);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return count>0;
		}
	}

	@Override
	public boolean deleteProductById(Integer productId) {
		Integer count=0;
		try {
			count=productMapper.deleteProductById(productId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return count>0;
		}
	}

	@Override
	public Product getProductById(Integer productId) {
		Product product=null;
		try {
			product=productMapper.getProductById(productId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return product;
		}
	}

	@Override
	public List<Product> getProductList(Integer currentPageNo,Integer pageSize,String proName, Integer categoryId, Integer level) {
		List<Product> productList=null;
		try {
			
			int total = count(proName,categoryId,level);
			Pager pager = new Pager(total, pageSize, currentPageNo);
			productList=productMapper.getProductList((pager.getCurrentPage() - 1) * pager.getRowPerPage(),pager.getRowPerPage(),proName,categoryId,level);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return productList;
		}
	}

	@Override
	public int count(String proName,Integer categoryId, Integer level) {
		Integer count=0;
		try {
			count=productMapper.queryProductCount(proName,categoryId,level);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return count;
		}
	}

	@Override
	public boolean updateStock(Integer productId, Integer stock) {
		Connection connection = null;
		Integer count=0;
		try {
			connection = DataSourceUtil.openConnection();
			count=productMapper.updateStock(productId,stock);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
			return count>0;
		}
	}
   
}
