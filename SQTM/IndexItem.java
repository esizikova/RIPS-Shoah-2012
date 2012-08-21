/**
 * @IndexItem.java
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