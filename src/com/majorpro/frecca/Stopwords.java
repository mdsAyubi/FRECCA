package com.majorpro.frecca;

import java.io.*;

/**
 * Class that can test whether a given string is a stop word.
 * Lowercases all words before the test.
 *
 * @author Eibe Frank (eibe@cs.waikato.ac.nz)
 * @version 1.0
 */
public abstract class Stopwords implements Serializable {
  
  /**
	 * 
	 */
	private static final long serialVersionUID = 5286894904787609240L;

/** 
   * Returns true if the given string is a stop word.
   */
  public abstract boolean isStopword(String str);
}


