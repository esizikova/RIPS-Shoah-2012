import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.ar.ArabicAnalyzer;//changed to Arabic
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


class Searcher{
	
	private IndexSearcher searcher;
	private QueryParser termIDQueryParser;
	private QueryParser labelQueryParser;
	private QueryParser searchLabelQueryParser;
	
	public Searcher(String keywordList) throws IOException{
		//open the index directory to search
		searcher = new IndexSearcher(IndexReader.open(FSDirectory.open(new File(keywordList))));
		ArabicAnalyzer analyzer = new ArabicAnalyzer(Version.LUCENE_36);//changed to Arabic
		//defining the query parser to search items by termID field, label field, and search label field
		termIDQueryParser = new QueryParser(Version.LUCENE_36, IndexItem.TERMID, analyzer);
		labelQueryParser = new QueryParser(Version.LUCENE_36, IndexItem.LABEL, analyzer);
		searchLabelQueryParser = new QueryParser(Version.LUCENE_36, IndexItem.SEARCHLABEL, analyzer);
	}
	//find indexed items by termID
	public List<IndexItem> findByTermID(String queryString, int numOfResults) throws ParseException, IOException {
		//create query from the incoming query string
		Query query = null;
		try {
			query = termIDQueryParser.parse(queryString);
		} catch (org.apache.lucene.queryParser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//execute the query and get results
		ScoreDoc[] queryResults = searcher.search(query, numOfResults).scoreDocs;
		List<IndexItem> results = new ArrayList<IndexItem>();
		//process the results
		for (ScoreDoc scoreDoc : queryResults) {
			Document doc = searcher.doc(scoreDoc.doc);
			results.add(new IndexItem(doc.get(IndexItem.TERMID), doc.get(IndexItem.LABEL), doc.get(IndexItem.SEARCHLABEL)));
		}
		return results;
	}
	//find indexed items by label
	public List<IndexItem> findByLabel(String queryString, int numOfResults) throws ParseException, IOException{
		//create query from incoming query string
		Query query = null;
		try {
			query = labelQueryParser.parse(queryString);
		} catch (org.apache.lucene.queryParser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		//create query from incoming query string
		Query query = null;
		try {
			query = searchLabelQueryParser.parse(queryString);
		} catch (org.apache.lucene.queryParser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	//close the searcher
	public void close() throws IOException {
		searcher.close();
	}
}