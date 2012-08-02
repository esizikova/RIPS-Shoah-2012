
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

class IndexItem{

	private String termID;
	private String label;
	private String searchLabel;

	public static final String TERMID = "termID";
	public static final String LABEL = "label";
	public static final String SEARCHLABEL = "searchLabel";

	public IndexItem(String termID, String label, String searchLabel){
		this.termID = termID;
		this.label = label;
		this.searchLabel = searchLabel;
		//System.out.println(this.searchLabel);
	}

	public String getTermID(){
		return termID;
	}

	public String getLabel(){
		return label;
	}

	public String getSearchLabel(){
		return searchLabel;
	}

	@Override
	public String toString(){
		return termID + "," + label + "," + searchLabel;
	}
}