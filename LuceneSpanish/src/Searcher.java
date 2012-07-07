import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class Searcher{
	
	private IndexSearcher searcher;
	private QueryParser termIDQueryParser;
	private QueryParser labelQueryParser;
	private QueryParser searchLabelQueryParser;
	
	public Searcher(String keywordList) throws IOException{
		//open the index directory to search
		searcher = new IndexSearcher(IndexReader.open(FSDirectory.open(new File(keywordList))));
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
		//defining the query parser to search items by termID field, label field, and search label field
		termIDQueryParser = new QueryParser(Version.LUCENE_36, IndexItem.TERMID, analyzer);
		labelQueryParser = new QueryParser(Version.LUCENE_36, IndexItem.LABEL, analyzer);
		searchLabelQueryParser = new QueryParser(Version.LUCENE_36, IndexItem.SEARCHLABEL, analyzer);
	}
	//find indexed items by termID
	public List<IndexItem> findByTermID(String queryString, int numOfResults) throws ParseException, IOException {
		//create query from the incoming query string
		Query query = termIDQueryParser.parse(queryString);
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
		//create query from incoming query string
		Query query = searchLabelQueryParser.parse(queryString);
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