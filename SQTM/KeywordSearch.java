/**
 * @(#)KeywordSearch.java
 *
 *
 * @Christie Quaranta 
 * @version 1.00 2012/6/29
 */


package keywordsearch;

/*import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;*/
import java.io.*;
import java.util.*;
import java.lang.*;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;

//import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.*;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;

/*import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;*/
import org.apache.lucene.index.*;

import org.apache.lucene.search.*;

//import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.*;

//import org.apache.lucene.util.Version;
import org.apache.lucene.util.*;

public class KeywordSearch {
	//location where the index will be stored
	private static final String INDEX_DIR = "keywordList";
	private static final int DEFAULT_RESULT_SIZE = 100000;
	public static String queryNum;
	public static String findMe;
	public static File file2 = new File("Test2.csv");
        
    public static void main(String[] args) throws IOException, ParseException, NoSuchElementException {
        file2.delete();
        //import text file
        File file = new File("english_thesaurus.txt");
        //File file = new File("test.txt");
        Scanner scan = new Scanner(file);
        List<IndexItem> toBeIndex = new ArrayList<IndexItem>();
        while (scan.hasNext()){
        	String line = scan.nextLine();
        	String [] parts = line.split("\\t");
        	//System.out.println(Arrays.toString(parts));
        	boolean continue0 = true;
        	do{
        		try{
		        	if (parts.length >= 3){
			        	String tID = parts[0];
			        	//System.out.println(tID);
			        	String lab = parts[1];
			        	//System.out.println(lab);
			        	String sLab = parts[2];
			        	//System.out.println(sLab);
			        	toBeIndex.add(new IndexItem(tID, lab, sLab));
		        	}
		        	/*else{
		        		System.out.println("It broke here.");
		        		System.out.println("LINE: " + line);
		        		System.out.println("PARTS: ");
		        		Arrays.toString(parts);
		        	}*/
		        	continue0 = false;
	        	}
	        	catch (InputMismatchException ex){
	        		System.out.println("It broke here.");
		        	System.out.println("LINE: " + line);
		        	System.out.println("PARTS: ");
		        	Arrays.toString(parts);
	        		scan.nextLine();
	        	}
        	}while(continue0);
        }
        //items to be indexed
        IndexItem[] indexItems = toBeIndex.toArray(new IndexItem[toBeIndex.size()]);
        
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
        File file1 = new File("178_queries.txt");
        Scanner newScan = new Scanner(file1, "UTF-8");
        String [] query = new String [178];
        PrintWriter output = new PrintWriter(new FileWriter(file2, false));
        for (int k = 0; k < 178; k++){
        	if (newScan.hasNext()){
	        	query[k] = newScan.nextLine();
	        	//System.out.println(query[k]);
	        	String [] parts1 = query[k].split("\\t");
	        	queryNum = parts1[0];
	        	findMe = parts1[1];
	        	List<IndexItem> result = searcher.findBySearchLabel(findMe, DEFAULT_RESULT_SIZE);
	        	System.out.println("Found " + result.size() + " results for query " + (queryNum));
	        	print(output, result);
        	}
        	else{
        		System.out.println("Reached end of file, tried to read k = " + k);
        	}
        }
        output.flush();
        output.close();
        System.out.println("Your file has been saved.");
        
        //Scanner input = new Scanner(System.in);
        //System.out.println("Type Q/q to quit.");
        //System.out.println("Type 1 to query by termID.");
        //System.out.println("Type 2 to query by Label.");
        //System.out.println("Type 3 to query by Search Label.");
        
        /*do{
        	System.out.print("Enter input: ");
        	String queryType = input.nextLine();
        	
        	if (queryType.equalsIgnoreCase("q")){
        		break;
        	}
        	//search by termID
        	if (queryType.equals("1")){
        		System.out.print("Enter termID to search: ");
        		String queryTermID = input.nextLine();
        		List<IndexItem> result = searcher.findByTermID(queryTermID, DEFAULT_RESULT_SIZE);
        		print(result);
        	}
        	//search by label
        	else if (queryType.equals("2")){
        		System.out.print("Enter Label to search: ");
        		String queryLabel = input.nextLine();
        		List<IndexItem> result = searcher.findByLabel(queryLabel, DEFAULT_RESULT_SIZE);
        		print(result);
        	}
        	//search by search label
        	else if (queryType.equals("3")){
        		System.out.print("Enter Search Label to search: ");
        		String querySearchLabel = input.nextLine();
        		List<IndexItem> result = searcher.findBySearchLabel(querySearchLabel, DEFAULT_RESULT_SIZE);
        		print(result);
        	}

        }while (true);*/
        searcher.close();
    }
    //print results
    private static void print(PrintWriter output, List<IndexItem> result) throws FileNotFoundException, IOException {
    	//System.out.println("Result Size: " + result.size());
    	//for (IndexItem item : result) {
    		for (int m = 0; m < result.size(); m++){
				String result1 = (result.get(m)).toString();
				String printMe = (((queryNum.concat(",")).concat(result1)).concat(",")).concat(findMe);
				output.println(printMe);
    		}
    }
}

