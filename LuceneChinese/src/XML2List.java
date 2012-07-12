import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//parses an XML file with specified fileName, returning a list of IndexItems

public class XML2List{
	//fields
	private ArrayList<IndexItem> indexingTerms;
	
	//constructor
	public XML2List(String fileName){
		indexingTerms = getAllItems(fileName);
	}
	
	//helper
	private ArrayList<IndexItem> getAllItems(String fileName) {
    	ArrayList<IndexItem> temp = new ArrayList<IndexItem>();
    	
    	try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File file = new File(fileName);
            if (file.exists()) {
                Document doc = db.parse(file);
                Element docEle = doc.getDocumentElement();
               
                //pulls all rows
                NodeList rowList = docEle.getElementsByTagName("row");
 				              
                if (rowList != null && rowList.getLength() > 0) {
                    for (int i = 0; i < rowList.getLength(); i++) {
 
                        Node node = rowList.item(i);
 
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
 
                            Element e = (Element) node;
                            NodeList nodeList = e.getElementsByTagName("*"); //pull off a column
                            String termID = nodeList.item(0).getChildNodes().item(0)
                                            .getNodeValue(); //get termID (element 0)
                            
                            /*String type = nodeList.item(1).getChildNodes().item(0)
                                    		.getNodeValue(); //get type*/
                            
                            String label = nodeList.item(2).getChildNodes().item(0)
                                            .getNodeValue(); //get label (element 2)
 
                            String searchlabel = nodeList.item(3).getChildNodes().item(0)
                                            .getNodeValue(); //get searchlabel (element 3)
                            
                            //add to array list
                            temp.add(new IndexItem(termID, label, searchlabel));
                        }
                    }
                
                return temp;
                } else {
                    System.exit(1);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return temp;
    }
    
	//accessor
	public ArrayList<IndexItem> getIndexingTerms(){
		return indexingTerms;
	}
}