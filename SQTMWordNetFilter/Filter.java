/**
 * @(#)Filter.java
 *
 *
 * @Christie
 * @version 1.00 2012/8/6
 */


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

class Filter {
	
	//public static String word2;
	public static File file5 = new File("WordsFilteredOut.txt");

    public static String filter(String word2, String[][] filterWords, String[] stopWords) throws IOException {
    	PrintWriter output3 = new PrintWriter(new FileWriter(file5, true));
    	//System.out.println(word2);
    	String[] parts2 = word2.split(" ");
    	//System.out.println(parts2.length);
    	if (parts2.length > 1) {
    		//System.out.println("Filter if");
    		word2 = filterStopWords(parts2, stopWords, filterWords);
    	}
    	else{
    		//System.out.println("Filter else");
    		//System.out.println(word2);
	    	for(int r = 0; r < 486327; r++){
	    		if(word2.equalsIgnoreCase(filterWords[r][0])){
	    			double score = Double.parseDouble(filterWords[r][1]);
	    			//System.out.println(word2 + ": " + score);
	    			if (score == -1){
	    				output3.println(word2);
	    				//System.out.println(word2);
	    				word2 = "XXXXX1";
	    				return word2;
	    			}
	    			else{
	    				return word2;
	    			}
	    		}
	    		else{
	    			continue;
	    		}
	    	}
	    	output3.println(word2);
	    	word2 = "XXXXX2";
	    	return word2;
    	}
    	return word2;
    }
    
    public static String filterStopWords(String[] parts3, String[] stopWords2, String[][] filterWords2){
    	List<String> parts = new ArrayList<String>();
    	for (int v = 0; v < parts3.length; v++){
    		parts.add(parts3[v]);
    	}
    	for (int r = 0; r < parts.size(); r++){
    		//System.out.println("R for loop " + r);
    		String part = parts.get(r);
    		//System.out.println(part);
    		for (int y = 0; y < stopWords2.length; y++){
    			if (part.equalsIgnoreCase(stopWords2[y])){
    				parts.remove(r);
    				//System.out.println(stopWords2[y]);
    				//System.out.println("Stop Words If");
    				r--;
    				break;
    			}
    			else{
    				continue;
    			}
    		}
    	}
    	for (int s = 0; s < parts.size(); s++){
    		//System.out.println("S for loop " + s);
    		String part2 = parts.get(s);
    		//System.out.println(part2);
    		//System.out.println("Going into P for loop");
    		for (int p = 0; p < 486327; p++){
    			String fitWord = filterWords2[p][0];
    			if (part2.equalsIgnoreCase(fitWord)){
    				//System.out.println("Filter Words If");
    				double score = Double.parseDouble(filterWords2[p][1]);
    				//System.out.println(part2 + ": " + score);
    				if (score == -1){
    					//System.out.println("Score If");
    					String word4 = "XXXXX3";
    					return word4;
    				}
    				else{
    					//System.out.println("Score Else");
    					break;
    				}
    			}
    			else if ((p == 486326) && !(part2.equalsIgnoreCase(fitWord))){
    				//System.out.println("Filter Words Else If");
    				String word5 = "XXXXX4";
    				return word5;
    			}
    			else{
    				continue;
    			}
    		}
    	}
    	//System.out.println("After S for loop");
    	String word6 = "";
    	for (int o = 0; o < parts.size(); o++){
    		word6 = (word6.concat(parts.get(o))).concat(" ");
    	}
    	//System.out.println(word6);
    	return word6;
    } 
    
}