package cn.easybuy.web.backend;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import cn.easybuy.entity.News;
import cn.easybuy.param.NewsParams;
import cn.easybuy.service.news.NewsService;
import cn.easybuy.service.product.ProductCategoryService;
import cn.easybuy.service.product.ProductService;
import cn.easybuy.service.news.NewsServiceImpl;
import cn.easybuy.service.order.CartService;
import cn.easybuy.service.order.OrderService;
import cn.easybuy.service.product.ProductServiceImpl;
import cn.easybuy.service.user.UserAddressService;
import cn.easybuy.service.user.UserService;
import cn.easybuy.utils.EmptyUtils;
import cn.easybuy.utils.Pager;
import cn.easybuy.web.AbstractServlet;

import java.util.List;

@WebServlet(urlPatterns = { "/admin/news" }, name = "adminNews")
public class AdminNewsServlet extends AbstractServlet {

	private static NewsService newsService;
	private static ProductService productService;

	public void init() throws ServletException {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"app-config.xml");
		productService = (ProductService) context.getBean("productService");
		newsService = (NewsService) context.getBean("newsService");
	}

	@Override
	public Class getServletClass() {
		return AdminNewsServlet.class;
	}

	/**
	 * 查询新闻列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String queryNewsList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String currentPageStr = request.getParameter("currentPage");
		String pageSize = request.getParameter("pageSize");
		int rowPerPage = EmptyUtils.isEmpty(pageSize) ? 10 : Integer
				.parseInt(pageSize);
		int currentPage = EmptyUtils.isEmpty(currentPageStr) ? 1 : Integer
				.parseInt(currentPageStr);
		int total = newsService.queryNewsCount(new NewsParams());
		Pager pager = new Pager(total, rowPerPage, currentPage);
		pager.setUrl("/admin/news?action=queryNewsList");
		NewsParams params = new NewsParams();
		params.openPage((pager.getCurrentPage() - 1) * pager.getRowPerPage(),
				pager.getRowPerPage());
		List<News> newsList = newsService.queryNewsList(params);
		request.setAttribute("newsList", newsList);
		request.setAttribute("pager", pager);
		request.setAttribute("menu", 7);
		return "/backend/news/newsList";
	}

	/**
	 * 查询新闻详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String newsDeatil(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		News news = newsService.findNewsById(id);
		request.setAttribute("news", news);
		request.setAttribute("menu", 7);
		return "/backend/news/newsDetail";
	}

}
