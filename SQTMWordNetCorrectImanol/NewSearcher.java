/**
 * @(#)NewSearcher.java
 *
 *
 * @Christie Quaranta
 * @version 1.00 2012/8/20
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

class NewSearcher{

	private IndexSearcher searcher2;
	private QueryParser queryNumberQueryParser;

	public NewSearcher(String resultsList) throws IOException{
		//open the index directory to search
		searcher2 = new IndexSearcher(IndexReader.open(FSDirectory.open(new File(resultsList))));
		EnglishAnalyzer analyzer = new EnglishAnalyzer(Version.LUCENE_36, StandardAnalyzer.STOP_WORDS_SET);
		//defining the query parser to search items by termID field, label field, and search label field
		queryNumberQueryParser = new QueryParser(Version.LUCENE_36, NewIndexItem.QUERYNUMBER, analyzer);
	}
	//find indexed items by search label
	public List<NewIndexItem> findByQueryNumber(String queryString, int numOfResults) throws ParseException, IOException, WordNetException{
		List<NewIndexItem> results2 = new ArrayList<NewIndexItem>();
		try{
			//create query from incoming query string
			Query query = queryNumberQueryParser.parse(queryString);
			//execute the query and get results
			ScoreDoc[] queryResults = searcher2.search(query, numOfResults).scoreDocs;
			//process the results
			for (ScoreDoc scoreDoc : queryResults){
				Document doc = searcher2.doc(scoreDoc.doc);
				results2.add(new NewIndexItem(doc.get(NewIndexItem.QUERYNUMBER), doc.get(NewIndexItem.TERMID), doc.get(NewIndexItem.LABEL), doc.get(NewIndexItem.SEARCHLABEL), doc.get(NewIndexItem.QUERYTERM), doc.get(NewIndexItem.NUMOFRESULTS)));
			}
			return results2;
		}
		catch (ParseException ex) {
			System.out.println("Parse Exception2 for: " + queryString);
		}
		return results2;
	}

	//close the searcher
	public void close() throws IOException {
		searcher2.close();
	}
}