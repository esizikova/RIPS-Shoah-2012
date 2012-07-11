import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

class Indexer{
	private IndexWriter indexWriter;
	
	public Indexer(String indexDir) throws IOException {
		//create the index
		if (indexWriter == null) {
			indexWriter = new IndexWriter(FSDirectory.open(new File("keywordList")), new IndexWriterConfig(Version.LUCENE_36, new StandardAnalyzer(Version.LUCENE_36)));
		}
	}
	//add items to index
	public void index(IndexItem indexItem) throws IOException{
		//deleting the item, if already exists
		indexWriter.deleteDocuments(new Term(IndexItem.SEARCHLABEL, indexItem.getSearchLabel().toString()));
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