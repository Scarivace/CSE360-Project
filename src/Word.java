public class Word 
{
	String aWord;
	int wordCount;
	
	public Word()
	{
		aWord = "";
		wordCount = 0;
	}
	
	public Word(String receivedWord)
	{
		aWord = receivedWord;
		wordCount = 1;
	}
	
	public String getWord()
	{
		return aWord;
	}
	
	public int getCount()
	{
		return wordCount;
	}
	
	public void addCount()
	{
		wordCount ++;
	}
}
