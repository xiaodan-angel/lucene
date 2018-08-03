import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class QueryIndex {
    //需求：根据关键词检索索引库，搜索出文档对象
    @Test
    public void searchIndex() throws Exception{
        //指定存储索引库磁盘路径
        DirectoryReader reader = DirectoryReader.open(FSDirectory.open(new File("C:\\indexBase")));
        //创建索引索引库的核心对象
        IndexSearcher indexSearcher = new IndexSearcher(reader);
        //指定搜索关键词
       String qName="lucene";//lucene中的stringField域字段类型不分词，有索引，textFiled字段必须分词
        //此关键词大于最小分词单元，必须分词才能进行搜索
        //创建查询解析器
        //第二个参数：指定的搜索字段是title
      org.apache.lucene.queryparser.classic.QueryParser queryParser = new org.apache.lucene.queryparser.classic.QueryParser(Version.LUCENE_4_10_2, "desc", new StandardAnalyzer());
/*      //指定多个字段检索
        //MultiFieldQueryParser multiFieldQueryParser = new MultiFieldQueryParser(new String[]{"title", "content"}, new IKAnalyzer());
       //根据词条进行检索，不会切词了，因为词条已是最小单位。
        //TermQuery termQuery = new TermQuery(new Term("content", qName));
        //模糊检索
        Term term = new Term("content", qName + "*");//根据该字段的词条检索
        WildcardQuery wildcardQuery = new WildcardQuery(term);
        *//*语法：关键词+*  表示是以什么开始  *表示所有
        * *+关键词   以什么结尾
        * *+关键词+* 表示包含关键词查询
        * ？+关键词 零个或者一个开头 *//*
        //相似度检索,只针对英文有效,最多两个模糊
        String text="lucene";
        Term term1 = new Term("id",text);

        FuzzyQuery luXXne = new FuzzyQuery(term1,2);
        //数字范围搜索，查询id在5-15之间的数据，并包含5,15.

         NumericRangeQuery numericRangeQuery = NumericRangeQuery.newLongRange("id", 5L, 15L, true, true);
        //查询所有
        MatchAllDocsQuery matchAllDocsQuery = new MatchAllDocsQuery();
        //组合查询*/

        // 解析查询关键词，进行分词
        //把分词结构包装到查询对象query对象中
     Query parse = queryParser.parse(qName);
      //  BooleanQuery query = new BooleanQuery();
      //  NumericRangeQuery numericRangeQuery = NumericRangeQuery.newLongRange("id", 8L, 18L, true, true);
       // query.add(numericRangeQuery, BooleanClause.Occur.MUST);
   // NumericRangeQuery RangeQuery = NumericRangeQuery.newLongRange("id", 15L, 20L, true, true);
     // query.add(RangeQuery, BooleanClause.Occur.MUST_NOT);
        //根据分词后query对象查询索引库
        //第二个参数：指定查询结果数量  含义：查询匹配度最高的10条记录
   TopDocs docs= indexSearcher.search(parse,10);
        int num = docs.totalHits;//获取命中文档总记录
        System.out.println("命中总记录数："+num);
        ScoreDoc[] scoreDocs = docs.scoreDocs;
        //循环数组获取文档得分，文档id
        for (ScoreDoc doc : scoreDocs) {
            double score = doc.score;
            System.out.println("文档得分:"+score);
            int docId = doc.doc;
            System.out.println("文档id"+docId);
            //根据文档id获取唯一的文档对象
            Document mydoc = indexSearcher.doc(docId);
            //获取文档对象内容
            //根据域字段名称获取域字段对应值
            String id = mydoc.get("id");
            System.out.println("文档与字段id:"+id);
            //获取标题
            String title = mydoc.get("title");
            System.out.println("标题："+title);
            //获取标题
            String desc = mydoc.get("desc");
            System.out.println("描述:"+desc);
            //获取内容
            String content = mydoc.get("content");
            System.out.println("内容："+content);


        }


    }

@Test
public void testIndexDelete() throws IOException {
    //创建directory流对象
    FSDirectory directory = FSDirectory.open(new File("C:\\indexBase"));
    IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_2, null);
    //创建写入对象
    IndexWriter indexWriter = new IndexWriter(directory, config);
    //根据trem删除索引库
    indexWriter.deleteDocuments(new Term("title","lucene"));
    //全部删除
   // indexWriter.deleteAll();
    //释放资源
    indexWriter.close();

}
 @Test
    public void testIndexUpdate() throws IOException {
        //创建分词器
     IKAnalyzer ikAnalyzer = new IKAnalyzer();
     //创建directory流对象
     Directory directory = FSDirectory.open(new File("C:\\indexBase"));
     IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_2, ikAnalyzer);
     IndexWriter indexWriter = new IndexWriter(directory, config);
     Document document = new Document();
     document.add(new TextField("id","22", Field.Store.YES));
     document.add(new TextField("name","zhangsan", Field.Store.YES));
     //执行更新，会把所有符合条件的document删除，在新增
     indexWriter.updateDocument(new Term("desc","lucene是"),  document);//
     //释放资源
     indexWriter.commit();
     indexWriter.close();



 }



}