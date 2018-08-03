import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class Solr1 {
    @Test
    //查询所有
    public void query() throws SolrServerException {
        String url="http://localhost:8080/solr/products";
        HttpSolrServer httpSolrServer = new HttpSolrServer(url);
       //创建查询对象
        SolrQuery solrQuery = new SolrQuery();
        //查询所有
        solrQuery.setQuery("*:*");
        //执行查询,获得reponse对象
        QueryResponse response = httpSolrServer.query(solrQuery);
        //获得结果集
        SolrDocumentList documentList = response.getResults();
        //获得文档命中总数
        long numFound = documentList.getNumFound();
        System.out.println(numFound);
        //遍历
        for (SolrDocument document : documentList) {
            System.out.println(document.get("id"));
           /* System.out.println(document.get("product_catalog_name"));
            System.out.println(document.get("product_catalog"));*/
            System.out.println(document.get("product_price"));
            System.out.println(document.get("product_name"));
        }
    }
}
