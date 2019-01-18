package cn.easybuy.web.pre;
import cn.easybuy.service.product.ProductCategoryService;
import cn.easybuy.service.product.ProductService;
import cn.easybuy.service.product.ProductServiceImpl;
import cn.easybuy.web.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by bdqn on 2016/4/21.
 */
@WebServlet(urlPatterns = {"/productCategory"},name = "productCategory")
public class ProductCategoryServlet extends AbstractServlet{

    private static ProductService productService;
    /*static{
    	ApplicationContext context=new ClassPathXmlApplicationContext("app-config.xml");
    	productService=(ProductService) context.getBean("productService");
    }*/
    public void init() throws ServletException {
    	ApplicationContext context=new ClassPathXmlApplicationContext("app-config.xml");
    	productService=(ProductService) context.getBean("productService");
    }

    @Override
    public Class getServletClass() {
        return ProductCategoryServlet.class;
    }
}
