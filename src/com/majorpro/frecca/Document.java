package com.majorpro.frecca;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;


public class Document {

	private HashSet<String> stopWords=new HashSet<String>();    //All the stop words
	private List<String> taggedSentences=new ArrayList<String>(); // raw sentences read from document
	
	private Hashtable<String,Integer> wordsTable=new Hashtable<String,Integer>(); //words frequencies
	private Hashtable<String,Double> wordsIDF=new Hashtable<String,Double>();   //idf frequencies of everyword
	private Hashtable<String,Double> wordsTF=new Hashtable<String,Double>();   //tf frequencies of everyword
	
	private List<Hashtable<String,Integer>> slist=new ArrayList<Hashtable<String,Integer>>(); 
	
	private double[] sentenceWeight;
	double[][] similarityMatrix;
	private int[] deg;
	private int max;
	
	
	
	public static String stopWordsArray[] = { "a","A","The" ,"about", "above", "above",
        "across", "after", "afterwards", "again", "against", "all",
        "almost", "alone", "along", "already", "also", "although",
        "always", "am", "among", "amongst", "amoungst", "amount", "an",
        "and", "another", "any", "anyhow", "anyone", "anything", "anyway",
        "anywhere", "are", "around", "as", "at", "back", "be", "became",
        "because", "become", "becomes", "becoming", "been", "before",
        "beforehand", "behind", "being", "below", "beside", "besides",
        "between", "beyond", "both", "bottom", "but", "by", "call",
        "can", "cannot", "cant", "co", "com", "con", "could", "couldnt", "cry",
        "de", "describe", "detail", "do", "done", "down", "due", "during",
        "each", "eg", "eight", "either", "eleven", "else", "elsewhere",
        "empty", "enough", "etc", "even", "ever", "every", "everyone",
        "everything", "everywhere", "except", "few", "fifteen", "fify",
        "fill", "find", "fire", "first", "five", "for", "former",
        "formerly", "forty", "found", "four", "free", "from", "front", "full",
        "further", "get", "give", "go", "had", "has", "hasnt", "have",
        "he", "hence", "her", "here", "hereafter", "hereby", "herein",
        "hereupon", "hers", "herself", "him", "himself", "his", "how",
        "however", "hundred", "ie", "if", "in", "inc", "indeed",
        "interest", "into", "is", "it", "its", "itself", "keep", "last",
        "latter", "latterly", "least", "less", "ltd", "made", "many",
        "may", "me", "meanwhile", "might", "mill", "mine", "more",
        "moreover", "most", "mostly", "move", "much", "must", "my",
        "myself", "name", "namely", "neither", "net", "never", "nevertheless",
        "next", "nine", "no", "nobody", "none", "noone", "nor", "not",
        "nothing", "now", "nowhere", "of", "off", "often", "on", "once",
        "one", "only", "onto", "or", "org", "other", "others", "otherwise", "our",
        "ours", "ourselves", "out", "over", "own", "part", "per",
        "perhaps", "please", "put", "rather", "re", "same", "see", "seem",
        "seemed", "seeming", "seems", "serious", "several", "she",
        "should", "show", "side", "since", "sincere", "six", "sixty", "so",
        "some", "somehow", "someone", "something", "sometime", "sometimes",
        "somewhere", "still", "such", "system", "take", "ten", "than",
        "that", "the", "their", "them", "themselves", "then", "thence",
        "there", "thereafter", "thereby", "therefore", "therein",
        "thereupon", "these", "they", "thickv", "thin", "third", "this",
        "those", "though", "three", "through", "throughout", "thru",
        "thus", "to", "together", "too", "top", "toward", "towards",
        "twelve", "twenty", "two", "un", "under", "until", "up", "upon",
        "us", "very", "via", "was", "we", "well", "were", "what",
        "whatever", "when", "whence", "whenever", "where", "whereafter",
        "whereas", "whereby", "wherein", "whereupon", "wherever",
        "whether", "which", "while", "whither", "who", "whoever", "whole",
        "whom", "whose", "why", "will", "wikipedia", "with", "within", "without",
        "would", "yet", "you", "your", "yours", "yourself", "yourselves",
        "the" };
	
	
	
	
	public Document(String[] sentences)throws Exception
	{
		for(String word:stopWordsArray )
		stopWords.add(word); //create stop words HashSet

		
		for(String stemp:stopWords)
			System.out.print("Interesting "+stemp);
    	
		/*
		sc=new Scanner(new File("doc.txt"));
		StringBuilder tempString=new StringBuilder();
		while(sc.hasNextLine())
			tempString.append(sc.nextLine());
		
			temp=tempString.toString();
		sc.close();
		
		StringTokenizer st=new StringTokenizer(temp,".");
		*/
		for(String sentence:sentences)
			taggedSentences.add(sentence);
		
		
		for(String stemp:taggedSentences)
			System.out.print("Interesting "+stemp);
    	
	max=0;
	
	for(String t:taggedSentences)
	{
	
		StringTokenizer stNew=new StringTokenizer(t," ");
		Hashtable<String, Integer> tempHash=new Hashtable<String,Integer>();
		
		
		while(stNew.hasMoreTokens())
		{
			String token=stNew.nextToken();
			if(stopWords.contains(token))continue;
			else
			{	
				if(tempHash.containsKey(token))
				tempHash.put(token, Integer.valueOf((tempHash.get(token)+1)));
				
				else
				tempHash.put(token, 1);
			
				if(wordsTable.containsKey(token))
				{
					int freq=wordsTable.get(token)+1;
					if(max<freq)max=freq;
					wordsTable.put(token, freq);
				}
				else
				wordsTable.put(token, 1);
			
			
			
			}
		
		}
		slist.add(tempHash);	
	}
	
	
	deg=new int[slist.size()];
	
	for(int i=0;i<slist.size();i++)
		deg[i]=1;
	
	
	for(int i=0;i<slist.size();i++)
		System.out.print("Interesting"+slist.get(i).toString());
	


	System.out.print("Interesting"+wordsTable.toString());
	
	
	System.out.print("Interesting Max="+max);
	
	
	
	
}
	
	
	
	
	
	
	
	
	public List<String> getSentences(){
		return taggedSentences;
	}
	
