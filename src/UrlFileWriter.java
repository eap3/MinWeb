import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class UrlFileWriter {

	public static void main(String[] args) throws IOException {
		Document doc;
		
		FileReader fr = new FileReader(new File("urls-economy.txt"));
		BufferedReader lerArq = new BufferedReader(fr);
		
		FileWriter fileWriter;
		for(int i = 100; i< 200; ++i){
			if(i>198){
				doc = Jsoup.connect(lerArq.readLine()).get();
				fileWriter = new FileWriter(new File("Data/"+(i)+".txt"));
				fileWriter.write(doc.text());
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
			} else {
				lerArq.readLine();
			}
		}
		
		lerArq.close();
	}
}