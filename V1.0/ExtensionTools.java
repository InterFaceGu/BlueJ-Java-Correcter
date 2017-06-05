package kelvey;

import bluej.extensions.BClass;
import bluej.extensions.editor.Editor;
import bluej.extensions.editor.TextLocation;

public class ExtensionTools {

	public static int isFirstLetterUpperCase(String word){
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

	public static void addComment(BClass curClass, String comment){
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
	
	public static String getStringArgs(String word,int args){
		word = word.trim();
		return word.split("\\s+")[args];
	}
	
}
