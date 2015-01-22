package com.majorpro.frecca;

public class FRECCAAlgo {

	private double[][] similarityMatrix;	//S(i,j), similarity matrix
	private int C;							//Number of Clusters
	private int N;							//Number of Sentences
	private double[][] p;					//P(i,j) Cluster Membership Values
	
	
	public FRECCAAlgo(double[][] sm,int C)
	{
		this.similarityMatrix=sm;
		this.C=C;
		this.N=similarityMatrix.length;
	}
	
	public double[][] getMembershipValues()
	{
		/*Initialization Step
		 * Initialize the membership values with a random function.
		 * This is the first step of the algorithm, the membership values are
		 * double[][] p array which corresponds to the p(i,m) notation of the 
		 * membership value found in the paper.
		 * The rows are number of sentences i.e. N
		 * The columns are number of clusters i.e. C
		 */
		  //Number of sentences, same as paper
		
		System.out.println("Number of Sentences= "+N);
		System.out.println("Number of Clusters= "+C);
		
		
		
		double rowSum=0.0;
		p=new double[N][C];			
		double[][] oldP=new double[N][C];
		
		for(int i=0;i<N;i++)			//Fill random values
		{
			
			rowSum=0.0;
			for(int m=0;m<C;m++)
			{
				p[i][m]=Math.random();
				rowSum+=p[i][m];
			}
		/* The Normalization Step
		 * Every row gives the membership value of a sentence to various clusters
		 * Therefore it is only logical that the sum of all the membership 
		 * probabilities should sum up to be 1.
		 * Every entry of p(i,j) or p[i][m] is divided by the row sum.
		 * In paper, line number 7-8 correspond to the following code.
		 * i.e. for m=1 to C
		 *  	p(i,m)=p(i,m)/Sum(p(i,j)) for all j=1 to C
		 */
			for(int m=0;m<C;m++)
			{
				p[i][m]/=rowSum;
			}
		
		}// "end for" of line number 9 of paper
		
		System.out.println("Printing Matrix P ");
		printMatrix(p);
		
		
		/*Equal Priors
		 * 11.for m=1 to C
		 * 12.pie(m)=1/C
		 * Following code corresponds
		 */
		
		double[] pie=new double[C];
		for(int m=0;m<C;m++)
		{
			pie[m]=1/(double)C;
		}// "end for" of line number 13 of paper
		
		System.out.println("Printing Pie ");
		printVector(pie);
		
		/*The Heart of the Algorithm
		 * These steps are to be repeated until the membership value converges
		 * for all the clusters or the maximum number of iterations are 
		 * exhausted.
		 * Pay attention to the code that follows...:)
		 * 
		 * It consists of two major steps,
		 * 1. The Expectation Step
		 * 2. Maximization Step
		 * 
		 * FRECCA comes under the class of EM algorithms, hence the two steps.
		 * 
		 */
		
		
		double error=0.01;					//The tolerance level
		double[][] w=new double[N][N];		//Weighted Affinity Matrix, w(i,j)
		double[] pr=new double[N];			//PageRank scores of all sentences(Nodes), PR(i,m)
		double[][] l=new double[N][C];			//Likelihoods, i.e. l(i,m)
		
		//The following do...while() loop is same as "repeat until convergence"	
		do
		{
			
			/*The EXPECTATION STEP, starts from here
			 * The first step of EM , i.e. Expectation step
			 */
			
			for(int m=0;m<C;m++)
			{
				

				//Creating the weighted affinity matrix for cluster m
				for(int i=0;i<N;i++)
				{
					for(int j=0;j<N;j++)
					{
						//w(i,j)=s(i,j)*p(i,m)*p(j,m)
						w[i][j]=similarityMatrix[i][j]*p[i][m]*p[j][m];
					}
				}//"end for" of line number 22
				
		//		System.out.println("Printing Weighted Affinity Matrix ");
			//	printMatrix(w);
				
				
				
				/*Calculate PageRank scores for cluster m
				 * This is done using the equation of PageRank, implemented 
				 * in the function getPageRankScores() which takes a two dimensional
				 * similarity matrix and gives the Eigen vector of PageRank scores
				 * of all the nodes in the graph represented by the similarity matrix 
				 * 
				 */
				pr=getPageRankScores(w);
				
				System.out.println("Printing PageRank Scores ");
				printVector(pr);
				
				//Assign PageRank scores to likelihoods
				for(int i=0;i<N;i++)
				l[i][m]=pr[i];
			
			}// "end for" for line number 29
			
			
			
			System.out.println("Printing Likelihood Values ");
			printMatrix(l);
			

			//Calculating new cluster membership values
		
			for(int i=0;i<N;i++)			//Saving old p
			{
				for(int m=0;m<C;m++)
				{
					oldP[i][m]=p[i][m];
				}
			}
			for(int i=0;i<N;i++)			//Calculating new p
			{
				rowSum=0.0;
				for(int m=0;m<C;m++)
				{
					p[i][m]=pie[m]*l[i][m];
					rowSum+=p[i][m];
				}
				
				for(int m=0;m<C;m++)
				{
					p[i][m]/=rowSum;
				}
				
			}//"end for" for line number 35
		
			
			System.out.println("Printing Matrix P ");
			printMatrix(p);
			
			
		/*The MAXIMIZATION STEP
		 * The second step of EM
		 * boy...we r nearing the end.. :) remember coffee lunch n movie..:)
		 */
			
		//Updating the mixing coefficients
			for(int m=0;m<C;m++ )
			{
				pie[m]=(1/(double)N)*sumOfMemValuesFor(m);
			}//"end for" for line number 40
			
		error=getErrorValue(p,oldP);
		System.out.println(" Error= "+error);
		
		}while(error>0.001); // "end repeat" on line number 41
		
		
		return p;
				
	}
	
	
	