	public Hashtable<String,Integer> getWordsTable(){
			return wordsTable;
	}
	
	
	public Hashtable<String,Double>getWordsIDF()
	{
		return wordsIDF;
	}
	
	
	
	public boolean containsWord(String word){
		return wordsTable.containsKey(word);
	}
	
	
	
	public int getMax(){
		return max;
	}
	
	
	private void calcTF_IDF()
	{
		for(String temp:wordsTable.keySet())
		{
			wordsTF.put(temp, wordsTable.get(temp)/(double)max);
			
			wordsIDF.put(temp, calcIDF(temp));
		}
		System.out.println(wordsIDF.toString());
	}

	/*
	public double[] action()
	{
		calcTF_IDF();
		sentenceWeight();
		similarityMatrix=similarityMatrix();
		//d.getResults();
		
		
		for(int i=0;i<slist.size();i++)
		{
			for(int j=0;j<slist.size();j++)
				Log.d("Interesting"," "+similarityMatrix[i][j]);
			
			
			
		}
		
		
		Lexrank lx=new Lexrank(slist,similarityMatrix,0.85,deg);
		double[] result1=lx.PowerMethod(.001);
		Log.d("Interesting"," Resultant MAtrix from Lexrank");
		
		for(double temp:result1)
			Log.d("Interesting"," "+temp);
		
		
		
		
		
		return result1; 
		
		for(double f:result1)
			System.out.println(" "+f);
		

		for(int  r:deg)
			System.out.println(" "+r);
			
	}
	
	
	*/
	/*
public static void main(String...a)throws Exception
{
	Document1 d=new Document1();
	d.calcTF_IDF();
	d.sentenceWeight();
	d.similarityMatrix=d.similarityMatrix();
	//d.getResults();
	
	
	for(int i=0;i<d.slist.size();i++)
	{
		for(int j=0;j<d.slist.size();j++)
			System.out.print(" "+d.similarityMatrix[i][j]);
		
		System.out.println();
		
	}
	
	
	Lexrank lx=new Lexrank(d.slist,d.similarityMatrix,0.85,d.deg);
	double[] result1=lx.PowerMethod(.001);
	
	
	for(double f:result1)
		System.out.println(" "+f);
	

	for(int  r:d.deg)
		System.out.println(" "+r);
	
}
*/
private double calcIDF(String word)
{
	int count=0;
	for(Hashtable<String,Integer> temp:slist)
	{
		if(temp.containsKey(word))count++;
	}
	return Math.log(slist.size()/count);
}


private void sentenceWeight()
{
	sentenceWeight=new double[slist.size()];
	int i=0;
	for(String temp:slist.get(i).keySet())
	{
		if(i==slist.size())break;
		sentenceWeight[i]+=Math.pow(wordsTF.get(temp)*wordsIDF.get(temp), 2);
		sentenceWeight[i]=Math.sqrt(sentenceWeight[i]);
		i++;
	}
	
}

private double cosineXY(int i,int j)
{
	double result=0.0;
	double X,Y,XY;
	X=Y=XY=0.0;
	
	
	for(String temp:slist.get(i).keySet())
	{
		if(slist.get(j).containsKey(temp))
			XY+=slist.get(i).get(temp)*slist.get(j).get(temp)*Math.pow(wordsIDF.get(temp),2);
	}
	X=sentenceWeight[i];
	Y=sentenceWeight[j];
	
	
	
	result=XY/(X*Y);
	
	
	return result;
}


public double[][] similarityMatrix()
{
	double[][] result=new double[slist.size()][slist.size()];
	int i=0;

	calcTF_IDF();
	sentenceWeight();
	
	
	for(i=0;i<slist.size();i++)
	{
		for(int j=0;j<slist.size();j++)
			result[i][j]=0.0;
	}
	
	for(i=0;i<slist.size();i++)
		result[i][i]=1;
	
	
	for(i=0;i<slist.size();i++)
	{
		for(int j=0;j<slist.size();j++)
		{
			
			if(i!=j)
			{
				result[i][j]=cosineXY(i, j);
		
				/*
				if(result[i][j]>=0.1)
				{result[i][j]=1.0;deg[i]++;}
				else
				result[i][j]=0.0;
				*/
			}
		}
	}

	return result;
}



public void getResults()
{
	
	Hashtable<Integer,String> result=new Hashtable<Integer,String>();
	int i=0;
	for(String temp:taggedSentences)
	{
		result.put(deg[i], temp);
		i++;
	}
	
	System.out.println(result.toString());
	
	
}




}