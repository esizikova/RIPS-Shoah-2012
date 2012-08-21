/**
 * @Indexer.java
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

class Indexer{

	private IndexWriter indexWriter;
	
	public Indexer(String indexDir) throws IOException {
		//create the index
		if (indexWriter == null) {
			indexWriter = new IndexWriter(FSDirectory.open(new File("keywordList")), new IndexWriterConfig(Version.LUCENE_36, new EnglishAnalyzer(Version.LUCENE_36, StandardAnalyzer.STOP_WORDS_SET)));
			indexWriter.deleteAll();
		}
	}
	//add items to index
	public void index(IndexItem indexItem) throws IOException{
		//deleting the item, if already exists
		//indexWriter.deleteDocuments(new Term(IndexItem.SEARCHLABEL, indexItem.getSearchLabel().toString()));
		Document doc = new Document();
		doc.add(new Field(IndexItem.TERMID, indexItem.getTermID().toString(), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field(IndexItem.LABEL, indexItem.getLabel().toString(), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field(IndexItem.SEARCHLABEL, indexItem.getSearchLabel().toString(), Field.Store.YES, Field.Index.ANALYZED));
		//add the document to the index
		indexWriter.addDocument(doc);
	}
	//Closing the index
	public void close() throws IOException{
		indexWriter.close();
	}
}