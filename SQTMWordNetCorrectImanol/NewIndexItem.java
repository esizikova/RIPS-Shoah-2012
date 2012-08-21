/**
 * @(#)NewIndexItem.java
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

class NewIndexItem{

	private String queryNumber;
	private String termID;
	private String label;
	private String searchLabel;
	private String queryTerm;
	private String numOfResults;

	public static final String QUERYNUMBER = "queryNumber";
	public static final String TERMID = "termID";
	public static final String LABEL = "label";
	public static final String SEARCHLABEL = "searchLabel";
	public static final String QUERYTERM = "queryTerm";
	public static final String NUMOFRESULTS = "numOfResults";

	public NewIndexItem(String everything, int number){
		String [] parts = everything.split(",");
		this.queryNumber = parts[0];
		this.termID = parts[1];
		this.label = parts[2];
		this.searchLabel = parts[3];
		this.queryTerm = parts[4];
		this.numOfResults = "" + number;
		//System.out.println(this.searchLabel);
	}
	
	public NewIndexItem(String queryNumber, String termID, String label, String searchLabel, String queryTerm, String numOfResults){
		this.queryNumber = queryNumber;
		this.termID = termID;
		this.label = label;
		this.searchLabel = searchLabel;
		this.queryTerm = queryTerm;
		this.numOfResults = numOfResults;
	}
	
	public String getNewQueryNumber(){
		return queryNumber;
	}

	public String getNewTermID(){
		return termID;
	}

	public String getNewLabel(){
		return label;
	}

	public String getNewSearchLabel(){
		return searchLabel;
	}
	
	public String getNewQueryTerm(){
		return queryTerm;
	}
	
	public String getNewNumOfResults(){
		return numOfResults;
	}

	//@Override
	public String toNewString(){
		return queryNumber + "," + termID + "," + label + "," + searchLabel + "," + queryTerm + "," + numOfResults;
	}
}