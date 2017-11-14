import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class TextFile 
{

	private String filename;
	private int numLines;
	private int numBlankLines;
	private int numWords;
	private int numCharacters;
	private int numSpaces;
	
	public TextFile() // Default Constructor
	{
		filename = "none.txt";
		numLines = 0;
		numBlankLines = 0;
		numWords = 0;
		numCharacters = 0;
		numSpaces = 0;
	}
	
	public TextFile(String name) // Overloaded constructor - just filename
	{
		filename = name;
		
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
	
	public TextFile(String name, int lines, int blanks, int words, int characters, int spaces) // overloaded constructor receives all numbers
	{
		filename = name;
		numLines = lines;
		numBlankLines = blanks;
		numWords = words;
		numCharacters = characters;
		numSpaces = spaces;
	}
	
	// Get functions
	public String getFilename()
	{
		return filename;
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
}