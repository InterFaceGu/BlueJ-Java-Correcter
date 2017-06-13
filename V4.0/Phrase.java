package kelvey;

public class Phrase {
	/**
	 * spaceb4-#of space before the word
	 * place-the place in that line
	 * string-the string it self
	 * :)
	 */
	public int spaceBefore;
	public int place;
	//#of word in the line
	public String string;
	//String with out space 
	public int lineNumber;
	public int lineCodeLength;
	//length
	public int spacePlace;
	//这个词的准确
	
	public Phrase(int space,int place, String string,int lineNumber,int lineCodeLength,int preSpace) {
		spaceBefore = space;
		this.place = place;
		this.lineNumber = lineNumber;
		this.string = string;
		this.lineCodeLength = lineCodeLength;
		spacePlace = preSpace;
	}
}
