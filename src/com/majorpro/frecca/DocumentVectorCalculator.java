package com.majorpro.frecca;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;

import com.majorpro.frecca.TextPreprocessor;

public class DocumentVectorCalculator {

List<String> documentList;
List<Hashtable<String,Double>> scoreVector;


public DocumentVectorCalculator(List<String> documentList)
{
	this.documentList=documentList;
	scoreVector=new ArrayList<Hashtable<String,Double>>(this.documentList.size());
	
}

private void generateTFScores()
{
	for(String t:documentList)
	{
	
		StringTokenizer stNew=new StringTokenizer(t," ");
		Hashtable<String, Double> tempHash=new Hashtable<String,Double>();
		double max=1.0;
		
		while(stNew.hasMoreTokens())
		{
				String token=stNew.nextToken();
			
		
				
				if(tempHash.containsKey(token))
				tempHash.put(token, Double.valueOf((tempHash.get(token)+1)));
				else
				tempHash.put(token, 1.0);
			
					double freq=tempHash.get(token);
					if(max<freq) max=freq;
		
		
		}
		//System.out.println(tempHash.toString());
		//System.out.println("Max="+max);
		
		
		for(String key:tempHash.keySet())
		{
			tempHash.put(key, tempHash.get(key)/max);
		}
			
		
		
		scoreVector.add(tempHash);	
	}
	
}

private double calcIDF(String word)
{
	int rho=documentList.size();
	int rhow=0;
	
	
	for(Hashtable<String,Double> temp:scoreVector)
	{
		if(temp.containsKey(word))
			rhow++;
	}
	
	//System.out.println(word+" rho="+rho+" rhow="+rhow);
	
	return Math.log((double)rho/(double)rhow);
}

public List<Hashtable<String,Double>> generateTF_IDFScores()
{
	generateTFScores();

	System.out.println("After only TF Scores");
	System.out.println(scoreVector.toString());
	
	for(Hashtable<String,Double> tempHash:scoreVector)
	{
		
		for(String key:tempHash.keySet())
		{
			tempHash.put(key, tempHash.get(key)*calcIDF(key));
			//System.out.println(key+" =>"+calcIDF(key));
			
		}
		
			
	}
	
	
	return scoreVector;
}

public static void main(String...args)
{
	String[] text={"Compatibility of systems of linear constraints over the set of natural numbers."," Criteria of compatibility of a system of linear Diophantine equations, strict inequations, and nonstrict inequations are considered."," Upper bounds for components of a minimal set of solutions and algorithms of construction of minimal generating sets of solutions for all types of systems are given."," These criteria and the corresponding algorithms for constructing a minimal supporting set of solutions can be used in solving all the considered types systems and systems of mixed types."};

	List<String> sentenceList=new ArrayList<String>();
	TextPreprocessor tp=new TextPreprocessor();
	
	for(String sentence:text)
	{
		sentenceList.add(tp.getPreprocessedText(sentence));
	}
	DocumentVectorCalculator dvc=new DocumentVectorCalculator(sentenceList);
	//System.out.println(dvc.generateTF_IDFScores().toString());
	List<Hashtable<String,Double>> score=dvc.generateTF_IDFScores();
	
	SimilarityMatrixGenerator simg=new SimilarityMatrixGenerator(score);
	
	double[][] result=simg.similarityMatrixGenerator();
	
	
	System.out.println(" ");
	System.out.println(" ");
	System.out.println("The Similarity Matrix");
	System.out.println(" ");
	
	
	for(int i=0;i<result.length;i++)
	{
		for(int j=0;j<result[i].length;j++)
		{
			System.out.print("     "+result[i][j]);
		}

		System.out.println(" ");
	}
	
}

}
