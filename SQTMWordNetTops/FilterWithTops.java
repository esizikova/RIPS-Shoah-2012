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

class FilterWithTops {

    public static List<String> filterWithTops(List<String> words, String[][] filterWords, String[] stopWords) throws IOException {
    	List<String> finalList = new ArrayList<String>();
    	String[] wordsList = new String[words.size()];
    	double[] scoreList = new double[words.size()];
    	if (words.size() == 1){
    		return words;
    	}
    	else if (words.size() == 2){
    		return words;
    	}
    	else {
	    	for (int i = 0; i < words.size(); i++){
	    		String word2 = words.get(i);
	    		//System.out.println(word2);
		    	String[] parts2 = word2.split(" ");
		    	if (parts2.length > 1) {
		    		List<String> parts = new ArrayList<String>();
			    	for (int v = 0; v < parts2.length; v++){
			    		parts.add(parts2[v]);
			    	}
			    	for (int r = 0; r < parts.size(); r++){
			    		String part = parts.get(r);
			    		for (int y = 0; y < stopWords.length; y++){
			    			if (part.equalsIgnoreCase(stopWords[y])){
			    				parts.remove(r);
			    				r--;
			    				break;
			    			}
			    			else{
			    				continue;
			    			}
			    		}
			    	}
			    	double multiScore = 0;
			    	int count = 0;
			    	for (int s = 0; s < parts.size(); s++){
			    		String part2 = parts.get(s);
			    		for (int p = 0; p < 486327; p++){
			    			String fitWord = filterWords[p][0];
			    			if (part2.equalsIgnoreCase(fitWord)){
			    				double score1 = Double.parseDouble(filterWords[p][1]);
			    				multiScore += score1;
			    				count ++;
			    				break;
			    			}
			    			else if ((p == 486326) && !(part2.equalsIgnoreCase(fitWord))){
			    				multiScore += -1;
			    				count ++;
			    				break;
			    			}
			    			else{
			    				continue;
			    			}
			    		}
			    	}
			    	//System.out.println(multiScore + " & " + count);
			    	double finalScore = multiScore / count;
			    	//System.out.println("Final Score = " + finalScore);
			    	String word6 = "";
			    	for (int o = 0; o < parts.size(); o++){
			    		word6 = (word6.concat(parts.get(o))).concat(" ");
			    	}
			    	wordsList[i] = word6;
			    	scoreList[i] = finalScore;
		    	}
		    	else{
			    	for(int r = 0; r < 486327; r++){
			    		if(word2.equalsIgnoreCase(filterWords[r][0])){
			    			double score2 = Double.parseDouble(filterWords[r][1]);
			    			wordsList[i] = word2;
			    			scoreList[i] = score2;
			    			//System.out.println("Score2 = " + score2);
			    			break;
			    		}
			    		else if((r == 486326) && !(word2.equalsIgnoreCase(filterWords[r][0]))){
			    			wordsList[i] = word2;
			    			scoreList[i] = -1;
			    		}
			    		else{
			    			continue;
			    		}
			    	}
		    	}
	    	}
	    	int where1 = 0;
	    	int where2 = 0;
	    	int where3 = 0;
	    	double max1 = -2;
	    	double max2 = -2;
	    	double max3 = -2;
	    	for (int g = 0; g < scoreList.length; g++){
	    		double scorea = scoreList[g];
	    		if (scorea > max1){
	    			where1 = g;
	    			max1 = scorea;
	    			g = 0;
	    		}
	    		else if ((scorea != max1) && (scorea > max2)){
	    			where2 = g;
	    			max2 = scorea;
	    			g = 0;
	    		}
	    		else if ((scorea != max1) && (scorea != max2) && (scorea > max3)){
	    			where3 = g;
	    			max3 = scorea;
	    			g = 0;
	    		}
	    		else {
	    			continue;
	    		}
	    	}
	    	System.out.println(wordsList[where1]);
	    	System.out.println(wordsList[where2]);
	    	System.out.println(wordsList[where3]);
	    	finalList.add(wordsList[where1]);
	    	finalList.add(wordsList[where2]);
	    	finalList.add(wordsList[where3]);
	    	//System.out.println(finalList);
	    	return finalList;
    	}
    }
}