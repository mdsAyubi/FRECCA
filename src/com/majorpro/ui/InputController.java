package com.majorpro.ui;


public class InputController {

	/**
	 * @param args
	 */
	
	String f,fileName,textString;
	boolean flag;
	
	public InputController(String fileName, String f , boolean flag){
	this.f=f;
	this.flag=flag;
	this.fileName=fileName;
	}
	
	public String checkStatus() throws Exception
	{
		if(flag==true)
		{
			FileController fc = new FileController(fileName);
			textString = fc.processFile();
		}
		
		else if(flag==false && f.equals("false")){
			// print error nothing choosen
		}
		
		return textString;
	}
	
	
	}
