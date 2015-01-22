package com.majorpro.frecca;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.sentdetect.SentenceSample;
import opennlp.tools.sentdetect.SentenceSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

public class SentenceExtractor {
String summarizableString;
String[] sentences;
//Context myContext;


public SentenceExtractor(String summarizableString) {

	this.summarizableString=summarizableString;
	
 
}
public String[] action()
{
 System.out.print("Interesting Sentence detector demo");
try{	
	train("assets/sent.train", "assets/sent.bin");
	//detect("sent.bin");
	detect("assets/en-sent.bin");

	}catch(Exception e){System.out.print("Interesting"+e.toString());}
return sentences;
}



private void train(String trainfilename, String modelfilename) throws IOException {
	System.out.println("Training");
	InputStream fs= new FileInputStream(trainfilename);
	
	ObjectStream<String> lineStream = new PlainTextByLineStream(fs, "UTF-8");
	ObjectStream<SentenceSample> sampleStream = new SentenceSampleStream(lineStream);

	SentenceModel model = SentenceDetectorME.train("en",sampleStream, true, null, /*cutoff*/5, /*iterations*/100);
	OutputStream modelOut = new BufferedOutputStream(new FileOutputStream(modelfilename));
	model.serialize(modelOut);
}



private void detect(String filename) throws IOException {
	InputStream modelIn = new FileInputStream(filename);
	SentenceModel model = new SentenceModel(modelIn);
	SentenceDetector sentenceDetector = new SentenceDetectorME(model);
	
	/*
	String sourceUrlString="http://www.paulgraham.com/icad.html";
	if (sourceUrlString.indexOf(':')==-1) sourceUrlString="file:"+sourceUrlString;
	MicrosoftConditionalCommentTagTypes.register();
	PHPTagTypes.register();
	PHPTagTypes.PHP_SHORT.deregister(); // remove PHP short tags for this example otherwise they override processing instructions
	MasonTagTypes.register();
	Source source=new Source(new URL(sourceUrlString));
	
	// Call fullSequentialParse manually as most of the source will be parsed.
			source.fullSequentialParse();
			
			
			TextExtractor textExtractor=new TextExtractor(source) {
				public boolean excludeElement(StartTag startTag) {
					return startTag.getName()==HTMLElementName.P || "control".equalsIgnoreCase(startTag.getAttributeValue("class"));
				}
			};
			//System.out.println(textExtractor.setIncludeAttributes(true).toString());
	  
			
	*/
/*	System.out.println("Detecting using "+filename+" - easy");
	String sentences[] = sentenceDetector.sentDetect("  First sentence. Second sentence. ");
	for (String sentence: sentences) 
		System.out.println("\t'"+sentence+"'");
	*/
			
	System.out.print("Interesting Detecting using "+filename+" - hard");
	sentences = sentenceDetector.sentDetect(summarizableString);	
	for (String sentence: sentences) 
		System.out.print("Interesting["+sentence+"]");
	
	//String key="sent";
	//Bundle bundle=new Bundle();
	//bundle.putStringArray(key, sentences);
	//Intent i = new Intent(mainActivity, OutputActivity.class);

	//i.putExtra(key, sentences);
	//mainActivity.startActivity(i);

}


	
}
