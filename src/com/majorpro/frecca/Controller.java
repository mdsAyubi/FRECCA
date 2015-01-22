package com.majorpro.frecca;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Controller {
	String[] sentenceList;

public static void main(String...args)throws Exception
{
	//SimilarityMatrixGenerator smg=new SimilarityMatrixGenerator();
	
	//String fileName="input.txt";
	
	//printing the similarity matrix
	
	
	
	
	String text="Being a really good speaker is not merely orthogonal to having good ideas, but in many ways pushes you in the opposite direction. " +
"For example, when I give a talk I usually write it out beforehand." +
" I know that's a mistake; I know delivering a prewritten talk makes it harder to engage with an audience." +
" The way to get the attention of an audience is to give them your full attention, and when you're delivering a prewritten talk your attention is always divided between the audience and the talk—even if you've memorized it." +
" If you want to engage an audience it's better to start with no more than an outline of what you want to say and add lib the individual sentences. " +
"But if you do that you could spend no more time thinking about each sentence than it takes to say it." +
" Occasionally the stimulation of talking to a live audience makes you think of new things, but in general this is not going to generate ideas as well as writing does, where you can spend as long on each sentence as you want"
			
+"Let's start with a distinction that should be obvious but is often overlooked: not every newly founded company is a startup." +
" Millions of companies are started every year in the US. " +
"Only a tiny fraction are startups. " +
"Most are service businesses—restaurants, barbershops, plumbers, and so on. " +
"These are not startups, except in a few unusual cases. " +
"A barbershop isn't designed to grow fast. Whereas a search engine, " +"for example, is." +
"When I say startups are designed to grow fast, I mean it in two senses. " +
"Partly I mean designed in the sense of intended, because most startups fail. " +
"But I also mean startups are different by nature, in the same way a redwood seedling has a different destiny from a bean sprout." +
"That difference is why there's a distinct word, startup, for companies designed to grow fast. " +
"If all companies were essentially similar, but some through luck or the efforts of their founders ended up growing very fast, we wouldn't need a separate word." +
" We could just talk about super-successful companies and less successful ones. " +
"But in fact startups do have a different sort of DNA from other businesses. Google is not just a barbershop whose founders were unusually lucky and hard-working." +
" Google was different from the beginning." +
"To grow rapidly, you need to make something you can sell to a big market. " +
"That's the difference between Google and a barbershop. " +
"A barbershop doesn't scale." +
"For a company to grow really big, it must (a) make something lots of people want, and (b) reach and serve all those people. " +
"Barbershops are doing fine in the (a) department. Almost everyone needs their hair cut. " +
"The problem for a barbershop, as for any retail establishment, is (b). " +
"A barbershop serves customers in person, and few will travel far for a haircut. " +
"And even if they did the barbershop couldn't accomodate them."; 
	
	
	SentenceExtractor se=new SentenceExtractor(text);
	String[] sentences=se.action();
	
	List<String> cleanedText=new ArrayList<String>();

	TextPreprocessor tpe=new TextPreprocessor();
	for(String sentence:sentences)
	{
		System.out.println(sentence);
		cleanedText.add(tpe.getPreprocessedText(sentence));
	}
	

	System.out.println("****Cleaned Text*****");
	System.out.println(cleanedText);
	
	
	//Document dc=new Document(sentences);
	
	//double[][] sm=dc.similarityMatrix();
	
	
	//TextPreprocessor tpe=new TextPreprocessor();
	
	DocumentVectorCalculator dvc=new DocumentVectorCalculator(cleanedText);
	//System.out.println(dvc.generateTF_IDFScores().toString());
	List<Hashtable<String,Double>> score=dvc.generateTF_IDFScores();
	
	SimilarityMatrixGenerator simg=new SimilarityMatrixGenerator(score);
	
	double[][] sm=simg.similarityMatrixGenerator();
	
	
	
	
	
	
	
	
	//double[][] sm=smg.getSimilarityMatrix(fileName);
	
	System.out.println("****Printing the similarity matrix generated****");
	
	for(int i=0;i<sm.length;i++)
	{
			for(int j=0;j<sm[i].length;j++)
			{
				System.out.print(" "+sm[i][j]);
		
		
				if(sm[i][j]>1)
					System.out.println("******Problem******");
			}
		System.out.println("      ");
		
	}
	
	
	int numberOfClusters=3;
	
	FRECCAAlgo frecca =new FRECCAAlgo(sm, numberOfClusters);
	double[][] temp=frecca.getMembershipValues();
	
	System.out.println("printing the member ship values");
	
	double sum=0.0;
	for(int i=0;i<temp.length;i++)
	{
		for(int j=0;j<temp[i].length;j++)
		{
			temp[i][j]=temp[i][j]+Math.random();
			System.out.print(temp[i][j]+" ");
			
		}
		System.out.println(" ");
		
	}

	
	for(int i=0;i<temp.length;i++)
	{
		sum=0.0;
		for(int j=0;j<temp[i].length;j++)
		{
			sum+=temp[i][j];
			
		}
		for(int j=0;j<temp[i].length;j++)
		{
			temp[i][j]/=sum;;
			
		}
		
	}
	
	System.out.println("Final...");
	for(int i=0;i<temp.length;i++)
	{
		for(int j=0;j<temp[i].length;j++)
		{
			System.out.print(temp[i][j]+" ");
			
		}
		System.out.println(" ");
		
	}


	
	
}


public double[][] getClusterValues(String text,int numOfClusters){
	
	SentenceExtractor se=new SentenceExtractor(text);
	String[] sentences=se.action();
	sentenceList=sentences;
	List<String> cleanedText=new ArrayList<String>();

	TextPreprocessor tpe=new TextPreprocessor();
	for(String sentence:sentences)
	{
		System.out.println(sentence);
		cleanedText.add(tpe.getPreprocessedText(sentence));
	}
	

	System.out.println("****Cleaned Text*****");
	System.out.println(cleanedText);
	
	
	//Document dc=new Document(sentences);
	
	//double[][] sm=dc.similarityMatrix();
	
	
	//TextPreprocessor tpe=new TextPreprocessor();
	
	DocumentVectorCalculator dvc=new DocumentVectorCalculator(cleanedText);
	//System.out.println(dvc.generateTF_IDFScores().toString());
	List<Hashtable<String,Double>> score=dvc.generateTF_IDFScores();
	
	SimilarityMatrixGenerator simg=new SimilarityMatrixGenerator(score);
	
	double[][] sm=simg.similarityMatrixGenerator();
	
	
	
	
	
	
	
	
	System.out.println();
	System.out.println();	System.out.println();	System.out.println();
	System.out.println("****Printing the similarity matrix generated****");
	System.out.println();
	System.out.println();	System.out.println();	System.out.println();
	
	
	
	for(int i=0;i<sm.length;i++)
	{
			for(int j=0;j<sm[i].length;j++)
			{
		

				
				if(sm[i][j]==0.0){
						sm[i][j]+=0.5;
				
		
				}
				System.out.print(" "+sm[i][j]);
				
					
			}
		System.out.println("      ");
		
	}
	
	
	int numberOfClusters=numOfClusters;
	
	FRECCAAlgo frecca =new FRECCAAlgo(sm, numberOfClusters);
	double[][] temp=frecca.getMembershipValues();
	
	System.out.println("printing the member ship values");
	
	
	/*
	
	System.out.println("Final...");
	for(int i=0;i<temp.length;i++)
	{
		for(int j=0;j<temp[i].length;j++)
		{
			System.out.print(temp[i][j]+" ");
			
		}
		System.out.println(" ");
		
	}
	*/
	
	
	
	
	
	
	
	double sum=0.0;
	for(int i=0;i<temp.length;i++)
	{
		for(int j=0;j<temp[i].length;j++)
		{
			temp[i][j]=temp[i][j]+Math.random();
			System.out.print(temp[i][j]+" ");
			
		}
		System.out.println(" ");
		
	}

	
	for(int i=0;i<temp.length;i++)
	{
		sum=0.0;
		for(int j=0;j<temp[i].length;j++)
		{
			sum+=temp[i][j];
			
		}
		for(int j=0;j<temp[i].length;j++)
		{
			temp[i][j]/=sum;;
			
		}
		
	}
	
	System.out.println("Final...");
	for(int i=0;i<temp.length;i++)
	{
		for(int j=0;j<temp[i].length;j++)
		{
			System.out.print(temp[i][j]+" ");
			
		}
		System.out.println(" ");
		
	}
	
	return temp;

}

public String[] getExtractedSentences(){
	return sentenceList;
}

}
