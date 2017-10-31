import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Random;
import java.io.*;

public class textAnalyzer extends JFrame implements ActionListener
{
	/* Constants for width of window and row height */
	final static int WIDTH = 1000;
	final static int ROW_HEIGHT = 75;
	final static int NUM_ROWS = 10;
	
	/* Labels for calculated data */
	JLabel numLinesLabel = new JLabel(" ");
	JLabel numBlankLinesLabel = new JLabel(" ");
	JLabel numWordsLabel = new JLabel(" ");
	JLabel numCharactersLabel = new JLabel(" ");
	JLabel numSpacesLabel = new JLabel(" ");
	JLabel avgWordLengthLabel = new JLabel(" ");
	JLabel longestWordLabel = new JLabel(" ");
	JLabel mostFrequentLabel = new JLabel(" ");
	
	/* Text Box to receive text file name */
	JTextField fileNameField = new JTextField("",20);
	
    public textAnalyzer() 
    {
    	int height = NUM_ROWS * ROW_HEIGHT;
    	
        //Create and set up the window.
        JFrame frame = new JFrame("Text Analyzer");
        frame.setSize(new Dimension(WIDTH,height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        
        /* Panels for sections of analyzer */
        JPanel analysisPanel = new JPanel();
        analysisPanel.setLayout(new GridBagLayout());
        analysisPanel.setBackground(Color.white);
        
            	
    	/* Text Labels for GUI */
    	JLabel requestFileHeading = new JLabel("Please enter file name: ");
    	requestFileHeading.setHorizontalAlignment(JLabel.RIGHT);
    	JLabel numLinesHeading = new JLabel("Number of Lines: ");
    	numLinesHeading.setHorizontalAlignment(JLabel.RIGHT);
    	JLabel numBlankLinesHeading = new JLabel("Number of Blank Lines: ");
    	numBlankLinesHeading.setHorizontalAlignment(JLabel.RIGHT);
    	JLabel numWordsHeading = new JLabel("Number of Words: ");
    	numWordsHeading.setHorizontalAlignment(JLabel.RIGHT);
    	JLabel numCharactersHeading = new JLabel("Number of Characters: ");
    	numCharactersHeading.setHorizontalAlignment(JLabel.RIGHT);
    	JLabel numSpacesHeading = new JLabel("Number of Spaces: ");
    	numSpacesHeading.setHorizontalAlignment(JLabel.RIGHT);
    	JLabel avgWordLengthHeading = new JLabel("Average Word Length: ");
    	avgWordLengthHeading.setHorizontalAlignment(JLabel.RIGHT);
    	JLabel longestWordHeading = new JLabel("Longest Word: ");
    	longestWordHeading.setHorizontalAlignment(JLabel.RIGHT);
    	JLabel mostFrequentWordHeading = new JLabel("Most Frequent Word in File: ");
    	mostFrequentWordHeading.setHorizontalAlignment(JLabel.RIGHT);
    	
    	/* Analyze button to accept input and begin analyzing */
    	JButton analyzeButton = new JButton("Analyze");
    	
        /* c is used for each component when added */
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 5; // carries through for all
        c.ipady = 5; // carries through for all
        frame.add(requestFileHeading, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(10,10,10,10);
        frame.add(fileNameField, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        frame.add(analyzeButton, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0,0,0,0);
        analysisPanel.add(numLinesHeading, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        analysisPanel.add(numLinesLabel, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        analysisPanel.add(numBlankLinesHeading, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        analysisPanel.add(numBlankLinesLabel, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        analysisPanel.add(numWordsHeading, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        analysisPanel.add(numWordsLabel, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        analysisPanel.add(numCharactersHeading, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        analysisPanel.add(numCharactersLabel, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        analysisPanel.add(numSpacesHeading, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 4;
        analysisPanel.add(numSpacesLabel, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 5;
        analysisPanel.add(avgWordLengthHeading, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 5;
        analysisPanel.add(avgWordLengthLabel, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 6;
        analysisPanel.add(longestWordHeading, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 6;
        analysisPanel.add(longestWordLabel, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 7;
        analysisPanel.add(mostFrequentWordHeading, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 7;
        analysisPanel.add(mostFrequentLabel, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        frame.add(analysisPanel,c);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
        
        analyzeButton.addActionListener(this);
    }	

	public static void main(String[] args) 
	{
		textAnalyzer newAnalysis = new textAnalyzer();
	}
	
	public void actionPerformed(ActionEvent eventName)
	{
		String fileName = fileNameField.getText();
		
		int lines, blankLines, words, characters, spaces;
				
		File file = new File(fileName);

		if(file.exists() && file.canRead())
		{
			try
			{
				lines = Lines(file);
				numLinesLabel.setText("" + lines);;
				blankLines = BlankLines(file);
				numBlankLinesLabel.setText("" + blankLines);
				words = Words(file);
				numWordsLabel.setText("" + words);
				characters = Characters(file);
				numCharactersLabel.setText("" + characters);
				spaces = Spaces(file);
				numSpacesLabel.setText("" + spaces);

				
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
			inWord = false; 					// Set inWord to false at beginning of each line.
				for (int i = 0; i < line.length(); i++) 	// Iterates through the line char by char while 'i' is < string length.
				{		
      				if (line.charAt(i) == ' ' && inWord) 		// If current char is a space and in a word boolean is true.
            			{		
               			inWord = false;
                		}
           			if (!inWord && line.charAt(i) != ' ') 		// If not in a word and char is not a space, in a word is set to true.
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
