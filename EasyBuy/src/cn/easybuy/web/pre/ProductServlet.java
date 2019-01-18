package cn.easybuy.web.pre;

import cn.easybuy.entity.Product;
import cn.easybuy.service.product.ProductCategoryService;
import cn.easybuy.service.product.ProductService;
import cn.easybuy.service.product.ProductCategoryServiceImpl;
import cn.easybuy.service.product.ProductServiceImpl;
import cn.easybuy.service.user.UserService;
import cn.easybuy.utils.EmptyUtils;
import cn.easybuy.utils.Pager;
import cn.easybuy.utils.ProductCategoryVo;
import cn.easybuy.web.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bdqn on 2016/4/26.
 */
@WebServlet(urlPatterns = {"/Product"}, name = "Product")
public class ProductServlet extends AbstractServlet {
	//指定solr服务器的地址  
    private final static String SOLR_URL = "http://192.168.128.141:8983/solr/mycore"; 
    private static ProductService productService;
    private static ProductCategoryService productCategoryService;
    /*static{
    	ApplicationContext context=new ClassPathXmlApplicationContext("app-config.xml");
    	productService=(ProductService) context.getBean("productService");
    	productCategoryService=(ProductCategoryService) context.getBean("productCategoryService");
    }*/

    public void init() throws ServletException {
    	ApplicationContext context=new ClassPathXmlApplicationContext("app-config.xml");
    	productService=(ProductService) context.getBean("productService");
    	productCategoryService=(ProductCategoryService) context.getBean("productCategoryService");
    }

    /**
     * 查询商品列表
     *
     * @param request
     * @param response
     * @return
     */
    public String queryProductList(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String category = request.getParameter("category");
        String levelStr = request.getParameter("level");
        String currentPageStr = request.getParameter("currentPage");
        String keyWord = request.getParameter("keyWord");
        //获取页大小
        String pageSizeStr = request.getParameter("pageSize");
        int rowPerPage = EmptyUtils.isEmpty(pageSizeStr) ? 20:Integer.parseInt(pageSizeStr);
        int currentPage = EmptyUtils.isEmpty(currentPageStr) ? 1 : Integer.parseInt(currentPageStr);
       /* int  level=EmptyUtils.isNotEmpty(levelStr)?Integer.parseInt(levelStr):0;*/
        HttpSolrClient client = new  HttpSolrClient(SOLR_URL);
        //SolrClient client=new HttpSolrClient(SOLR_URL);
        
        //创建查询对象
        SolrQuery query = new SolrQuery();
        //q 查询字符串，如果查询所有*:*
        query.set("q", "name:"+keyWord+"");
        //fq 过滤条件，过滤是基于查询结果中的过滤
       /* query.set("fq", "product_catalog_name:幽默杂货");*/
        //sort 排序，请注意，如果一个字段没有被索引，那么它是无法排序的
//        query.set("sort", "product_price desc");
        //start row 分页信息，与mysql的limit的两个参数一致效果
        query.setStart(0);
        query.setRows(3);
        //fl 查询哪些结果出来，不写的话，就查询全部，所以我这里就不写了
//        query.set("fl", "");
        //df 默认搜索的域
        query.set("df", "keywords");
        
        //======高亮设置===
        //开启高亮
        query.setHighlight(true);
        //高亮域
        query.addHighlightField("name");
        //前缀
        query.setHighlightSimplePre("<span style='color:red'>");
        //后缀
        query.setHighlightSimplePost("</span>");
        
        
        //执行搜索
        QueryResponse queryResponse = client.query(query);
        //搜索结果
        SolrDocumentList productList = queryResponse.getResults();
        //查询出来的数量
        long total = productList.getNumFound();
        System.out.println("总查询出:" + total + "条记录");
        
        /*//遍历搜索记录
        //获取高亮信息
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        for (SolrDocument solrDocument : results) {
            System.out.println("id:" + solrDocument.get("id"));
            System.out.println("名称 :" + solrDocument.get("name"));
            System.out.println("描述:" + solrDocument.get("description"));

            //输出高亮 
            Map<String, List<String>> map = highlighting.get(solrDocument.get("id"));
            List<String> list = map.get("name");
            if(list != null && list.size() > 0){
                System.out.println(list.get(0));
            }
        }*/
        
        client.close();
       /* int total = productService.count(keyWord, EmptyUtils.isEmpty(category)?null:Integer.valueOf(category), level);*/
       /* Pager pager = new Pager(total, rowPerPage, currentPage);
        pager.setUrl("/Product?action=queryProductList&level="+level+"&category="+(EmptyUtils.isEmpty(category)?"":category));
        */
        List<ProductCategoryVo> productCategoryVoList = productCategoryService.queryAllProductCategoryList();
       /* List<Product> productList = productService.getProductList(currentPage, rowPerPage, keyWord, EmptyUtils.isEmpty(category)?null:Integer.valueOf(category), level);*/
        
        request.setAttribute("productList", productList);
        /*request.setAttribute("pager", pager);*/
        request.setAttribute("total", total);
        request.setAttribute("keyWord", keyWord);
        request.setAttribute("productCategoryVoList", productCategoryVoList);
        return "/pre/product/queryProductList";
    }
    /**
     *
     * @param request
     * @param response
     * @return
     */
    public String queryProductDeatil(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String id = request.getParameter("id");
        Product product = productService.getProductById(Integer.valueOf(id));
        List<ProductCategoryVo> productCategoryVoList = productCategoryService.queryAllProductCategoryList();
        request.setAttribute("product", product);
        request.setAttribute("productCategoryVoList", productCategoryVoList);
        addRecentProduct(request,product);
        return "/pre/product/productDeatil";
    }
    /**
     * 查询最近商品
     * @return
     */
    private List<Product> queryRecentProducts(HttpServletRequest request)throws Exception{
        HttpSession session=request.getSession();
        List<Product> recentProducts= (List<Product>) session.getAttribute("recentProducts");
        if(EmptyUtils.isEmpty(recentProducts)){
            recentProducts=new ArrayList<Product>();
        }
        return recentProducts;
    }
    /**
     * 添加最近浏览商品
     * @param request
     * @param product
     */
    private void addRecentProduct(HttpServletRequest request,Product product)throws Exception{
        HttpSession session=request.getSession();
        List<Product> recentProducts=queryRecentProducts(request);
        //判断是否满了
        if(recentProducts.size()>0 &&  recentProducts.size()==10){
          recentProducts.remove(0);
        }
        recentProducts.add(recentProducts.size(),product);
        session.setAttribute("recentProducts",recentProducts);
    }

    @Override
    public Class getServletClass() {
        return ProductServlet.class;
    }
}
