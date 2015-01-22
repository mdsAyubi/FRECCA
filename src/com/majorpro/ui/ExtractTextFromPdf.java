package com.majorpro.ui;


import org.apache.pdfbox.exceptions.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;


public class ExtractTextFromPdf
{

    public String extractPDF( String args ) throws Exception
    {        
            PDDocument document = null;
            try
            {
                document = PDDocument.load( args );
                if( document.isEncrypted() )
                {
                    try
                    {
                        document.decrypt( "" );
                    }
                    catch( InvalidPasswordException e )
                    {
                        System.err.println( "Error: Document is encrypted with a password." );
                        System.exit( 1 );
                    }
                }

                PDFTextStripper stripper = new PDFTextStripper();
                String text="";
				text += stripper.getText(document);
				return text;
            }
            finally
            {
                if( document != null )
                {
                    document.close();
                }
            }
        
    }

}
