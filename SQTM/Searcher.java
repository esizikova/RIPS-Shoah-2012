/**
 * @Searcher.java
 *
 *
 * @Christie Quaranta
 * @version 1.00 2012/8/21
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

class Searcher{
	
	private IndexSearcher searcher;
	private QueryParser termIDQueryParser;
	private QueryParser labelQueryParser;
	private QueryParser searchLabelQueryParser;
	
	public Searcher(String keywordList) throws IOException{
		//open the index directory to search
		searcher = new IndexSearcher(IndexReader.open(FSDirectory.open(new File(keywordList))));
		EnglishAnalyzer analyzer = new EnglishAnalyzer(Version.LUCENE_36, StandardAnalyzer.STOP_WORDS_SET);
		//defining the query parser to search items by termID field, label field, and search label field
		termIDQueryParser = new QueryParser(Version.LUCENE_36, IndexItem.TERMID, analyzer);
		labelQueryParser = new QueryParser(Version.LUCENE_36, IndexItem.LABEL, analyzer);
		searchLabelQueryParser = new QueryParser(Version.LUCENE_36, IndexItem.SEARCHLABEL, analyzer);
	}
	//find indexed items by termID
	public List<IndexItem> findByTermID(String queryString, int numOfResults) throws ParseException, IOException {
		List<IndexItem> results = new ArrayList<IndexItem>();
		try{
			//create query from the incoming query string
			Query query = termIDQueryParser.parse(queryString);
			//execute the query and get results
			ScoreDoc[] queryResults = searcher.search(query, numOfResults).scoreDocs;
			//process the results
			for (ScoreDoc scoreDoc : queryResults) {
				Document doc = searcher.doc(scoreDoc.doc);
				results.add(new IndexItem(doc.get(IndexItem.TERMID), doc.get(IndexItem.LABEL), doc.get(IndexItem.SEARCHLABEL)));
			}
			return results;
		}
		catch (ParseException ex) {
			results = null;
			System.out.println("Parse Exception for: " + queryString);
		}
		return results;
	}
	//find indexed items by label
	public List<IndexItem> findByLabel(String queryString, int numOfResults) throws ParseException, IOException{
		//create query from incoming query string
		Query query = labelQueryParser.parse(queryString);
		//execute the query and get results
		ScoreDoc[] queryResults = searcher.search(query, numOfResults).scoreDocs;
		List<IndexItem> results = new ArrayList<IndexItem>();
		//process the results
		for (ScoreDoc scoreDoc : queryResults){
			Document doc = searcher.doc(scoreDoc.doc);
			results.add(new IndexItem(doc.get(IndexItem.TERMID), doc.get(IndexItem.LABEL), doc.get(IndexItem.SEARCHLABEL)));
		}
		return results;
	}
	//find indexed items by search label
	public List<IndexItem> findBySearchLabel(String queryString, int numOfResults) throws ParseException, IOException{
		List<IndexItem> results = new ArrayList<IndexItem>();
		try{
			//create query from incoming query string
			Query query = searchLabelQueryParser.parse(queryString);
			//execute the query and get results
			ScoreDoc[] queryResults = searcher.search(query, numOfResults).scoreDocs;
			//process the results
			for (ScoreDoc scoreDoc : queryResults){
				Document doc = searcher.doc(scoreDoc.doc);
				results.add(new IndexItem(doc.get(IndexItem.TERMID), doc.get(IndexItem.LABEL), doc.get(IndexItem.SEARCHLABEL)));
			}
			return results;
		}
		catch (ParseException ex) {
			System.out.println("Parse Exception for: " + queryString);
		}
		return results;
	}
	
	//close the searcher
	public void close() throws IOException {
		searcher.close();
	}
}