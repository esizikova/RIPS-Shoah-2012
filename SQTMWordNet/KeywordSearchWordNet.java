/**
 * @(#)KeywordSearch.java
 *
 *
 * @Christie Quaranta
 * @version 1.00 2012/8/2
 *
 *Princeton University "About WordNet." WordNet. Princeton University. 2010. <http://wordnet.princeton.edu>
 *
 *  Java API for WordNet Searching 1.0
  Copyright (c) 2007 by Brett Spell.

  This software is being provided to you, the LICENSEE, by under the following
  license.  By obtaining, using and/or copying this software, you agree that
  you have read, understood, and will comply with these terms and conditions:
   
  Permission to use, copy, modify and distribute this software and its
  documentation for any purpose and without fee or royalty is hereby granted,
  provided that you agree to comply with the following copyright notice and
  statements, including the disclaimer, and that the same appear on ALL copies
  of the software, database and documentation, including modifications that you
  make for internal use or for distribution.

  THIS SOFTWARE AND DATABASE IS PROVIDED "AS IS" WITHOUT REPRESENTATIONS OR
  WARRANTIES, EXPRESS OR IMPLIED.  BY WAY OF EXAMPLE, BUT NOT LIMITATION,  
  LICENSOR MAKES NO REPRESENTATIONS OR WARRANTIES OF MERCHANTABILITY OR FITNESS
  FOR ANY PARTICULAR PURPOSE OR THAT THE USE OF THE LICENSED SOFTWARE OR
  DOCUMENTATION WILL NOT INFRINGE ANY THIRD PARTY PATENTS, COPYRIGHTS,
  TRADEMARKS OR OTHER RIGHTS.
 */


//package keywordsearch;
package edu.smu.tspell.wordnet;

import java.io.*;
import java.util.*;
import java.lang.*;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;

import org.apache.lucene.queryParser.*;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.synonym.*;
import org.apache.lucene.analysis.TokenStream;
//import org.apache.lucene.analysis.synonym.SynonymFilter;
//import org.apache.lucene.analysis.synonym.SynonymMap;
//import org.apache.lucene.analysis.synonym.WordnetSynonymParser;

import org.apache.lucene.index.*;

import org.apache.lucene.search.*;

import org.apache.lucene.store.*;

import org.apache.lucene.util.*;

import edu.smu.tspell.wordnet.*;

public class KeywordSearchWordNet {
	//location where the index will be stored
	private static final String INDEX_DIR = "keywordList";
	private static final int DEFAULT_RESULT_SIZE = 100000;
	public static String queryNum;
	public static String findMe;
	public static File file2 = new File("English_WordNet4.csv");//output file
	public static List<String> synonyms = new ArrayList<String>();
	public static File file3 = new File("synonyms.csv");

