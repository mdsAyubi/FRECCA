package com.majorpro.frecca;

import java.util.StringTokenizer;

public class StopwordsRemover {

String inputText;
String outputText;
StopwordsEnglish se;

public StopwordsRemover(String inputText) {
	// TODO Auto-generated constructor stub

this.inputText=inputText;
se=new StopwordsEnglish();
}

public String removeStopwords(){
	
	
	StringTokenizer st=new StringTokenizer(inputText);
	StringBuilder temp=new StringBuilder();
	String t;
	while(st.hasMoreTokens()){
		t=st.nextToken();
		if(!se.isStopword(t))
		{
			temp.append(t);
			temp.append(" ");
		}
	}
	
	outputText=temp.toString();
	return outputText;
	
	
	
}

public static void main(String...args){
	
 String inputText="Compatibility of systems of linear constraints over the set of natural numbers. Criteria of compatibility of a system of linear Diophantine equations, strict inequations, and nonstrict inequations are considered. Upper bounds for components of a minimal set of solutions and algorithms of construction of minimal generating sets of solutions for all types of systems are given. These criteria and the corresponding algorithms for constructing a minimal supporting set of solutions can be used in solving all the considered types systems and systems of mixed types.";
 StopwordsRemover sr=new StopwordsRemover(inputText);
 
 System.out.println("Final String After Stopwords Removal:::");

 System.out.println(" ");

 System.out.println(" ");

 System.out.println(sr.removeStopwords());
	
}
}
