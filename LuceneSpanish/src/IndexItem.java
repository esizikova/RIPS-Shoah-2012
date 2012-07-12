public class IndexItem{

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
		return termID + "," + searchLabel;
	}
}
