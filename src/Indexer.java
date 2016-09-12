import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.IOUtils;
import org.apache.lucene.util.Version;
import org.jsoup.Jsoup;
import org.apache.lucene.document.Field;

public class Indexer {
	
	private IndexWriter writer;

	   public Indexer(String indexDirectoryPath) throws IOException{
	      //this directory will contain the indexes
	      Directory indexDirectory = FSDirectory.open(Paths.get(indexDirectoryPath));

	      //create the indexer
	      writer = new IndexWriter(indexDirectory, new IndexWriterConfig(new StandardAnalyzer()));
	   }

	   public void close() throws CorruptIndexException, IOException{
	      writer.close();
	   }

	   private Document getDocument(File file) throws IOException{
	      Document document = new Document();

	      String conteudo = new Scanner(file).useDelimiter("\\Z").next();
	      //index file contents
	      Field contentField = new TextField("contents", conteudo, Field.Store.YES);
	      //index file name
	      Field fileNameField = new TextField("name",
	         file.getName(),
	         Field.Store.YES);
	      //index file path

	      document.add(contentField);
	      document.add(fileNameField);

	      return document;
	   }   

	   public void indexFile(File file) throws IOException{
	      System.out.println("Indexing "+file.getCanonicalPath());
	      Document document = getDocument(file);
	      writer.addDocument(document);
	   }

	   public int createIndex(String dataDirPath) 
	      throws IOException{
	      //get all files in the data directory
	      File[] files = new File(dataDirPath).listFiles();

	      for (File file : files) {
	         if(!file.isDirectory()
	            && !file.isHidden()
	            && file.exists()
	            && file.canRead()
	         ){
	            indexFile(file);
	         }
	      }
	      return writer.numDocs();
	   }
	
	
	
//
//	static IndexWriter indexWriter;
//	
//	public Indexer(String path) throws IOException{
//		Directory indexDir = FSDirectory.open(Paths.get(path));
//		IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
//		indexWriter = new IndexWriter(indexDir, config);
//	}
//	
//	public void addDocumentToIndex(String name) throws IOException{
//		Document doc = new Document();
//		File file = new File(name);
//		FileReader fr = new FileReader(file);
//		BufferedReader reader = new BufferedReader(fr);
//		String s = reader.readLine();
//		System.out.println(s);
//		TextField textField = new TextField("body", s, Field.Store.YES);
//		doc.add(textField);
//		indexWriter.addDocument(doc);
//	}
//	
//	public void close() throws CorruptIndexException, IOException{
//	      indexWriter.close();
//    }


}
