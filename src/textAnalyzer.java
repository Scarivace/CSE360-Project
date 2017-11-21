import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Random;
import java.io.*;

public class textAnalyzer extends JFrame implements ActionListener
{
	/* Constants for width of window and row height */
	final static int WIDTH = 2000;
	final static int ROW_HEIGHT = 150;
	final static int NUM_ROWS = 10;

	final String helpInformation = "This program will open a file you specify and analyze it as follows: \n" +
			"  Count the number of lines in the file,\n" +
			"  Count the number of blank lines in the file,\n" +
			"  Count the number of spaces in the file,\n" +
			"  Count the number of characters in the file, \n" +
			"  Count the number of words in the file,\n" +
			"  Display the longest word in the file,\n" +
			"  Display the most frequent word appearing in the file.\n\n\n" +
			"Definitions:\n" +
			"  A blank line is a line with NO characters on it.\n" +
			"  For character count, all characters are considered valid, including spaces and punctuation\n" +
			"  Words:\n" +
			"    Apostrophes between letters will be assumed to be valid, all others will be removed \n" +
			"    Except as above, any numbers or punctuation inside of a word will be removed\n" +
			"    Words are not validated against a dictionary to ensure correctness\n\n" +
			"A record is kept of the files that have been analyzed and their statistics. \n" +
			"Averages are also kept across files and displayed as indicated.\n\n" +
			"RESET will clear all history and the current file.";
	
	/* Menu Bar */
	JMenuBar menuBar = new JMenuBar();
	JMenu menu = new JMenu("Menu");
	JMenuItem helpOption = new JMenuItem("Help");
	JMenuItem resetOption = new JMenuItem("RESET");
	
	/* Labels for calculated data */
	JLabel numLinesLabel = new JLabel(" ");
	JLabel numBlankLinesLabel = new JLabel(" ");
	JLabel numWordsLabel = new JLabel(" ");
	JLabel numCharactersLabel = new JLabel(" ");
	JLabel numSpacesLabel = new JLabel(" ");
	JLabel avgWordLengthLabel = new JLabel(" ");
	JLabel longestWordLabel = new JLabel(" ");
	JLabel mostFrequentLabel = new JLabel(" ");
	JLabel frequentWordCountLabel = new JLabel(" ");
	
	/* File Chooser */
	final JFileChooser fc = new JFileChooser();
	
	/* Buttons */
	JButton analyzeButton = new JButton("Analyze");
	JButton browseButton = new JButton("Browse...");
	
	/* Text Box to receive text file name */
	JTextField fileNameField = new JTextField("",20);
	
