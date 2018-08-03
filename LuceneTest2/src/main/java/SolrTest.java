import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;
//修改，如果有的话就修改，没有就添加，di一变就会添加
public class SolrTest {
    @Test
    public void SolrjTest() throws IOException, SolrServerException {
        //指定远程服务器地址
        //如果索引库名称是默认名称collection1 ，直接写索引仓库地址即可
        //如果索引名称被修改，连接地址后面必须格式索引库名称
        String url="http://localhost:8080/solr/products";
        //创建服务对象，连接远程solr服务
        HttpSolrServer httpSolrServer = new HttpSolrServer(url);
        //创建solrDocument对象
        SolrInputDocument document = new SolrInputDocument();
        //添加域字段所对应的值
        //向索引库对应域子段添加值时，必须保证此域子段在schema文件中存在
        document.addField("id","u9998888");
        document.addField("title","走过路过不要错过88");
        //添加索引库
        httpSolrServer.add(document);
        httpSolrServer.commit();

    }
    //删除索引库
    @Test
    public void Delete() throws IOException, SolrServerException {
   String url="http://localhost:8080/solr/products";
        HttpSolrServer httpSolrServer = new HttpSolrServer(url);
        //根据id删除
        //httpSolrServer.deleteById("u9998888");
        //根据查询语法删除
        httpSolrServer.deleteByQuery("title:走过路过不要错过");
        //httpSolrServer.deleteByQuery("*:*");
        httpSolrServer.commit();

    }

}
