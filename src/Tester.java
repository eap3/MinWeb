import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class Tester {

	public static void main(String[] args) throws IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {

		MatrixRow[] elements = new MatrixRow[200];
		BufferedReader buffer = new BufferedReader(new FileReader("matrizRelevancia.txt"));
		String line;
		int indice = 0;
		System.out.println("Matriz de Relevância:");
		while((line = buffer.readLine()) != null) {
			String[] values = line.split(" ");
			System.out.println(line);
			elements[indice] = new MatrixRow("1".equals(values[0]), "1".equals(values[1]), "1".equals(values[2]));
			indice++;
		}
		
		Scanner in = new Scanner(System.in);
		System.out.println("Digite a consulta que quer fazer à base de dados:");
		String consulta = in.nextLine();
		System.out.println("Digite como quer que o índice e sua consulta sejam tratados (Basic, StopWord, Stemming ou Both):");
		String forma = in.nextLine();
		
		in.close();
		
		Searcher searcher = new Searcher(forma);
				
		TopDocs hits = searcher.search(consulta);
		//TopDocs hits = searcher.search("economic growth in China"); //consulta 1
		//TopDocs hits = searcher.search("economic impact of tourism"); //consulta 2
		//TopDocs hits = searcher.search("cheap Thailand trip"); //consulta 3
		
		int query = 0;
		if(consulta.equalsIgnoreCase("economic growth in China")){
			query = 1;
		} else if (consulta.equalsIgnoreCase("economic impact of tourism")) {
			query = 2;
		} else if (consulta.equalsIgnoreCase("cheap Thailand trip")) {
			query = 3;
		}
		
		double precision = 0, recall = 0, fMeasure = 0, totalDocs = 0, truePositives = 0;
		
		for(ScoreDoc scoreDoc : hits.scoreDocs) {
			totalDocs++;
			Document doc = searcher.getDocument(scoreDoc);

			String name = doc.get("name").replace(".txt", "");
			int posicao = Integer.parseInt(name);
			
			if(elements[posicao] != null){
				if(query == 1){
					if(elements[posicao].relevant1){
						truePositives++;
					}
				}else if (query == 2){ 
					if(elements[posicao].relevant2){
						truePositives++;
					}
				} else if (query == 3) {
					if(elements[posicao].relevant3){
						truePositives++;
					}
				}
			}
			
			System.out.println("File "+doc.get("name")+": "
					+ doc.get("contents").substring(0, 500));
			System.out.println("=======================================");
		}
		
		double relevants = 0;
		for(int i =0; i < elements.length; ++i){
			if(query == 1){
				if(elements[i] != null && elements[i].relevant1){
					relevants++;
				}
			}
			else if (query == 2){
				if(elements[i] != null && elements[i].relevant2){
					relevants++;
				}
			}
			else if (query == 3){
				if(elements[i] != null && elements[i].relevant3){
					relevants++;
				}
			}
		}
		
		precision = truePositives/totalDocs;

		recall = truePositives/relevants;
		fMeasure = (2*precision*recall)/(precision+recall);
		
		System.out.println(hits.scoreDocs.length + " documents found.");
		System.out.printf("Precision: %.4f\n", precision);
		System.out.printf("Recall: %.4f\n", recall);
		System.out.printf("F-measure: %.4f", fMeasure);
	}
}