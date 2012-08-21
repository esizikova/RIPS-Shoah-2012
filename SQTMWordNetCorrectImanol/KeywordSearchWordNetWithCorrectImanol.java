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

public class KeywordSearchWordNetWithCorrectImanol {
	//location where the index will be stored
	private static final String INDEX_DIR = "keywordList";
	private static final String INDEX_DIR2 = "resultsList";
	private static final int DEFAULT_RESULT_SIZE = 100000;
	public static String queryNum;
	public static String findMe;
	public static File file2 = new File("Spanish_IMDB_WPDC.csv");//output file
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
        File file1 = new File("es2en_IMDB_pivotplusdirect1.txt");
        Scanner scan3 = new Scanner(file1, "UTF-8");
        String [] query = new String [2357];
        PrintWriter output = new PrintWriter(new FileWriter(file2, false));
        //PrintWriter output2 = new PrintWriter(new FileWriter(file2, true));
        List<NewIndexItem> newList = new ArrayList<NewIndexItem>();
        for (int k = 0; k < 2357; k++){
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
	        	for (int b = 0; b < result.size(); b++){
	        		String result3 = (result.get(b)).toString();
	        		String toMethod = (((queryNum.concat(",")).concat(result3)).concat(",")).concat(findMe);
	        		newList.add(new NewIndexItem(toMethod, result.size()));
	        	}
	        	//print(output, result);
        	}
        }
        searcher.close();
        NewIndexItem[] all = newList.toArray(new NewIndexItem[newList.size()]);
        NewIndexer indexer2 = new NewIndexer(INDEX_DIR2);
        for (int a = 0; a < all.length; a++){
        	indexer2.newIndex(all[a]);
        }
        indexer2.close();
        NewSearcher searcher2 = new NewSearcher(INDEX_DIR2);
        List<String> finalSearch = new ArrayList<String>();
        for (int x = 0; x < 1341; x++){
        	String y = "" + x;
        	String look = "";
        	List<NewIndexItem> almostFinal = searcher2.findByQueryNumber(y, DEFAULT_RESULT_SIZE);
        	String [] almostDone = new String [almostFinal.size()];
        	for (int v = 0; v < almostFinal.size(); v++){
        		almostDone[v] = (almostFinal.get(v)).toNewString();
        	}
        	int maximum = -1;
        	for (int z = 0; z < almostDone.length; z++){
        		//System.out.println(almostDone[z]);
        		String [] parts4 = (almostDone[z]).split(",");
        		int number = Integer.parseInt(parts4[5]);
        		if (number > maximum){
        			maximum = number;
        			look = parts4[4];
        		}
        	}
        	finalSearch.add(look);
        }
        for(int f = 0; f < finalSearch.size(); f++){
        	List<IndexItem> done = searcher.findBySearchLabel(finalSearch.get(f), DEFAULT_RESULT_SIZE);
        	findMe = finalSearch.get(f);
        	queryNum = "" + (f);
        	System.out.println("Found " + done.size() + " results for query " + (f) + ": " + findMe);
        	print(output, done);
        }
        output.flush();
        output.close();
        System.out.println("Your file has been saved.");
        searcher2.close();
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