	//Utility methods for use in the getMembershipValues() method
	
	
	private double[] getPageRankScores(double[][] w)
	{
		double[] PR=new double[w.length];
		double[] oldPR=new double[w.length];
		double dampingFactor=0.85;
		double sumOfAllWeights=0.0;
		double error=0.001;
		//Initialization
		for(int i=0;i<w.length;i++)
		PR[i]=1/(double)N; //or pr[i]=1/w.length;
		
		
		for(int i=0;i<w.length;i++)
		{
			for(int j=0;j<w[i].length;j++)
				sumOfAllWeights+=w[i][j];
		}
		
		do
		{
			double temp=0.0;
			
			//Saving old PageRank Values
			for(int i=0;i<PR.length;i++)
				oldPR[i]=PR[i];
				
			
			//Calculating New PageRank Values
			for(int i=0;i<N;i++)
			{
			
				for(int j=0;j<N;j++)
				{
				temp=(w[i][j]*PR[j])/sumOfAllWeights;
				}
			
				PR[i]=(1-dampingFactor)+dampingFactor*temp;
			}
		
		error=getErrorInPageRankScores(PR,oldPR);
		}while(error<0.01);
		
		
		
		
		return PR;
	}
	
	private double sumOfMemValuesFor(int m)
	{
		double sum=0.0;
		
		for(int i=0;i<N;i++)
		{
			sum+=p[i][m];
		}
		
		return sum;
	}
	
	private double getErrorValue(double[][] newP,double[][] oldP)
	{
		double[][] temp=new double[N][C];
		double sum=0.0;
		for(int i=0;i<N;i++)
		{
			for(int j=0;j<C;j++)
			{
				temp[i][j]=Math.pow((newP[i][j]-oldP[i][j]),2);
				sum+=temp[i][j];
			}
		}
		
		
		
		
		return Math.sqrt(sum);
	}

private double getErrorInPageRankScores(double[] newPR,double[] oldPR)
	{
	double[] temp=new double[newPR.length];
	double sum=0.0;
	for(int i=0;i<newPR.length;i++)
	{
		
			temp[i]=Math.pow((newPR[i]-oldPR[i]),2);
			sum+=temp[i];
		
	}
	
	
	
	
	return Math.sqrt(sum);

	
	}

private void printVector(double[] arr)
{
	for(int i=0;i<arr.length;i++)
		System.out.print(" "+arr[i]);
	

	System.out.println(" ");
}


private void printMatrix(double[][] arr)
{
	for(int i=0;i<arr.length;i++)
	{
		
		for(int j=0;j<arr[i].length;j++)
		System.out.print(" "+arr[i][j]);
	
		System.out.println(" ");
	}
}

}
