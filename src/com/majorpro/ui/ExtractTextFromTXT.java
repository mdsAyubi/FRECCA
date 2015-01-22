package com.majorpro.ui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class ExtractTextFromTXT {

	public String extractTxtText(String args) throws IOException 
	{
		String text="",st="";
		BufferedReader br=null;
		
		try
		{
		FileReader f = new FileReader(args);
		br = new BufferedReader(f);
		while((st=br.readLine())!=null)
			text+=st;
		}
		catch(IOException ioe){}
		finally{
			br.close();	
		}	
		
		return text;
	}	
}
