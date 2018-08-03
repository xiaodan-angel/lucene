import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class QueryIndex_aaaaa {

    @Test
    public void searchIndex() throws Exception{

        DirectoryReader reader = DirectoryReader.open(FSDirectory.open(new File("C:\\indexBase")));

        IndexSearcher indexSearcher = new IndexSearcher(reader);

       String qName="lucene";

      org.apache.lucene.queryparser.classic.QueryParser queryParser = new org.apache.lucene.queryparser.classic.QueryParser(Version.LUCENE_4_10_2, "desc", new StandardAnalyzer());

     Query parse = queryParser.parse(qName);

   TopDocs docs= indexSearcher.search(parse,10);
        int num = docs.totalHits;//获取命中文档总记录
        System.out.println("命中总记录数："+num);
        ScoreDoc[] scoreDocs = docs.scoreDocs;

        for (ScoreDoc doc : scoreDocs) {
            double score = doc.score;
            System.out.println("文档得分:"+score);
            int docId = doc.doc;
            System.out.println("文档id"+docId);

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
    public void testIndexUpdate() throws Exception {
        // 创建分词器
        //Analyzer analyzer = new IKAnalyzer();

        // 创建Directory流对象
        Directory directory = FSDirectory.open(new File("C:/itcast/lucene/index"));
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_2, new StandardAnalyzer());
        // 创建写入对象
        IndexWriter indexWriter = new IndexWriter(directory, config);

        // 创建Document
        Document document = new Document();
        document.add(new TextField("id", "1002", Field.Store.YES));
        document.add(new TextField("title", "lucene测试test 002", Field.Store.YES));

        // 执行更新，会把所有符合条件的Document删除，再新增。
        indexWriter.updateDocument(new Term("desc", "cc"), document);
         indexWriter.commit();
        // 释放资源
        indexWriter.close();
    }


}