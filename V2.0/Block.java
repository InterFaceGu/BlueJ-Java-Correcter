package kelvey;

public class Block {
	/**
	 * spaceb4-#of space before the word
	 * place-the place in that line
	 * string-the string it self
	 * :)
	 */
	public int spaceBefore;
	public int place;
	public String string;
	public int lineNumber;
	public int lineCodeLength;
	
	public Block(int space,int place, String string,int lineNumber,int lineCodeLength) {
		spaceBefore = space;
		this.place = place;
		this.lineNumber = lineNumber;
		this.string = string;
		this.lineCodeLength = lineCodeLength;
	}
}
