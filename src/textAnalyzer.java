import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

public class textAnalyzer extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	/* Constants for width of window and row height */
	final static int WIDTH = 3000;
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
	
	final String historyHeader = String.format("%-15s %-15s %-5s %-5s %-5s %-5s %-5s %-5s %-15s %-15s %-5s ", "File", "Date", "L", "B", "W", "Ch", "Sp", "Avg", "Long", "Freq", "#");
	
	/* Menu Bar */
	JMenuBar menuBar = new JMenuBar();
	JMenu menu = new JMenu("Menu");
	JMenuItem helpOption = new JMenuItem("Help");
	JMenuItem resetOption = new JMenuItem("RESET");
	
	/* Tabs for History and Current File */
	JTabbedPane tabs = new JTabbedPane();
	
	/* Labels for GUI */
	JLabel numLinesLabel = new JLabel(" ");
	JLabel numBlankLinesLabel = new JLabel(" ");
	JLabel numWordsLabel = new JLabel(" ");
	JLabel numCharactersLabel = new JLabel(" ");
	JLabel numSpacesLabel = new JLabel(" ");
	JLabel avgWordLengthLabel = new JLabel(" ");
	JLabel longestWordLabel = new JLabel(" ");
	JLabel mostFrequentLabel = new JLabel(" ");
	JLabel frequentWordCountLabel = new JLabel(" ");
	
	JLabel requestFileHeading = new JLabel("Please enter file name or Browse: ");
	JLabel numLinesHeading = new JLabel("Number of Lines: ");
	JLabel numBlankLinesHeading = new JLabel("Number of Blank Lines: ");
	JLabel numWordsHeading = new JLabel("Number of Words: ");
	JLabel numCharactersHeading = new JLabel("Number of Characters: ");
	JLabel numSpacesHeading = new JLabel("Number of Spaces: ");
	JLabel avgWordLengthHeading = new JLabel("Average Word Length: ");
	JLabel longestWordHeading = new JLabel("Longest Word: ");
	JLabel mostFrequentWordHeading = new JLabel("Most Frequent Word in File: ");
	JLabel frequentWordCountHeading = new JLabel("Count of Most Frequent Word: ");
	
	JLabel averagesHeading = new JLabel("Averages:");
	JLabel avgLinesHeading = new JLabel("Lines: ");
	JLabel avgLinesLabel = new JLabel();
	JLabel avgBlanksHeading = new JLabel("Blanks:  ");
	JLabel avgBlanksLabel = new JLabel();
	JLabel avgWordsHeading = new JLabel("Words: ");
	JLabel avgWordsLabel = new JLabel();
	JLabel avgCharactersHeading = new JLabel("Char: ");
	JLabel avgCharactersLabel = new JLabel();
	JLabel avgSpacesHeading = new JLabel("Spaces: ");
	JLabel avgSpacesLabel = new JLabel();

	/* Panels and Frame */
    JFrame frame = new JFrame("Text Analyzer");
    JPanel currentPanel = new JPanel();
    JPanel analysisPanel = new JPanel();
    JPanel historyPanel = new JPanel();
    JTextArea displayHistory = new JTextArea("",10,50);
    JScrollPane scrollHistory = new JScrollPane();
	
	/* File Chooser */
	final JFileChooser fc = new JFileChooser();
	
	/* Buttons */
	JButton analyzeButton = new JButton("Analyze");
	JButton browseButton = new JButton("Browse...");
	
	/* Text Box to receive text file name */
	JTextField fileNameField = new JTextField("",20);
	
	/* currentFile is the textfile object for current file */
	TextFile currentFile;
	
	LinkedList<TextFile> fileList = new LinkedList<TextFile>();
	
	double averageLines, averageBlanks, averageWords, averageCharacters, averageSpaces;
	
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
    	
        /* set up the main frame */
        frame.setSize(new Dimension(WIDTH,height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        
        /* Panels for sections of analyzer */
        currentPanel.setLayout(new GridBagLayout());
        currentPanel.setBorder(new EmptyBorder(0,20,20,20));
        
        analysisPanel.setLayout(new GridBagLayout());
        analysisPanel.setBackground(Color.white);
        analysisPanel.setBorder(loweredetched);
        
        historyPanel.setLayout(new GridBagLayout());
        
        displayHistory.setBorder(loweredetched);
        
    	/* Alight Text Labels for GUI */
    	requestFileHeading.setHorizontalAlignment(JLabel.RIGHT);
    	numLinesHeading.setHorizontalAlignment(JLabel.RIGHT);
    	numBlankLinesHeading.setHorizontalAlignment(JLabel.RIGHT);
    	numWordsHeading.setHorizontalAlignment(JLabel.RIGHT);
    	numCharactersHeading.setHorizontalAlignment(JLabel.RIGHT);
    	numSpacesHeading.setHorizontalAlignment(JLabel.RIGHT);
    	avgWordLengthHeading.setHorizontalAlignment(JLabel.RIGHT);
    	longestWordHeading.setHorizontalAlignment(JLabel.RIGHT);
    	mostFrequentWordHeading.setHorizontalAlignment(JLabel.RIGHT);
    	frequentWordCountHeading.setHorizontalAlignment(JLabel.RIGHT);
    	
    	averagesHeading.setHorizontalAlignment(JLabel.CENTER);
    	avgLinesHeading.setHorizontalAlignment(JLabel.RIGHT);
    	avgBlanksHeading.setHorizontalAlignment(JLabel.RIGHT);
    	avgWordsHeading.setHorizontalAlignment(JLabel.RIGHT);
    	avgCharactersHeading.setHorizontalAlignment(JLabel.RIGHT);
    	avgSpacesHeading.setHorizontalAlignment(JLabel.RIGHT);
    	
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
        currentPanel.add(requestFileHeading, c);
        
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(10,10,10,10);
        currentPanel.add(fileNameField, c);
        
        c.gridx = 2;
        c.gridy = 0;
        currentPanel.add(browseButton, c);
        
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        currentPanel.add(analyzeButton, c);
        
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.insets = new Insets(0,0,0,0);
        analysisPanel.add(numLinesHeading, c);
        
        c.gridx = 1;
        c.gridy = 0;
        analysisPanel.add(numLinesLabel, c);
        
        c.gridx = 0;
        c.gridy = 1;
        analysisPanel.add(numBlankLinesHeading, c);
        
        c.gridx = 1;
        c.gridy = 1;
        analysisPanel.add(numBlankLinesLabel, c);
        
        c.gridx = 0;
        c.gridy = 2;
        analysisPanel.add(numWordsHeading, c);
        
        c.gridx = 1;
        c.gridy = 2;
        analysisPanel.add(numWordsLabel, c);
        
        c.gridx = 0;
        c.gridy = 3;
        analysisPanel.add(numCharactersHeading, c);
        
        c.gridx = 1;
        c.gridy = 3;
        analysisPanel.add(numCharactersLabel, c);
        
        c.gridx = 0;
        c.gridy = 4;
        analysisPanel.add(numSpacesHeading, c);
        
        c.gridx = 1;
        c.gridy = 4;
        analysisPanel.add(numSpacesLabel, c);
        
        c.gridx = 0;
        c.gridy = 5;
        analysisPanel.add(avgWordLengthHeading, c);
        
        c.gridx = 1;
        c.gridy = 5;
        analysisPanel.add(avgWordLengthLabel, c);
        
        c.gridx = 0;
        c.gridy = 6;
        analysisPanel.add(longestWordHeading, c);
        
        c.gridx = 1;
        c.gridy = 6;
        analysisPanel.add(longestWordLabel, c);
        
        c.gridx = 0;
        c.gridy = 7;
        analysisPanel.add(mostFrequentWordHeading, c);
        
        c.gridx = 1;
        c.gridy = 7;
        analysisPanel.add(mostFrequentLabel, c);
        
        c.gridx = 0;
        c.gridy = 8;
        analysisPanel.add(frequentWordCountHeading, c);
        
        c.gridx = 1;
        c.gridy = 8;
        analysisPanel.add(frequentWordCountLabel, c);
        
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        currentPanel.add(analysisPanel,c);
        
        displayHistory.setFont(new Font("courier", Font.PLAIN, 12));
        displayHistory.append(historyHeader + "\n");
        readHistory();
        for(int i = 0; i < fileList.size(); i ++)
        {
        	displayHistory.append(fileList.get(i).toString() + "\n");
        }
        
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 10;
        historyPanel.add(scrollHistory.add(displayHistory),c);
        
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 10;
        historyPanel.add(averagesHeading, c);
        
        calcAverages();
        
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        historyPanel.add(avgLinesHeading, c);

        c.gridx = 1;
        c.gridy = 2;
        historyPanel.add(avgLinesLabel, c);
        
        c.gridx = 2;
        c.gridy = 2;
        historyPanel.add(avgBlanksHeading, c);

        c.gridx = 3;
        c.gridy = 2;
        historyPanel.add(avgBlanksLabel, c);

        c.gridx = 4;
        c.gridy = 2;
        historyPanel.add(avgWordsHeading, c);

        c.gridx = 5;
        c.gridy = 2;
        historyPanel.add(avgWordsLabel, c);
        
        c.gridx = 6;
        c.gridy = 2;
        historyPanel.add(avgCharactersHeading, c);

        c.gridx = 7;
        c.gridy = 2;
        historyPanel.add(avgCharactersLabel, c);
        
        c.gridx = 8;
        c.gridy = 2;
        historyPanel.add(avgSpacesHeading, c);

        c.gridx = 9;
        c.gridy = 2;
        historyPanel.add(avgSpacesLabel, c);
        
        tabs.addTab("Current File",currentPanel);
        tabs.addTab("Analysis History", historyPanel);
 
        frame.add(tabs);
        
        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }	

	@SuppressWarnings("unused")
	public static void main(String[] args) 
	{
		textAnalyzer newAnalysis = new textAnalyzer();
	}
	
	public void actionPerformed(ActionEvent eventName)
	{
		Object source = eventName.getSource();
		//int lines, blankLines, words, characters, spaces;
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
				String fileName = fileNameField.getText();
				file = new File(fileName);
			}
			
			currentFile = new TextFile(file);
			numLinesLabel.setText("" + currentFile.getLines());
			numBlankLinesLabel.setText("" + currentFile.getBlanks());
			numWordsLabel.setText("" + currentFile.getWords());
			numCharactersLabel.setText("" + currentFile.getCharacters());
			numSpacesLabel.setText("" + currentFile.getSpaces());
			avgWordLengthLabel.setText("" + currentFile.getAvgLength());
			longestWordLabel.setText(currentFile.getLongest());
			mostFrequentLabel.setText(currentFile.getMostFrequent());
			frequentWordCountLabel.setText("" + currentFile.getFrequentCount());
			
			displayHistory.append(currentFile.toString() + "\n");
			calcAverages();
			
			try 
			{						// Method call to toString via Output method call.
				writeOutput(currentFile.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
		if(source == helpOption)
		{
			JOptionPane.showMessageDialog(null,helpInformation);
		}
		
		if(source == resetOption)
		{
			// Resets labels as blank text.
			numLinesLabel.setText("");
			numBlankLinesLabel.setText("");
			numWordsLabel.setText("");
			numCharactersLabel.setText("");
			numSpacesLabel.setText("");
			avgWordLengthLabel.setText("");
			longestWordLabel.setText("");
			mostFrequentLabel.setText("");
			frequentWordCountLabel.setText("");
		}
	}
	
	/**
		Method to create and modify an output file.
	*/
	public void writeOutput(String stringy) throws IOException 
	{
		File output = null;							 // Object Declarations.
		FileWriter scribe = null;
		BufferedWriter writer = null;

		String fileName = "Output.txt";						 // String Declarations.
		String content = stringy;
		
		try {
			output = new File(fileName);
			
			if (!output.exists()) {						 // If the file doesn't exist then create it.
				output.createNewFile();
				scribe = new FileWriter(output.getAbsoluteFile()); 	 // THE SOURCE OF ERRORS.
			}
			else 
			{
				scribe = new FileWriter(output.getAbsoluteFile(), true); // THE SOURCE OF ERRORS.
			}
			
			writer = new BufferedWriter(scribe);
			
			writer.write(content);
			writer.newLine();
			writer.flush();		
		}
		catch (IOException e) {	
			e.printStackTrace();						 // To find out where I went wrong.
		}
		finally {								 // Always close.
			if (writer != null) {
				writer.close();
			}
			if (scribe != null) {
				scribe.close();
			}
		}

	}
	
	/* readHistory will read the output file to create/display the history of analyzed files 
	 * Assumes Output.txt exists and is correctly formatted/was generated by this program */
	public void readHistory()
	{
		String filename, dateAnalyzed, longestWord, frequentWord;
		int lines,blanks,words,characters,spaces,averageWord,freqCount;
		File infile = new File("Output.txt");

		if(infile.exists() && infile.canRead())
		{
			try
			{
				Scanner fileScanner = new Scanner(infile);
				fileList = new LinkedList<TextFile>();
				while(fileScanner.hasNext())
				{
					filename = fileScanner.next();
					dateAnalyzed = fileScanner.next();
					lines = fileScanner.nextInt();
					blanks = fileScanner.nextInt();
					words = fileScanner.nextInt();
					characters = fileScanner.nextInt();
					spaces = fileScanner.nextInt();
					averageWord = fileScanner.nextInt();
					longestWord = fileScanner.next();
					frequentWord = fileScanner.next();
					freqCount = fileScanner.nextInt();
				
					fileList.add(new TextFile(filename, dateAnalyzed, lines, blanks, words, characters,spaces,averageWord,longestWord,frequentWord,freqCount));
				}
				fileScanner.close();
			}
			catch (IOException errorMessage)
			{
				JOptionPane.showMessageDialog(null,errorMessage.getMessage(),"IO Error",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void calcAverages()
	{
		int totalLines=0, totalBlanks=0, totalWords=0, totalCharacters=0, totalSpaces=0, fileCount=0;
		DecimalFormat num = new DecimalFormat("#,###.00");
		
		if(currentFile != null)
		{
			totalLines += currentFile.getLines();
			totalBlanks += currentFile.getBlanks();
			totalWords += currentFile.getWords();
			totalCharacters += currentFile.getCharacters();
			totalSpaces += currentFile.getSpaces();
			fileCount ++;
		}
		for(int i = 0; i < fileList.size(); i ++)
		{
			totalLines += fileList.get(i).getLines();
			totalBlanks += fileList.get(i).getBlanks();
			totalWords += fileList.get(i).getWords();
			totalCharacters += fileList.get(i).getCharacters();
			totalSpaces += fileList.get(i).getSpaces();
			fileCount++;
		}
		if(fileCount != 0)
		{
			averageLines = (double)totalLines / fileCount;
			averageBlanks = (double)totalBlanks / fileCount;
			averageWords = (double)totalWords / fileCount;
			averageCharacters = (double)totalCharacters / fileCount;
			averageSpaces = (double)totalSpaces / fileCount;
		}

		avgLinesLabel.setText("" + num.format(averageLines));
		avgBlanksLabel.setText("" + num.format(averageBlanks));
		avgWordsLabel.setText("" + num.format(averageWords));
		avgCharactersLabel.setText("" + num.format(averageCharacters));
		avgSpacesLabel.setText("" + num.format(averageSpaces));
	}
}
