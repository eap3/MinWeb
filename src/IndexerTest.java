import java.io.File;
import java.io.IOException;

public class IndexerTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Indexer indexer = new Indexer("Index");
		
		for(int i = 0; i< 100; ++i){
			indexer.indexFile(new File(i+".txt"));
		}
		
		indexer.close();
	}

}
