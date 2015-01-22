package com.majorpro.ui;


public class FileController
{
	
	String file="";
	String text="";
	
	public FileController(String file)
	{
		this.file=file;
	}
	
	public String processFile() throws Exception
	{
		
    		String extension = file.substring(file.lastIndexOf(".") + 1, file.length());

    		if(extension.equals("pdf"))
    		{
    			text+=new ExtractTextFromPdf().extractPDF(file);	
    		}
    		else if((extension.equals("doc")) || (extension.equals("docx")))
    		{
                System.out.println("doc file");
    			text+=new ExtractTextFromDoc(file).process();	
    		}
    		else
    		{
    			text+=new ExtractTextFromTXT().extractTxtText(file);
    		}
			    return text;
	}
}
