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
	     //ָ��solr�������ĵ�ַ  
	     private final static String SOLR_URL = "http://192.168.128.141:8983/solr/mycore";  
	     /**
	      * ������������ĵ�
	     * @throws IOException 
	     * @throws SolrServerException 
	      */
	     public void addDoc() throws SolrServerException, IOException{
	    	//����
	         HttpSolrClient client = new  HttpSolrClient(SOLR_URL);
	         SolrInputDocument doc = new SolrInputDocument();
	         doc.addField("id", "6");
	         doc.addField("name", "����ӱ");
	         doc.addField("description", "����һ������Ա");
	         System.out.println("�����ɹ�");
	         client.add(doc);
	         client.commit();
	         client.close();
	     }


	    
	     /**
	      * ��ѯ
	     * @throws Exception 
	      */
	     public void querySolr() throws Exception{
	    	 HttpSolrClient client = new  HttpSolrClient(SOLR_URL);
	         
	         //������ѯ����
	         SolrQuery query = new SolrQuery();
	         //q ��ѯ�ַ����������ѯ����*:*
	         query.set("q", "description:���������Ա");
	         //fq ���������������ǻ��ڲ�ѯ����еĹ���
	        /* query.set("fq", "product_catalog_name:��Ĭ�ӻ�");*/
	         //sort ������ע�⣬���һ���ֶ�û�б���������ô�����޷������
//	         query.set("sort", "product_price desc");
	         //start row ��ҳ��Ϣ����mysql��limit����������һ��Ч��
	         query.setStart(0);
	         query.setRows(3);
	         //fl ��ѯ��Щ�����������д�Ļ����Ͳ�ѯȫ��������������Ͳ�д��
//	         query.set("fl", "");
	         //df Ĭ����������
	         query.set("df", "keywords");
	         
	         //======��������===
	         //��������
	         query.setHighlight(true);
	         //������
	         query.addHighlightField("description");
	         //ǰ׺
	         query.setHighlightSimplePre("<span style='color:red'>");
	         //��׺
	         query.setHighlightSimplePost("</span>");
	         
	         
	         //ִ������
	         QueryResponse queryResponse = client.query(query);
	         //�������
	         SolrDocumentList results = queryResponse.getResults();
	         //��ѯ����������
	         long numFound = results.getNumFound();
	         System.out.println("�ܲ�ѯ��:" + numFound + "����¼");
	         
	         //����������¼
	         //��ȡ������Ϣ
	         Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
	         for (SolrDocument solrDocument : results) {
	             System.out.println("id:" + solrDocument.get("id"));
	             System.out.println("���� :" + solrDocument.get("name"));
	             System.out.println("����:" + solrDocument.get("description"));

	             //������� 
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
	         
	         //1.ɾ��һ��
	         client.deleteById("6");
	         
	         //2.ɾ�����
	        /* List<String> ids = new ArrayList<>();
	         ids.add("1");
	         ids.add("2");
	         client.deleteById(ids);*/
	         
	         //3.���ݲ�ѯ����ɾ������,���������ֻ����һ���������Զ������
	         //client.deleteByQuery("id:7");
	         
	         //4.ɾ��ȫ����ɾ�����ɻָ�
	         //client.deleteByQuery("*:*");
	         
	         //һ��Ҫ�ǵ��ύ������������
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
	


