
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
	//find indexed items by search label
	public List<IndexItem> findBySearchLabel(String queryString, int numOfResults) throws ParseException, IOException, WordNetException{
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
