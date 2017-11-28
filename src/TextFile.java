import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.*;
import java.text.*;

import javax.swing.JOptionPane;

public class TextFile 
{

	private String filename;
	private String dateAnalyzed;
	private int numLines;
	private int numBlankLines;
	private int numWords;
	private int numCharacters;
	private int numSpaces;
	private int avgWordLength = -1;
	private String longestWord = "";
	private String frequentWord = "";
	private int frequentWordCount = -1;
	
	private LinkedList<Word> wordList = new LinkedList<Word>();
	
	public TextFile() // Default Constructor
	{
		filename = "none.txt";
		dateAnalyzed = "0/0/0";
		numLines = 0;
		numBlankLines = 0;
		numWords = 0;
		numCharacters = 0;
		numSpaces = 0;
		avgWordLength = -1;
		longestWord = "";
		frequentWord = "";
		frequentWordCount = -1;
	}
	
	public TextFile(String name) // Overloaded constructor - just filename
	{
		filename = name;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		dateAnalyzed = df.format(date);
		
		File file = new File(name);

		if(file.exists() && file.canRead())
		{
			try
			{
				numLines = Lines(file);
				numBlankLines = BlankLines(file);
				numWords = Words(file);
				numCharacters = Characters(file);

			}
			catch (IOException errorMessage)
			{
				JOptionPane.showMessageDialog(null,errorMessage.getMessage(),"IO Error",JOptionPane.ERROR_MESSAGE);
			}
			
		}
		else
		{
			JOptionPane.showMessageDialog(null,"File Name: " + file + ",  File Read Error\n","File Read Error",JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public TextFile(File file) // Overloaded constructor - just file
	{
		filename = file.getName();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		dateAnalyzed = df.format(date);

		if(file.exists() && file.canRead())
		{
			try
			{
				numLines = Lines(file);
				numBlankLines = BlankLines(file);
				numWords = Words(file);
				numCharacters = Characters(file);
				numSpaces = Spaces(file);
			}
			catch (IOException errorMessage)
			{
				JOptionPane.showMessageDialog(null,errorMessage.getMessage(),"IO Error",JOptionPane.ERROR_MESSAGE);
			}
			
		}
		else
		{
			JOptionPane.showMessageDialog(null,"File Name: " + file + ",  File Read Error\n","File Read Error",JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public TextFile(String name, String date, int lines, int blanks, int words, int characters, int spaces, int average, String longest, String frequent, int freqCount) // overloaded constructor receives all numbers
	{
		filename = name;
		dateAnalyzed = date;
		numLines = lines;
		numBlankLines = blanks;
		numWords = words;
		numCharacters = characters;
		numSpaces = spaces;
		avgWordLength = average;
		longestWord = longest;
		frequentWord = frequent;
		frequentWordCount = freqCount;
	}
	
	// Get functions
	public String getFilename()
	{
		return filename;
	}
	
	public String getDateAnalyzed()
	{
		return dateAnalyzed;
	}
	
	public String getDate()
	{
		return dateAnalyzed;
	}
	
	public int getLines()
	{
		return numLines;
	}
	
	public int getBlanks()
	{
		return numBlankLines;
	}
	
	public int getWords()
	{
		return numWords;
	}
	
	public int getCharacters()
	{
		return numCharacters;
	}
	
	public int getSpaces()
	{
		return numSpaces;
	}
	
	public int getAvgLength()
	{
		return avgWordLength;
	}
	
	public String getLongest()
	{
		return longestWord;
	}
	
	public String getMostFrequent()
	{
		return frequentWord;
	}
	
	public int getFrequentCount()
	{
		return frequentWordCount;
	}
	
	public static int Lines(File file) throws IOException
	{
		int num = 0;
		Scanner fileScanner = new Scanner(file);
		while(fileScanner.hasNextLine())
		{
			fileScanner.nextLine();
			num++;
		}
		fileScanner.close();
		return num;
	}

	public static int BlankLines(File file) throws IOException
	{
		int num = 0;
		Scanner fileScanner = new Scanner(file);
		while(fileScanner.hasNextLine())
		{
			String line = fileScanner.nextLine();
			boolean blank = true;
			for(int i=0;i<line.length();i++)
			{
				if(line.charAt(i) != ' ')
				{
					blank = false;
				}
			}
			if(blank)
			{
				num++;
			}
		}
		fileScanner.close();
		return num;
	}
	
	public int Words(File file) throws IOException 
	{

		FileReader rawFile = new FileReader(file);			// Opened file in file reader.
		BufferedReader buffFile = new BufferedReader(rawFile);		// Opened new buffered reader.
		String line = buffFile.readLine();				// First line copied to string.
		boolean inWord = false;  					// Boolean control variable in a word is false by default.
		int counter = 0, startIndex = 0, endIndex = 0, totalLengths = 0;

		System.out.println(wordList.size());
		
		while (line != null)   						// While line is not null.
		{						
			inWord = false; //set inWord to false at beginning of each line
			for (int i = 0; i < line.length(); i++) // Iterates through the line char by char while 'i' is < string length.
			{	
      			if (line.charAt(i) == ' ' && inWord) // If current char is a space and in a word boolean is true.
            	{		
               		inWord = false;
               		endIndex = i;
               		scrubWord(line.substring(startIndex, endIndex));
                }
      			if (i+1 == line.length() && inWord) // word runs to end of line
      			{
      				endIndex = i+1;
      				scrubWord(line.substring(startIndex,endIndex));
      			}
      			
           		if (!inWord && line.charAt(i) != ' ') // If not in a word and char is not a space, in a word is set to true.
           		{		
           			inWord = true;
           			startIndex = i;
               		counter++;				// Counter is incremented.
           		}
			}

			line = buffFile.readLine();				// Next line sent to string, iterates the while loop.
		}

		buffFile.close();						// Close the reader.
		for(int i = 0; i < wordList.size();i ++)
		{
			totalLengths += wordList.get(i).aWord.length();
			if(wordList.get(i).aWord.length() > longestWord.length())
			{
				longestWord = wordList.get(i).getWord();
			}
			
			if(wordList.get(i).getCount() > frequentWordCount)
			{
				frequentWord = wordList.get(i).getWord();
				frequentWordCount = wordList.get(i).getCount();
			}
		}
		avgWordLength = totalLengths / wordList.size();
		return counter;							// Return the counter and exit the method.
	}

	/* scrubWord will remove any punctuation or numbers from a received string, and if 
	 * there is anything remaining, will check the linked list (wordList) for the word, 
	 * incrementing the count if found, or adding if not found.
	 */
	public void scrubWord(String word)
	{
		String scrubbedWord = "";
		
		for(int i = 0; i < word.length(); i++)
		{
			if(Character.isLetter(word.charAt(i)) || word.charAt(i) == '\'')
			{
				scrubbedWord += word.charAt(i);
			}
		}
		if (scrubbedWord != "")
		{
			Boolean found = false;
			int i = 0;
			while(i < wordList.size() && !found)
			{
				if(wordList.get(i).getWord().equals(scrubbedWord))
				{
					wordList.get(i).addCount();
					found = true;
				}
				i++;
			}
			if(!found)
			{
				wordList.add(new Word(scrubbedWord));
			}
		}
	}
	
	public static int Characters(File file) throws FileNotFoundException
	{
		int numCharacters = 0;  // counter variable initialized to 0
		Scanner fileScanner = new Scanner(file);
		String line; // holds text read from file;
		
		while(fileScanner.hasNextLine())
		{
			line = fileScanner.nextLine();
			numCharacters += line.length();
		}
		fileScanner.close();
		return numCharacters;
	}
	
	public static int Spaces(File file) throws FileNotFoundException
	{
		int num = 0;
	    Scanner fileScanner = new Scanner(file);

	    while(fileScanner.hasNextLine())
	    {
		    String line = fileScanner.nextLine();
	    	for(int i=0;i<line.length();i++)
	    	{
	    		if(line.charAt(i) == ' ')
	    		{
	    			num++;
				}
	    	}
	    }
		fileScanner.close();
		return num;
	}
	
	/** 
		Method to read from the output file for history. Should create a linked list of TextFile Objects and output a string.
	*/
	public String toString()
	{
		String returnString = "";
		
		returnString = String.format("%-15s %-15s %-5d %-5d %-5d %-5d %-5d %-5d %-15s %-15s %-5d", filename, dateAnalyzed, numLines, numBlankLines, numWords, numCharacters, numSpaces, avgWordLength, longestWord, frequentWord, frequentWordCount);
							
		return returnString;
	}

}
