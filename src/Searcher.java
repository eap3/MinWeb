import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.QueryBuilder;
import org.apache.lucene.util.Version;

public class Searcher {

	IndexSearcher indexSearcher;
	QueryBuilder queryBuilder;
	Query query;
	   
	   public Searcher(String indexDirectoryPath) 
	      throws IOException{
	      IndexReader indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(indexDirectoryPath)));
	      indexSearcher = new IndexSearcher(indexReader);
	      queryBuilder = new QueryBuilder(new StandardAnalyzer());
	   }
	   
	   public TopDocs search( String searchQuery) 
	      throws IOException, ParseException{
		  query = queryBuilder.createPhraseQuery("contents", searchQuery);
	      return indexSearcher.search(query, 10);
	   }

	   public Document getDocument(ScoreDoc scoreDoc) 
	      throws CorruptIndexException, IOException{
	      return indexSearcher.doc(scoreDoc.doc);	
	   }
}