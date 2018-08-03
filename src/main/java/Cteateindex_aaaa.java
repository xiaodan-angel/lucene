import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class Cteateindex_aaaa {

    @Test
    public void createIndex() throws IOException {

        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_2, analyzer);
        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(new File("C:\\indexBase")), indexWriterConfig);
        Document document = new Document();
        document.add(new TextField("title", "c", Field.Store.YES));
        TextField textField = new TextField("desc", "cc", Field.Store.YES);
        document.add(textField);

        indexWriter.addDocument(document);
        indexWriter.commit();
        indexWriter.close();


    }


}

