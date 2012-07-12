/**
 * @(#)KeywordSearch.java
 *
 *
 * @Christie Quaranta 
 * @version 1.00 2012/6/29
 */


/*import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;*/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.lucene.document.Field.Index;
import org.apache.lucene.queryParser.ParseException;

public class SpanishKeywordSearch {//changed to Spanish
	//location where the index will be stored
	private static final String INDEX_DIR = "keywordList";
	private static final int DEFAULT_RESULT_SIZE = 100000;
    static String term = null;
    static String queryNum = null;
    public static String findMe;//added
	public static File file2 = new File("Spanish.csv");  // file in which results of the code will be saved; changed to Spanish
	
    public static void main(String[] args) throws IOException, ParseException {
        file2.delete();
        System.out.println("start");
        
    	// import the XML file (replace with correct addresss)
        XML2List xmlListConverter = new XML2List("/home/lena/Documents/CS_Projects/RIPS-Shoah-2012/LuceneXML/src/Thesaurus2_xml_ver2.xml");
        ArrayList<IndexItem> indexItems = xmlListConverter.getIndexingTerms();
        System.out.println("XML loaded");
        
        //creating indexer and indexing the items
        Indexer indexer = new Indexer(INDEX_DIR);
        for (IndexItem indexItem : indexItems){
        	indexer.index(indexItem);
        }
        //close the index
        indexer.close();
        
        //creating the searcher to same location as the indexer
        Searcher searcher = new Searcher(INDEX_DIR);
        
        //import queries file
        File file1 = new File("/home/lena/Dropbox/RIPS-Shoah/keyword search/queries.txt"); // replace with your address
        Scanner newScan = new Scanner(file1);
        String [] query = new String [228];
        PrintWriter output = new PrintWriter(new FileWriter(file2, false));  // change

        for (int k = 0; k < 228; k++){   // for each query, input into Lucene and search
        	query[k] = newScan.nextLine();
        	String [] parts1 = query[k].split("\\t");  // split the query into query number and query word

        	
        	queryNum = parts1[0];
        	term = parts1[1];

        	List<IndexItem> result = null;
			try {
				result = searcher.findBySearchLabel(term, DEFAULT_RESULT_SIZE); 
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			
        	print(output, result);
        }
        output.flush();
        output.close();
        System.out.println("Your file has been saved. Search results are recorded in Spanish.csv");//changed to Spanish
        searcher.close();
    }
    //print results into the text file
    private static void print(PrintWriter output, List<IndexItem> result) throws FileNotFoundException, IOException {

    	for (int m = 0; m < result.size() - 1; m++){
			String result1 = (result.get(m)).toString();
			String result2 = (result.get(m + 1)).toString();
			boolean truth = result1.equals(result2);
			String printMe = (((queryNum.concat(",")).concat(result1)).concat(",")).concat(findMe);
			if (truth){
				break;
			}
			else{
			output.println(printMe);
			}
    	}//slight change, still same result
    	
    }
}
