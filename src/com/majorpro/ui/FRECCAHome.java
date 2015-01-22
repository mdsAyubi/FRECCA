package com.majorpro.ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.majorpro.frecca.Controller;

public class FRECCAHome implements ActionListener  {

	JFrame mainFrame; 
	JLabel heading,textLabel,fileLabel,rankLabel,orLabel,relatedWorkLabel;
	JFileChooser chooseFile;
	JTextArea urls,textArea,rankTextArea;
	JScrollPane scText,scUrls,scrankTextArea;
	JButton submit, file,clear;
	JTextField fileField;
	JFileChooser chooser=null;
	String inputText=null;
	File inputFilePath=null;
	String rstr="";
	int frmWidth,frmHeight;
	boolean flag=false;
	String fileName=null;
	String finalTextString="";
	
	public FRECCAHome()
	{
		
		//frame width , height 
		frmWidth=1000;
		frmHeight=600;
		
		// All Labels here :)
		mainFrame = new JFrame("Fuzzy Relational Clustering");
		heading = new JLabel("Fuzzy Relational Clustering");
		textLabel = new JLabel("Paste the Plain Text");
		fileLabel = new JLabel("Choose a File(.doc,.txt,.pdf)");
		rankLabel = new JLabel("Ranking");
		orLabel = new JLabel("OR");
		rankLabel = new JLabel("Number of Clusters");
		relatedWorkLabel = new JLabel("Clusters");
		
		// Other Components //
		chooseFile = new JFileChooser();
		urls = new JTextArea();
		textArea = new JTextArea();
		rankTextArea = new JTextArea();
		scText = new JScrollPane(textArea);
		scUrls = new JScrollPane(urls);
		scrankTextArea = new JScrollPane(rankTextArea);
		fileField = new JTextField(50);
		
		//Buttons here :)
		submit = new JButton("Cluster");
		file = new JButton("Choose File");
		clear=new JButton("Clear");
		
		//All set Bounds Here :)
		heading.setBounds(180, 20, 600, 60);
		heading.setFont(new Font("Verdana",50, 30));
		
		textLabel.setBounds(200,100,frmWidth-700,frmHeight-550);
		scText.setBounds(50,150,frmWidth/2,frmHeight-380);
		orLabel.setFont(new Font("Verdana",50, 25));
		orLabel.setBounds(250, frmHeight-220, 50, 50);
		
		
		fileLabel.setBounds(50,frmHeight-150,155,20);
		fileField.setBounds(205, frmHeight-150, 200, 20);
		file.setBounds(405, frmHeight-150, 100, 20);
		file.addActionListener(this);
		
		rankLabel.setBounds(frmWidth/2 + 250,120 , frmWidth, 20);
		scrankTextArea.setBounds(frmWidth/2 + 80,150,frmWidth/2-120,frmHeight-500);
		relatedWorkLabel.setBounds(frmWidth/2 +220, 280, frmWidth, 20);
		scUrls.setBounds(frmWidth/2 +80, 310, frmWidth/2 - 120, frmHeight - 380);
		submit.setBounds(200, frmHeight-110, 150, 30);
		submit.addActionListener(this);
		
		
		clear.setBounds(375, frmHeight-110, 100, 30);
		clear.addActionListener(this);
		
		//rankTextArea.setEditable(false);
		urls.setEditable(false);
		
		mainFrame.setLayout(null);
		mainFrame.getContentPane().add(heading);
		mainFrame.getContentPane().add(textLabel);
		mainFrame.getContentPane().add(scText);
		mainFrame.getContentPane().add(orLabel);
		mainFrame.getContentPane().add(fileLabel);
		mainFrame.getContentPane().add(fileField);
		mainFrame.getContentPane().add(file);
		mainFrame.getContentPane().add(rankLabel);
		mainFrame.getContentPane().add(scrankTextArea);
		mainFrame.getContentPane().add(relatedWorkLabel);
		mainFrame.getContentPane().add(scUrls);
		mainFrame.getContentPane().add(submit);
		mainFrame.getContentPane().add(clear);
		
		
		
		mainFrame.setLocation(200, 100);
		mainFrame.setSize(frmWidth, frmHeight);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}
	
	
	String getStringText(){
		return rstr;
	}
	
	public void actionPerformed(ActionEvent ev) {
		// TODO Auto-generated method stub
		
		Object  clicked =ev.getSource();
		
		if(clicked == submit)
		{
				inputText = textArea.getText();
				rstr=checkSubmitOption(inputText);
				if(flag==false)
				{
					finalTextString = inputText;
				}
				if(flag==true && !rstr.equals("false"))
				{
				InputController InC = new InputController(rstr,rstr,flag);
				try {
					finalTextString =  InC.checkStatus();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				
				
				
				presentOutput(finalTextString, Integer.parseInt(rankTextArea.getText()));
				
				
				
				
				
		}
		else if(clicked == file)
		{
			chooser = new JFileChooser();
			 FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "DOC , PDF & TXT Files", "doc", "pdf","txt");
				    chooser.setFileFilter(filter);
				    int returnVal = chooser.showOpenDialog(null);
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				    	inputFilePath = chooser.getSelectedFile();	    	
				    	fileName = chooser.getName(inputFilePath);
				     fileField.setText(inputFilePath.getPath());
				     }
		}
		else if(clicked==clear){
			textArea.setText("");
			rankTextArea.setText("");
			urls.setText("");
		}

	}
	
	public String checkSubmitOption(String inputText)
	{
		String temp="";
		
		if (inputText.equals("") && inputFilePath !=null){	
		temp =  inputFilePath.getPath();
		flag=true;
		}
		else if (!inputText.equals("") && inputFilePath == null){
			temp =  inputText;
		}
		else {
			System.out.println("choose at least one");
			temp = "false";
		}
		
		return temp;
	}

	
	/**
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	//	 javax.swing.SwingUtilities.invokeLater(new Runnable() {
	  //          public void run() {
	            	new FRECCAHome();
	    //        }
	      //  });
			
	}
	
	
	private void presentOutput(String finalString,int numberOfCluster){
		Controller control=new Controller();
		double[][] values=control.getClusterValues(finalString, numberOfCluster);
		String sentences[]=control.getExtractedSentences();
		
		int[] maxPos=new int[sentences.length];
		
		
		for(int i=0;i<values.length;i++){
			maxPos[i]=getMax(values[i]);
		}
		System.out.println("Max Membership positions...");
		for(int i=0;i<maxPos.length;i++){
			System.out.println(maxPos[i]);
		}
		
		textArea.setText("");
		textArea.setText(finalString);
		urls.setText("");
		for(int i=0;i<numberOfCluster;i++){
			urls.append("Cluster Number "+(i+1)+"\n");
			urls.append("\n");
			for(int j=0;j<maxPos.length;j++){
				if(maxPos[j]==i)
				urls.append(sentences[j]+"\n");	
			}
			urls.append("\n");	
			urls.append("\n");	
			
		}
		
		
		
	}
	
	private int getMax(double[] v){
		double max=v[0];
		int pos=0;
		for(int i=1;i<v.length;i++){
			if(v[i]>max){max=v[i];pos=i;}
		}
		return pos;
	}
	
}
