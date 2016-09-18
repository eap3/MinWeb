import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class Tester {

	public static void main(String[] args) throws IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {
		// TODO Auto-generated method stub

		/**
		Indexer indexer = new Indexer("Standard");
		
		for(int i = 0; i< 100; ++i){
			indexer.indexFile(new File(i+".txt"));
		}
		
		indexer.close();
		*/
		
		
		 Searcher searcher = new Searcher("Standard");
	     
	     TopDocs hits = searcher.search("asia winter");
	   
	      System.out.println(hits.totalHits +
	         " documents found.");
	      for(ScoreDoc scoreDoc : hits.scoreDocs) {
	         Document doc = searcher.getDocument(scoreDoc);
	            System.out.println("File: "
	            + doc.get("contents").substring(0, 500));
	      }
	}

}