//class IndexItem{
//
//	private String termID;
//	private String label;
//	private String searchLabel;
//	
//	public static final String TERMID = "termID";
//	public static final String LABEL = "label";
//	public static final String SEARCHLABEL = "searchLabel";
//	
//	public IndexItem(String termID, String label, String searchLabel){
//		this.termID = termID;
//		this.label = label;
//		this.searchLabel = searchLabel;
//		//System.out.println(this.searchLabel);
//	}
//
//	public String getTermID(){
//		return termID;
//	}
//
//	public String getLabel(){
//		return label;
//	}
//	
//	public String getSearchLabel(){
//		return searchLabel;
//	}
//	
//	@Override
//	public String toString(){
//		return termID + "," + label + "," + searchLabel;
//	}
//}

//class Indexer{
//
//	private IndexWriter indexWriter;
//	
//	public Indexer(String indexDir) throws IOException {
//		//create the index
//		if (indexWriter == null) {
//			indexWriter = new IndexWriter(FSDirectory.open(new File("keywordList")), new IndexWriterConfig(Version.LUCENE_36, new EnglishAnalyzer(Version.LUCENE_36, StandardAnalyzer.STOP_WORDS_SET)));
//			indexWriter.deleteAll();
//		}
//	}
//	//add items to index
//	public void index(IndexItem indexItem) throws IOException{
//		//deleting the item, if already exists
//		//indexWriter.deleteDocuments(new Term(IndexItem.SEARCHLABEL, indexItem.getSearchLabel().toString()));
//		Document doc = new Document();
//		doc.add(new Field(IndexItem.TERMID, indexItem.getTermID().toString(), Field.Store.YES, Field.Index.ANALYZED));
//		doc.add(new Field(IndexItem.LABEL, indexItem.getLabel().toString(), Field.Store.YES, Field.Index.ANALYZED));
//		doc.add(new Field(IndexItem.SEARCHLABEL, indexItem.getSearchLabel().toString(), Field.Store.YES, Field.Index.ANALYZED));
//		//add the document to the index
//		indexWriter.addDocument(doc);
//	}
//	//Closing the index
//	public void close() throws IOException{
//		indexWriter.close();
//	}
//}

//class Searcher{
//	
//	private IndexSearcher searcher;
//	private QueryParser termIDQueryParser;
//	private QueryParser labelQueryParser;
//	private QueryParser searchLabelQueryParser;
//	
//	public Searcher(String keywordList) throws IOException{
//		//open the index directory to search
//		searcher = new IndexSearcher(IndexReader.open(FSDirectory.open(new File(keywordList))));
//		EnglishAnalyzer analyzer = new EnglishAnalyzer(Version.LUCENE_36, StandardAnalyzer.STOP_WORDS_SET);
//		//defining the query parser to search items by termID field, label field, and search label field
//		termIDQueryParser = new QueryParser(Version.LUCENE_36, IndexItem.TERMID, analyzer);
//		labelQueryParser = new QueryParser(Version.LUCENE_36, IndexItem.LABEL, analyzer);
//		searchLabelQueryParser = new QueryParser(Version.LUCENE_36, IndexItem.SEARCHLABEL, analyzer);
//	}
//	//find indexed items by termID
//	public List<IndexItem> findByTermID(String queryString, int numOfResults) throws ParseException, IOException {
//		List<IndexItem> results = new ArrayList<IndexItem>();
//		try{
//			//create query from the incoming query string
//			Query query = termIDQueryParser.parse(queryString);
//			//execute the query and get results
//			ScoreDoc[] queryResults = searcher.search(query, numOfResults).scoreDocs;
//			//process the results
//			for (ScoreDoc scoreDoc : queryResults) {
//				Document doc = searcher.doc(scoreDoc.doc);
//				results.add(new IndexItem(doc.get(IndexItem.TERMID), doc.get(IndexItem.LABEL), doc.get(IndexItem.SEARCHLABEL)));
//			}
//			return results;
//		}
//		catch (ParseException ex) {
//			results = null;
//			System.out.println("Parse Exception for: " + queryString);
//		}
//		return results;
//	}
//	//find indexed items by label
//	public List<IndexItem> findByLabel(String queryString, int numOfResults) throws ParseException, IOException{
//		//create query from incoming query string
//		Query query = labelQueryParser.parse(queryString);
//		//execute the query and get results
//		ScoreDoc[] queryResults = searcher.search(query, numOfResults).scoreDocs;
//		List<IndexItem> results = new ArrayList<IndexItem>();
//		//process the results
//		for (ScoreDoc scoreDoc : queryResults){
//			Document doc = searcher.doc(scoreDoc.doc);
//			results.add(new IndexItem(doc.get(IndexItem.TERMID), doc.get(IndexItem.LABEL), doc.get(IndexItem.SEARCHLABEL)));
//		}
//		return results;
//	}
//	//find indexed items by search label
//	public List<IndexItem> findBySearchLabel(String queryString, int numOfResults) throws ParseException, IOException{
//		List<IndexItem> results = new ArrayList<IndexItem>();
//		try{
//			//create query from incoming query string
//			Query query = searchLabelQueryParser.parse(queryString);
//			//execute the query and get results
//			ScoreDoc[] queryResults = searcher.search(query, numOfResults).scoreDocs;
//			//process the results
//			for (ScoreDoc scoreDoc : queryResults){
//				Document doc = searcher.doc(scoreDoc.doc);
//				results.add(new IndexItem(doc.get(IndexItem.TERMID), doc.get(IndexItem.LABEL), doc.get(IndexItem.SEARCHLABEL)));
//			}
//			return results;
//		}
//		catch (ParseException ex) {
//			System.out.println("Parse Exception for: " + queryString);
//		}
//		return results;
//	}
//	
//	//close the searcher
//	public void close() throws IOException {
//		searcher.close();
//	}
//}