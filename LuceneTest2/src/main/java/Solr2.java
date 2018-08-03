import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class Solr2 {
    @Test
    public void query() throws SolrServerException {
        //指定远程服务器索引库地址
        String url="http://localhost:8080/solr/products";
        //连接solr远程索引库
        HttpSolrServer httpSolrServer = new HttpSolrServer(url);
          //创建封装查询条件对象
        SolrQuery solrQuery = new SolrQuery();
        //设置主查询条件
        solrQuery.set("q", "*:*");
        // solrQuery.setQuery("浴巾");//在配置文件中设置了复制域
        //设置过滤查询条件
        //查询商品类别是时尚卫浴
        solrQuery.addFilterQuery("product_catalog_name: 魅力女人");
        //过滤查询，价格是20元以上
      solrQuery.addFilterQuery("product_price:[* TO 20]");
        //sort 排序查询
        solrQuery.setSort("product_price", SolrQuery.ORDER.asc);
        //分页查询
        solrQuery.setStart(2);
        solrQuery.setRows(5);
        //字段映射查询
        //字段之间是空格，或者逗号都可
         solrQuery.setFields("product_price,product_catalog_name");
        //设置默认查询字段,缺省字段一般设置复制域
         solrQuery.set("df","product_keywords");
        //高亮查询
     //开启高亮
        solrQuery.setHighlight(true);
        //指定高亮字段
        solrQuery.addHighlightField("product_name");
        //设置高亮前缀
        solrQuery.setHighlightSimplePre("<font color='red'>");
        //设置的高亮后置
        solrQuery.setHighlightSimplePost("</font>");
        this.exeQuery(solrQuery);
    }

    private void exeQuery(SolrQuery solrQuery) throws SolrServerException {
         String url="http://localhost:8080/solr/products";
        HttpSolrServer httpSolrServer = new HttpSolrServer(url);
        QueryResponse response = httpSolrServer.query(solrQuery);
        SolrDocumentList results = response.getResults();
        //获取命中总记录数
        long numFound = results.getNumFound();
        System.out.println("命中总记录数："+numFound);
        //循环文档集合
        for (SolrDocument document : results) {
                   //获取id
            String id = (String) document.get("id");
            System.out.println("文档域id："+id);
            //商品标题
     /*       String product_name = (String) document.get("product_name");
              //获取高亮
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
               //第一个map的key就是文档id
            Map<String, List<String>> stringListMap = highlighting.get("id");
             //第二个map的key就是高亮字段
            List<String> hlist = stringListMap.get("product_name");
            //判断高亮是否存在
            if(hlist!=null && hlist.size()>0){
                product_name = hlist.get(0);
            }*/
           // System.out.println("商品名称：" + product_name);

            Float productPrice = (Float) document.get("product_price");
            System.out.println("商品价格：" + productPrice);
            String productDescription = (String) document
                    .get("product_description");
            System.out.println("商品描述：" + productDescription);
            String productPicture = (String) document.get("product_picture");
            System.out.println("商品图片：" + productPicture);
            String productCatalogName = (String) document
                    .get("product_catalog_name");
            System.out.println("商品类别：" + productCatalogName);
        }
    }
}