    public static void main(String[] args) throws IOException, ParseException {
        file2.delete();
        file3.delete();
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
        Scanner scan3 = new Scanner(file1, "UTF-8");
        String [] query = new String [178];
        PrintWriter output = new PrintWriter(new FileWriter(file2, false));
        //PrintWriter output2 = new PrintWriter(new FileWriter(file2, true));
        for (int k = 0; k < 178; k++){
        	query[k] = scan3.nextLine();
        	//System.out.println(query[k]);
        	String [] parts1 = query[k].split("\\t");
        	queryNum = parts1[0];
        	findMe = parts1[1];
        	wordNetStuff(findMe);
        	for (int n = 0; n < synonyms.size(); n++){
        		//output2.println(synonyms.get(n));
	        	List<IndexItem> result = searcher.findBySearchLabel(synonyms.get(n), DEFAULT_RESULT_SIZE);
	        	System.out.println("Found " + result.size() + " results for query " + (queryNum));
	        	print(output, result);
        	}
        }
        output.flush();
        output.close();
        System.out.println("Your file has been saved.");
        searcher.close();
    }
    //print results
    private static void print(PrintWriter output, List<IndexItem> result) throws FileNotFoundException, IOException {
    	for (int m = 0; m < result.size(); m++){
			String result1 = (result.get(m)).toString();
			String printMe = (((queryNum.concat(",")).concat(result1)).concat(",")).concat(findMe);
			output.println(printMe);
    	}
    }
    public static List<String> wordNetStuff(String word) throws FileNotFoundException, IOException, WordNetException {
    	//Set dictionary location
    	System.setProperty("wordnet.database.dir", "C:\\Program Files (x86)\\WordNet\\2.1\\dict\\");
    	//create database from dictionary
    	WordNetDatabase database = WordNetDatabase.getFileInstance();
    	//clear synonym arraylist from last run
    	synonyms.clear();
    	synonyms.add(word);
    	//get synsets for query
    	Synset[] set = database.getSynsets(word);
    	PrintWriter output2 = new PrintWriter(new FileWriter(file3, true));
    	String printMe2 = (queryNum.concat(",")).concat(word);
    	output2.println(printMe2);
    	for (int q = 0; q < set.length; q++){
    		//get synonyms for query
	    	String[] newWords = set[q].getWordForms();
	    	for(int f = 0; f < newWords.length; f++){
	    		//add words to synonym list to be searched
	    		synonyms.add(newWords[f]);
	    		//System.out.println(newWords[f]);
	    		String printMe3 = (queryNum.concat(",")).concat(newWords[f]);
	    		output2.println(printMe3);
	    	}
    	}
    	output2.flush();
        output2.close();
    	return synonyms;
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
//	//find indexed items by search label
//	public List<IndexItem> findBySearchLabel(String queryString, int numOfResults) throws ParseException, IOException, WordNetException{
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

//class WordNetSynonymMap implements SynonymMap {
//	IndexSearcher searcher;
//	Directory fsDir;
//
//	public WordNetSynonymMap (File index) throws IOException {
//		fsDir = FSDirectory.open(index);
//		searcher = new IndexSearcher(IndexReader.open(fsDir));
//		//searcher = new IndexSearcher(IndexReader.open(FSDirectory.open(new File(index))));
//	}
//	public void mapClose() throws IOException {
//		searcher.close();
//		fsDir.close();
//	}
//	public String[] getSynonyms(String word) throws IOException {
//		List<String> synList = new ArrayList<String>();
//		TopScoreDocCollector collector = new CachingCollector(1000000000, true);
//		searcher.search(new TermQuery(new Term("word", word)), collector);
//		for (ScoreDoc scoreDoc : collector.getTotalHits()){
//			Document doc = searcher.doc(scoreDoc.doc);
//			String[] values = doc.getValues("syn");
//			for (String syn : values) {
//				synList.add(syn);
//			}
//		}
//		return synList.toArray(new String[0]);
//	}
//	public SynonymMap build () throws IOException {
//
//	}
//}
//
//class SynonymAnalyzer {
//	private SynonymMap map;
//
//	public SynonymAnalyzer(SynonymMap map) {
//		this.map = map;
//	}
//	//public TokenStream tokenStream (String fieldName, Reader reader) {
//	//	TokenStream result = new SynonymFilter (new StopFilter(true, new LowerCaseFilter(new StandardFilter(new StandardTokenizer(Version.LUCENE_36, reader))),StandardAnalyzer.STOP_WORDS_SET), engine);
//	//	return result;
//	//}
//}
//
//class SynonymFilter extends AttributeSource {
//	public static final String TOKEN_TYPE_SYNONYM = "SYNONYM";
//	private Stack<String> synonymStack;
//	private SynonymMap map;
//	private AttributeSource.State current;
//	private final AttributeSource termAtt;
//	private final AttributeSource posIncrAtt;
//
//	public SynonymFilter (TokenStream input, SynonymMap map, boolean ignoreCase){
//		super(input);
//		synonymStack = new Stack<String>();
//		this.map = map;
//		this.termAtt = addAttribute(AttributeSource(input));
//		this.posIncrAtt = addAttribute(incrementToken());
//	}
//	public boolean incrementToken() throws IOException {
//		if (synonymStack.size() > 0) {
//			String syn = synonymStack.pop();
//			restoreState(current);
//			termAtt.setTermBuffer(syn);
//			posIncrAtt.setPositionIncrement(0);
//			return true;
//		}
//		//if (!input.incrementToken()){
//		//	return false;
//		//}
//		if (addAliasesToStack()){
//			current = captureState();
//		}
//		return true;
//	}
//	private boolean addAliasesToStack() throws IOException {
//		String[] synonyms = map.getSynonyms(termAtt.term());
//		if (synonyms == null) {
//			return false;
//		}
//		for (String synonym : synonyms) {
//			synonymStack.push(synonym);
//		}
//		return true;
//	}
//}