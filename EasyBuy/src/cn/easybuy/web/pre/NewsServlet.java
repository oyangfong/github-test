package cn.easybuy.web.pre;
import cn.easybuy.service.news.NewsService;
import cn.easybuy.service.news.NewsServiceImpl;
import cn.easybuy.service.product.ProductCategoryService;
import cn.easybuy.service.product.ProductService;
import cn.easybuy.web.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * Created by bdqn on 2016/4/25.
 */
@WebServlet(urlPatterns = {"/News"}, name = "News")
public class NewsServlet extends AbstractServlet {

    private static NewsService newsService;
    /*static{
    	ApplicationContext context=new ClassPathXmlApplicationContext("app-config.xml");
    	newsService=(NewsService) context.getBean("newsService");
    }*/
    public void init() throws ServletException {
    	ApplicationContext context=new ClassPathXmlApplicationContext("app-config.xml");
    	newsService=(NewsService) context.getBean("newsService");
    }

    @Override
    public Class getServletClass() {
        return NewsServlet.class;
    }

    public String index(HttpServletRequest request, HttpServletResponse response)throws Exception{
        return "/pre/newsList";
    }

}
