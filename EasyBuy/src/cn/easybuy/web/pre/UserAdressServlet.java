package cn.easybuy.web.pre;

import cn.easybuy.service.user.UserAddressService;
import cn.easybuy.service.user.UserAddressServiceImpl;
import cn.easybuy.web.AbstractServlet;

import javax.servlet.ServletException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by bdqn on 2016/5/12.
 */
public class UserAdressServlet extends AbstractServlet {

    private static UserAddressService userAddressService;

    /*static{
    	ApplicationContext context=new ClassPathXmlApplicationContext("app-config.xml");
    	userAddressService=(UserAddressService)context.getBean("userAddressService");
    }*/
    
    public void init() throws ServletException {
    	ApplicationContext context=new ClassPathXmlApplicationContext("app-config.xml");
    	userAddressService=(UserAddressService)context.getBean("userAddressService");
    }

    @Override
    public Class getServletClass() {
        return UserAdressServlet.class;
    }
}
