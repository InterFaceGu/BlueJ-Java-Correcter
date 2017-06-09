package kelvey;

import java.util.HashSet;
import java.util.Set;

import javax.naming.spi.DirStateFactory.Result;

import bluej.extensions.BClass;
import bluej.extensions.editor.Editor;
import bluej.extensions.editor.TextLocation;

public class ExtensionTools {
	
	ExtensionTools instance;
	
	public ExtensionTools() {
		instance = this;
	}
	public int isFirstLetterUpperCase(String word){
		/**
		 * -1 for unknowword
		 * 0 for false
		 * 1 for true
		 * if the first letter is ' ', it will ignore it.
		 * :) by gu17.
		 */
		word = word.trim();
		
		if(word == null||word.length()<=0) return -1;
		
		if(word.substring(0, 1).toCharArray()[0]>='A'&& word.substring(0, 1).toCharArray()[0]<='Z'){
			return 1;
		} else {
			return 0;
		}
	}

	public void addComment(BClass curClass, String comment){
		Editor classEditor = null;
		try {
			classEditor = curClass.getEditor();
		} catch (Exception e) {}
		
		if(classEditor == null){
			System.out.println("Can't find Editor,Because:"+curClass);
			return;
		}
		classEditor.setReadOnly(true);
		int lineLength = classEditor.getTextLength();
		TextLocation lastLine = classEditor.getTextLocationFromOffset(lineLength);
		lastLine.setColumn(0);
		classEditor.setText(lastLine, lastLine, comment);
		classEditor.setReadOnly(false);
		
	}
	
	public String getStringArgs(String word,int args){
		word = word.trim();
		return word.split("\\s+")[args];
	}	
	
	
	public Set<Block> turnCodeIntoParts(String line,int lineNumber){
		/**-
		 * 
		 */
		Set<Block> result = new HashSet<Block>();
		int preBlockLastSpace = 0;
		int LastSpace = 0;
		int numberOfFindedWord = 0;
		boolean StartRange = false;
		
		//Start to arrage the word.
		for (int i = 0; i < line.length(); i++) {
			if (StartRange) {
				if(line.substring(i, i+1).equals(" ")){
					StartRange = false;
					result.add(new Block(preBlockLastSpace-LastSpace, numberOfFindedWord, line.substring(preBlockLastSpace,i),lineNumber,line.length()));
					numberOfFindedWord++;
					LastSpace = i;
				} else if(i == line.length()-1){
					StartRange = false;
					result.add(new Block(preBlockLastSpace-LastSpace, numberOfFindedWord, line.substring(preBlockLastSpace,i+1),lineNumber,line.length()));
					numberOfFindedWord++;
				} 
			} else {
				if(!line.substring(i, i+1).equals(" "))
					StartRange = true;
					preBlockLastSpace = i;
				}
			}
		return result;
	}
	
	private Set<Integer> findStringInBlocks(String name,Set<Block> st){
		Set<Integer> Result  = new HashSet<Integer>();
		
		for (Block block : st) {
			if(block.string.equals(name));
			Result.add(block.lineNumber);
		}
		return Result;
	}
}
