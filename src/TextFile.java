import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

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
	private int avgWordLength;
	private String longestWord;
	private String frequentWord;
	private int frequentWordCount;
	
	public TextFile() // Default Constructor
	{
		filename = "none.txt";
		dateAnalyzed = "0/0/0";
		numLines = 0;
		numBlankLines = 0;
		numWords = 0;
		numCharacters = 0;
		numSpaces = 0;
		avgWordLength = 0;
		longestWord = "";
		frequentWord = "";
		frequentWordCount = 0;
	}
	
	public TextFile(String name) // Overloaded constructor - just filename
	{
		filename = name;
		dateAnalyzed = "00/00/0000";
		
		File file = new File(name);

		if(file.exists() && file.canRead())
		{
			try
			{
				numLines = Lines(file);
				numBlankLines = BlankLines(file);
				numWords = Words(file);
				numCharacters = Characters(file);
				numSpaces = Spaces(file);
				avgWordLength = -1;
				longestWord = "notdone";
				frequentWord = "notdone";
				frequentWordCount = -1;
				
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
		dateAnalyzed = "00/00/0000";

		if(file.exists() && file.canRead())
		{
			try
			{
				numLines = Lines(file);
				numBlankLines = BlankLines(file);
				numWords = Words(file);
				numCharacters = Characters(file);
				numSpaces = Spaces(file);
				avgWordLength = -1;
				longestWord = "notdone";
				frequentWord = "notdone";
				frequentWordCount = -1;
				
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
	
	public TextFile(String name, String date, int lines, int blanks, int words, int characters, int spaces) // overloaded constructor receives all numbers
	{
		filename = name;
		dateAnalyzed = date;
		numLines = lines;
		numBlankLines = blanks;
		numWords = words;
		numCharacters = characters;
		numSpaces = spaces;
		avgWordLength = -1;
		longestWord = "notdone";
		frequentWord = "notdone";
		frequentWordCount = -1;
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
			String line = fileScanner.nextLine();
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
	
	public static int Words(File file) throws IOException 
	{

		FileReader rawFile = new FileReader(file);			// Opened file in file reader.
		BufferedReader buffFile = new BufferedReader(rawFile);		// Opened new buffered reader.
		String line = buffFile.readLine();				// First line copied to string.
		boolean inWord = false;  					// Boolean control variable in a word is false by default.
		int counter = 0;

		while (line != null)   						// While line is not null.
		{						
			inWord = false; //set inWord to false at beginning of each line
			for (int i = 0; i < line.length(); i++) // Iterates through the line char by char while 'i' is < string length.
			{		
      			if (line.charAt(i) == ' ' && inWord) // If current char is a space and in a word boolean is true.
            	{		
               		inWord = false;
                }
           		if (!inWord && line.charAt(i) != ' ') // If not in a word and char is not a space, in a word is set to true.
           		{		
           			inWord = true;
               		counter++;				// Counter is incremented.
           		}
			}

			line = buffFile.readLine();				// Next line sent to string, iterates the while loop.
		}

		buffFile.close();						// Close the reader.
		return counter;							// Return the counter and exit the method.
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
		
		returnString = String.format("%-15s %s %5d %5d %5d %5d %5d %15s %15s %5d", filename, dateAnalyzed, numLines, numBlankLines, numWords, numCharacters, numSpaces, longestWord, frequentWord, frequentWordCount);
		
		return returnString;
	}
	
	public void Output(String stringy) throws IOException {
		File output = null;
		FileWriter scribe = null;
		BufferedWriter writer = null;
		
		String fileName = "Output.txt";
		String content = stringy + "|";
		
		try {
			output = new File(fileName);
			
			if (!output.exists()) {
				output.createNewFile();
				scribe = new FileWriter(output.getAbsoluteFile());
			}
			else {
				scribe = new FileWriter(output.getAbsoluteFile(), true);
			}
			
			writer = new BufferedWriter(scribe);
			
			writer.write(content);
			writer.newLine();
			writer.flush();
		}
		catch (IOException e) {
			e.printStackTrace();	
		}
		finally {
			if (writer != null) {
				writer.close();
			}
			if (scribe != null) {
				scribe.close();
			}
		}
	}
}
