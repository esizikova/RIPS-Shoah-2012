/**
 * @(#)KeywordSearch.java
 *
 *
 * @Christie Quaranta 
 * @version 1.00 2012/6/29
 */



/*import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;*/
import java.io.*;
import java.util.*;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;

//import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.*;

import org.apache.lucene.analysis.standard.StandardAnalyzer;

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

public class KeywordSearch {
	//location where the index will be stored
	private static final String INDEX_DIR = "keywordList";
	private static final int DEFAULT_RESULT_SIZE = 10;
        
    public static void main(String[] args) throws IOException, ParseException {
        //items to be indexed
        IndexItem[] indexItems = {
        	new IndexItem("1", "food", "cookies"),
        	new IndexItem("1", "food", "candy"),
        	new IndexItem("1", "food", "food"),
        	new IndexItem("2", "emotion", "happiness"),
        	new IndexItem("3", "blood", "Type A"),
        	new IndexItem("2", "emotion", "sadness"),
        	new IndexItem("1", "food", "pudding"),
        	new IndexItem("1", "food", "chocolate pudding")
        };
        //creating indexer and indexing the items
        Indexer indexer = new Indexer(INDEX_DIR);
        for (IndexItem indexItem : indexItems){
        	indexer.index(indexItem);
        }
        //close the index
        indexer.close();
        
        Scanner input = new Scanner(System.in);
        System.out.println("Type Q/q to quit.");
        System.out.println("Type 1 to query by termID.");
        System.out.println("Type 2 to query by Label.");
        System.out.println("Type 3 to query by Search Label.");
        
        //creating the searcher to same location as the indexer
        Searcher searcher = new Searcher(INDEX_DIR);
        
        do{
        	System.out.print("Enter input: ");
        	String queryType = input.nextLine();
        	
        	if (queryType.equalsIgnoreCase("q")){
        		break;
        	}
        	//search by termID
        	if (queryType.equals("1")){
        		System.out.print("Enter termID to search: ");
        		String queryTermID = input.nextLine();
        		List<IndexItem> result = searcher.findByTermID(queryTermID, DEFAULT_RESULT_SIZE);
        		print(result);
        	}
        	//search by label
        	else if (queryType.equals("2")){
        		System.out.print("Enter Label to search: ");
        		String queryLabel = input.nextLine();
        		List<IndexItem> result = searcher.findByLabel(queryLabel, DEFAULT_RESULT_SIZE);
        		print(result);
        	}
        	//search by search label
        	else if (queryType.equals("3")){
        		System.out.print("Enter Search Label to search: ");
        		String querySearchLabel = input.nextLine();
        		List<IndexItem> result = searcher.findBySearchLabel(querySearchLabel, DEFAULT_RESULT_SIZE);
        		print(result);
        	}

        }while (true);
        searcher.close();
    }
    //print results
    private static void print(List<IndexItem> result) {
    	//System.out.println("Result Size: " + result.size());
    	for (IndexItem item : result) {
    		System.out.println(item);
    	}
    }
}