import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.StopwordAnalyzerBase;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

public class BothAnalyzer extends StopwordAnalyzerBase{

	
	public BothAnalyzer(){
		this(StandardAnalyzer.STOP_WORDS_SET);
	}
	
	private BothAnalyzer(CharArraySet stopWords){
		super(stopWords);
	}
	
	@Override
	protected TokenStreamComponents createComponents(String arg0) {
		
		final Tokenizer source = new StandardTokenizer();
	    TokenStream result = new StandardFilter(source);
	    result = new EnglishPossessiveFilter(result);
	    result = new LowerCaseFilter(result);
	    result = new StopFilter(result, stopwords);
	    result = new PorterStemFilter(result);
	    return new TokenStreamComponents(source, result);
	}
}