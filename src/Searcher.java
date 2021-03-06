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
import org.apache.lucene.queryparser.classic.QueryParser;
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
	QueryParser queryParser;
	Query query;

	public Searcher(String indexDirectoryPath) throws IOException{
		IndexReader indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(indexDirectoryPath)));
		indexSearcher = new IndexSearcher(indexReader);

		if(indexDirectoryPath.equals("Basic")){
			queryParser = new QueryParser("contents", new BasicAnalyzer());
		} else if (indexDirectoryPath.equals("StopWord")){
			queryParser = new QueryParser("contents", new StandardAnalyzer());
		} else if (indexDirectoryPath.equals("Stemming")){
			queryParser = new QueryParser("contents", new StemmingAnalyzer());
		} else{
			queryParser = new QueryParser("contents", new BothAnalyzer());
		}
	}

	public TopDocs search(String searchQuery) 
			throws IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException{
		query = queryParser.parse(searchQuery);
		return indexSearcher.search(query, 200);
	}

	public Document getDocument(ScoreDoc scoreDoc) throws CorruptIndexException, IOException{
		return indexSearcher.doc(scoreDoc.doc);	
	}
}