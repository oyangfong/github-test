package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class SolrTests {
	     //指定solr服务器的地址  
	     private final static String SOLR_URL = "http://192.168.128.141:8983/solr/mycore";  
	     /**
	      * 往索引库添加文档
	     * @throws IOException 
	     * @throws SolrServerException 
	      */
	     public void addDoc() throws SolrServerException, IOException{
	    	//创建
	         HttpSolrClient client = new  HttpSolrClient(SOLR_URL);
	         SolrInputDocument doc = new SolrInputDocument();
	         doc.addField("id", "6");
	         doc.addField("name", "赵丽颖");
	         doc.addField("description", "这是一个好演员");
	         System.out.println("新增成功");
	         client.add(doc);
	         client.commit();
	         client.close();
	     }


	    
	     /**
	      * 查询
	     * @throws Exception 
	      */
	     public void querySolr() throws Exception{
	    	 HttpSolrClient client = new  HttpSolrClient(SOLR_URL);
	         
	         //创建查询对象
	         SolrQuery query = new SolrQuery();
	         //q 查询字符串，如果查询所有*:*
	         query.set("q", "description:香港著名演员");
	         //fq 过滤条件，过滤是基于查询结果中的过滤
	        /* query.set("fq", "product_catalog_name:幽默杂货");*/
	         //sort 排序，请注意，如果一个字段没有被索引，那么它是无法排序的
//	         query.set("sort", "product_price desc");
	         //start row 分页信息，与mysql的limit的两个参数一致效果
	         query.setStart(0);
	         query.setRows(3);
	         //fl 查询哪些结果出来，不写的话，就查询全部，所以我这里就不写了
//	         query.set("fl", "");
	         //df 默认搜索的域
	         query.set("df", "keywords");
	         
	         //======高亮设置===
	         //开启高亮
	         query.setHighlight(true);
	         //高亮域
	         query.addHighlightField("description");
	         //前缀
	         query.setHighlightSimplePre("<span style='color:red'>");
	         //后缀
	         query.setHighlightSimplePost("</span>");
	         
	         
	         //执行搜索
	         QueryResponse queryResponse = client.query(query);
	         //搜索结果
	         SolrDocumentList results = queryResponse.getResults();
	         //查询出来的数量
	         long numFound = results.getNumFound();
	         System.out.println("总查询出:" + numFound + "条记录");
	         
	         //遍历搜索记录
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
	         }
	         
	         client.close();
	     }
	     
	    
	     public void deleteDocumentById() throws Exception{
	         HttpSolrClient client = new  HttpSolrClient(SOLR_URL);
	         
	         //1.删除一个
	         client.deleteById("6");
	         
	         //2.删除多个
	        /* List<String> ids = new ArrayList<>();
	         ids.add("1");
	         ids.add("2");
	         client.deleteById(ids);*/
	         
	         //3.根据查询条件删除数据,这里的条件只能有一个，不能以逗号相隔
	         //client.deleteByQuery("id:7");
	         
	         //4.删除全部，删除不可恢复
	         //client.deleteByQuery("*:*");
	         
	         //一定要记得提交，否则不起作用
	         client.commit();
	         client.close();
	 }


	     public static void main(String[] args) throws Exception {
	         SolrTests solr = new SolrTests();
	         
	         //solr.addDoc();
	         //solr.deleteDocumentById();
	         //solr.querySolr();
	    }
	}
	


