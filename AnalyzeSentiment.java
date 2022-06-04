
import twitter4j.*;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;


public class AnalyzeSentiment {
    private static final int MAXTWEETS = 100; // max tweets at once
    private  SearchTweets tweetslist = new SearchTweets(); // tweets from query
    private Query query; // query that generated the tweets
    private static SeparateChainingHashST<String, Float> sentiment;


    public AnalyzeSentiment(){

    }


    //derived from loadLocation in SearchTweets
    public SeparateChainingHashST<String, Float> loadSentiments (String csvFile){
        sentiment = new SeparateChainingHashST<String, Float>();
        csvFile = "sentiments.csv"; //imports csv
        Scanner scanner=null;

        try{
        	scanner = new Scanner(new File(csvFile)); //creates scanner of csv file
        }catch (FileNotFoundException ex){
        	System.err.println("Error: sentiments not found");
        	return null;
        }
        while(scanner.hasNextLine()){ //while there is more in the csv file
  			String line = scanner.nextLine(); 
  			StringTokenizer tokenizer = new StringTokenizer(line); //tokenizer by line
  			String[] fields = line.split(","); //line split by ,
  			sentiment.put(fields[0],Float.parseFloat(fields[1]));
		}
  		scanner.close();
  		return sentiment;
	}

//calculates the avg sentiment val
	public Float Sentiment(String filename){
		tweetslist.load(filename);//loading tweets from file
		String [] tweets= new String[tweetslist.oldlist.size()]; 
		Float total = new Float(0.0);
		for(int i=0; i< tweets.length; i++){
			String text = tweetslist.oldlist.get(i).getText();//text of the tweets
			String [] stringArray=text.split (" ");//number of words
			for(int j=0;j<stringArray.length;j++){
				if(sentiment.contains(stringArray[j])){
					total =total + sentiment.get(stringArray[j]);
          System.out.println(total/stringArray.length);//divide tweets by number of words
				}
			}		
		}
    return total;
	} 



	public static void main(String [] args){
		AnalyzeSentiment list = new  AnalyzeSentiment(); 

    list.loadSentiments("alltweets.ser"); //analyze sentiment from list
    list.Sentiment("alltweets.ser"); 
	

	}
}
