/**
 * @(#)NewIndexer.java
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

class NewIndexer{

	private IndexWriter indexWriter2;

	public NewIndexer(String indexDir2) throws IOException {
		//create the index
		if (indexWriter2 == null) {
			indexWriter2 = new IndexWriter(FSDirectory.open(new File("resultsList")), new IndexWriterConfig(Version.LUCENE_36, new EnglishAnalyzer(Version.LUCENE_36, StandardAnalyzer.STOP_WORDS_SET)));
			indexWriter2.deleteAll();
		}
	}
	//add items to index
	public void newIndex(NewIndexItem newIndexItem) throws IOException{
		//deleting the item, if already exists
		//indexWriter.deleteDocuments(new Term(IndexItem.SEARCHLABEL, indexItem.getSearchLabel().toString()));
		Document doc = new Document();
		doc.add(new Field(NewIndexItem.QUERYNUMBER, newIndexItem.getNewQueryNumber().toString(), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field(NewIndexItem.TERMID, newIndexItem.getNewTermID().toString(), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field(NewIndexItem.LABEL, newIndexItem.getNewLabel().toString(), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field(NewIndexItem.SEARCHLABEL, newIndexItem.getNewSearchLabel().toString(), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field(NewIndexItem.QUERYTERM, newIndexItem.getNewQueryTerm().toString(), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field(NewIndexItem.NUMOFRESULTS, newIndexItem.getNewNumOfResults().toString(), Field.Store.YES, Field.Index.ANALYZED));
		//add the document to the index
		indexWriter2.addDocument(doc);
	}
	//Closing the index
	public void close() throws IOException{
		indexWriter2.close();
	}
}