    public textAnalyzer() 
    {
    	int height = NUM_ROWS * ROW_HEIGHT;
    	
    	Border blackline, raisedetched, loweredetched, 
        raisedbevel, loweredbevel, empty;

    	blackline = BorderFactory.createLineBorder(Color.black);
    	raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
    	loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    	raisedbevel = BorderFactory.createRaisedBevelBorder();
    	loweredbevel = BorderFactory.createLoweredBevelBorder();	
    	empty = BorderFactory.createEmptyBorder();
    	
        //Create and set up the window.
        JFrame frame = new JFrame("Text Analyzer");
        frame.setSize(new Dimension(WIDTH,height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        
        /* Panels for sections of analyzer */
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20,20,20,20));
        
        JPanel analysisPanel = new JPanel();
        analysisPanel.setLayout(new GridBagLayout());
        analysisPanel.setBackground(Color.white);
        analysisPanel.setBorder(loweredetched);
                    	
    	/* Text Labels for GUI */
    	JLabel requestFileHeading = new JLabel("Please enter file name or Browse: ");
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
    	JLabel frequentWordCountHeading = new JLabel("Count of Most Frequent Word: ");
    	frequentWordCountHeading.setHorizontalAlignment(JLabel.RIGHT);
    	
    	/* Setup Menu Bar */
    	menuBar.add(Box.createHorizontalGlue());
    	menuBar.add(menu);
    	menu.add(helpOption);
    	menu.add(resetOption);
    	helpOption.addActionListener(this);
      	resetOption.addActionListener(this);
    	frame.setJMenuBar(menuBar);
    	
    	/* buttons to accept input and begin analyzing */
    	analyzeButton.addActionListener(this);
    	analyzeButton.setBackground(Color.red);
    	browseButton.addActionListener(this);
    	
        /* c is used for each component when added */
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 5; // carries through for all
        c.ipady = 5; // carries through for all
        mainPanel.add(requestFileHeading, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(10,10,10,10);
        mainPanel.add(fileNameField, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        mainPanel.add(browseButton, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        mainPanel.add(analyzeButton, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
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
        c.gridy = 8;
        analysisPanel.add(frequentWordCountHeading, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 8;
        analysisPanel.add(frequentWordCountLabel, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        mainPanel.add(analysisPanel,c);
 
        frame.add(mainPanel);
        
        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        analyzeButton.addActionListener(this);
    }	

	public static void main(String[] args) 
	{
		textAnalyzer newAnalysis = new textAnalyzer();
	}
	
	public void actionPerformed(ActionEvent eventName)
	{
		Object source = eventName.getSource();
		TextFile currentFile;
		int lines, blankLines, words, characters, spaces;
		File file = null;
		
		if(source == browseButton)
		{
			int returnVal = fc.showOpenDialog(textAnalyzer.this);

	        if (returnVal == JFileChooser.APPROVE_OPTION) 
	        {
	            file = fc.getSelectedFile();
	            fileNameField.setText(file.getName());
	        } 
	        else 
	        {
	        	JOptionPane.showMessageDialog(null, "No File Selected");
	        }

		}
		
		if(source == analyzeButton)
		{
			if(file == null)
			{
<<<<<<< HEAD
				String fileName = fileNameField.getText();
				file = new File(fileName);
=======
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
					
					//----- RAUL --------------------------------
					Output(lines, blankLines, words, characters, spaces);
					//-------------------------------------------
				}
				catch (IOException errorMessage)
				{
					JOptionPane.showMessageDialog(null,errorMessage.getMessage(),"IO Error",JOptionPane.ERROR_MESSAGE);
				}
>>>>>>> branch 'master' of https://github.com/Scarivace/CSE360-Project.git
			}
			
			currentFile = new TextFile(file);
			numLinesLabel.setText("" + currentFile.getLines());;
			numBlankLinesLabel.setText("" + currentFile.getBlanks());
			numWordsLabel.setText("" + currentFile.getWords());
			numCharactersLabel.setText("" + currentFile.getCharacters());
			numSpacesLabel.setText("" + currentFile.getSpaces());
			avgWordLengthLabel.setText("" + currentFile.getAvgLength());
			longestWordLabel.setText(currentFile.getLongest());
			mostFrequentLabel.setText(currentFile.getMostFrequent());
			frequentWordCountLabel.setText("" + currentFile.getFrequentCount());
			
		}
	
		if(source == helpOption)
		{
			JOptionPane.showMessageDialog(null,helpInformation);
		}
		
		if(source == resetOption)
		{
			// Code to be determined
		}
	}
<<<<<<< HEAD
=======
	
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
		Method to create an output file from the data sent to the actionListiner class. DOUBLE PRINT ERROR!
	*/
	public static void Output(int lines, int blankLines, int words, int characters, int spaces) throws IOException {
		
		File output = null;							 // Object Declarations.
		FileWriter scribe = null;
		BufferedWriter writer = null;

		String fileName = "Output.txt";						 // String Declarations.
		String content = lines + " " + blankLines + " " + words + " " + characters + " " + spaces + "\n";
		
		try {
			output = new File(fileName);
			
			if (!output.exists()) {						 // If the file doesn't exist then create it.
				output.createNewFile();
			
				scribe = new FileWriter(output.getAbsoluteFile()); 	 // THE SOURCE OF ERRORS.

			}
			else {
				scribe = new FileWriter(output.getAbsoluteFile(), true); // THE SOURCE OF ERRORS.
			}
			
			writer = new BufferedWriter(scribe);
			
			writer.write(content);
			
			writer.close();
			scribe.close();
		
		} catch (IOException e) {
			
			e.printStackTrace();						 // To find out where I went wrong.
			
		}

	}
	
>>>>>>> branch 'master' of https://github.com/Scarivace/CSE360-Project.git
}
