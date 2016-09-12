import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class UrlFileWriter {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Document doc;
//		doc = Jsoup.connect("http://www.travelchannel.com/interests/packages/we-are-summer/articles/top-summer-luxury-getaways").get();
//		FileWriter fileWriter = new FileWriter(new File("teste.txt"));
//		fileWriter.write(doc.text());
		
		FileReader fr = new FileReader(new File("urls-economy.txt"));
		BufferedReader lerArq = new BufferedReader(fr);
		
		//System.out.println(lerArq.readLine());
		FileWriter fileWriter;
		for(int i = 0; i< 100; ++i){
			//System.out.println(i+" "+lerArq.readLine());
			
			if(i>91){
				doc = Jsoup.connect(lerArq.readLine()).get();
				fileWriter = new FileWriter(new File(i+100+"_.txt"));
				fileWriter.write(doc.text());
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}

}
