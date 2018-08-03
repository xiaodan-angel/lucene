import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class Cteateindex {
    //创建索引库(模拟文档对象)

    @Test
    public void createIndex() throws IOException {
        //索引库存放的磁盘路径
        FSDirectory directory = FSDirectory.open(new File("C:\\indexBase"));
      /*  创建分词器对象
      * 把文档对象中，文本进行分词，变成索引单词
      * 1、基本分词器：特点：一个汉字一个词语*/
//
        /*2、cjk分词器
        * 使用cjk分词器创建索引库
        * 特点：每两个汉字一个词语，不管这个两个汉字组合是不是词语*/
        //CJKAnalyzer cjkAnalyzer = new CJKAnalyzer();
            /*3、聪明的中国分词器：smartchineseAnalyzer
            * 使用smartChineseanalyzer创建索引库
            * 特点：满足中文一些搜索习惯，对英文支持不是很好*/
       // SmartChineseAnalyzer smartChineseAnalyzer = new SmartChineseAnalyzer();
        //4、使用ik分词器创建索引库
       IKAnalyzer ikAnalyzer = new IKAnalyzer();
       // StandardAnalyzer standardAnalyzer = new StandardAnalyzer();

        //创建索引核心配置对象
       // StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_2,ikAnalyzer);

        //创建索引库核心对象
        IndexWriter indexWriter = new IndexWriter(directory,indexWriterConfig);
         /*文档对象数据来源:网页、文件、数据库、文档数据结构
         * 文档数据结构是以域字段方法组成的，类似于数据库字段，一个字段对应一些值
         *怎么把抽象数据变成域字段进行存储
          * 网页特点：文件
           * id，title，desc，url，content
           * */

        for (int i = 0; i < 30; i++) {
            Document document = new Document();
            /*添加域字段 id
             * stringFied域字段类型
             * 特点：不分词，有索引，stroe。no/yes
             * 搜索结果需不需要进行结果展示，如果不展示，数据就不存储*/
            document.add(new LongField("id",i, Field.Store.YES));
            /*添加域字段：title
             * textFiled:域字段类型
             * 特点：必须分词，索引，存储*/

            document.add(new TextField("title","lucene中的stringField域字段类型不分词，有索引，textFiled字段必须分词",Field.Store.YES));
            //添加域字段：dec
            TextField textField = new TextField("desc", "lucene是", Field.Store.YES);

            if(i==4){
               textField.setBoost(1000f);
            }
            document.add(textField);
            document.add(new TextField("content","lucene是",Field.Store.NO));
            //写索引库
            indexWriter.addDocument(document);
            Document document1 = new Document();
            document1.add(new StringField("project","经典", Field.Store.NO));

        }
        //提交
        indexWriter.commit();

    }
}
