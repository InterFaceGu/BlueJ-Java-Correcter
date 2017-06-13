package kelvey;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import bluej.extensions.BClass;
import bluej.extensions.BField;
import bluej.extensions.BMethod;
import bluej.extensions.BlueJ;
import bluej.extensions.ClassNotFoundException;
import bluej.extensions.MenuGenerator;
import bluej.extensions.ProjectNotOpenException;
import bluej.extensions.editor.*;

class MenuBuilder extends MenuGenerator {
 
	public BlueJ bJ;
	public static String ShowingMessage = "Thanks for using the Java Parser Extension.\n ";

	private Editor classEditor = null;
	private Set<Phrase> PhraseList = null;
	
	private ExtensionTools eTools;
	@Override
	public JMenuItem getClassMenuItem(BClass bc) {
		return new JMenuItem(
				
				new AbstractAction() {
			
				private static final long serialVersionUID = 5839447996442724262L;
				{putValue(AbstractAction.NAME, "Parser Check");}
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					eTools = new ExtensionTools().instance;
					
					try {
						simplifyFiles(bc); 
						
						checkFieldName(bc);
						
						checkMethodName(bc);
						
						checkClassName(bc);
							
						
					} catch (Exception e2) {
						addingTheShowingMessage("An Exception happend. Please make sure your file is Complieable.\n");
						addingTheShowingMessage(e2.toString());
					}
							
					addingTheShowingMessage("========================\nChecked by ParserExtension.");
					JOptionPane.showMessageDialog(null, addingTheShowingMessage("Completed!"));
					ShowingMessage = "Thanks for using the Java Parser Extension.\n ";
					return;
				}
				
		});
	}
	
	private boolean simplifyFiles(BClass curclass) throws Exception{
		
		addingTheShowingMessage("==>Simplify Code<==");
		
			classEditor = curclass.getEditor();
			if(classEditor == null){ return false; }
			
			Set<Phrase> codeSet = new HashSet<Phrase>(); 
			for (int i = 0; i < classEditor.getLineCount(); i++) {
				TextLocation tLocation = new TextLocation(i,classEditor.getLineLength(i)-1);
				String line = classEditor.getText(new TextLocation(i,0),tLocation);
				codeSet.addAll(eTools.turnCodeIntoParts(line, i));
			}
			addingTheShowingMessage("finished");
			PhraseList = codeSet;
		
		return true;
	}
	
	private void checkClassName(BClass curClass) throws Exception{
		addingTheShowingMessage("==>Check Class Name<==");
		
		String className = curClass.getName();
		if(eTools.isFirstLetterUpperCase(className) == 1) { 
			addingTheShowingMessage("Your Class Name is correct.");
			return;
			
		} else {
			classEditor.setReadOnly(true);
			for (int i = 0; i < classEditor.getLineCount(); i++) {
				TextLocation tLocation = new TextLocation(i,classEditor.getLineLength(i)-1);
				String line = classEditor.getText(new TextLocation(i,0),tLocation);
				if(line.contains("class")&&line.contains(className)&&(!line.contains("*"))){
					
					classEditor.setSelection(new TextLocation(i,0), tLocation);

					addingTheShowingMessage("HighLightCompleted. The Bad Class Name Has Been Selected: "+className+" At Line "+i);

					classEditor.setReadOnly(false);
					return;
				}
				
			}
			addingTheShowingMessage("We can't find your class Name! Please check for any complie error.");
			classEditor.setReadOnly(false);
		}
		
	}
	
	public static String addingTheShowingMessage(String addingMessage){
		//Adding a line to the message before
		//Saveing and return the String that contains all the message before.
		
		ShowingMessage +=addingMessage+"\n";
		return ShowingMessage;
	}
	
	private void checkMethodName(BClass curClass) throws Exception {
		addingTheShowingMessage("==>Check Method Name<==");
		BMethod[] MethodList = curClass.getMethods();
		int find = 0;
		
			for (BMethod bMethod : MethodList) {
				String name = bMethod.getName();
				
				if(eTools.isFirstLetterUpperCase(name)==1){
					for (Phrase b : PhraseList) {
						if(eTools.removeNotLetter(b.string).equals(name)) {
							classEditor.setSelection(new TextLocation(b.lineNumber,0),new TextLocation(b.lineNumber, b.lineCodeLength));
							addingTheShowingMessage("Bad Method Name Selected: "+name+" At Line "+b.lineNumber);
						}
					}
					find++;
				}
			}

		if(find==0){
			addingTheShowingMessage("Your Field Name is correct.");
		}
		
	}

	private void checkFieldName(BClass curClass) throws Exception {
		addingTheShowingMessage("==>Check Field Name<==");
		BField[] FieldList = curClass.getFields();
		int find = 0;
		
		for (BField bField : FieldList) {
			String name = bField.getName();
			
			if(eTools.isFirstLetterUpperCase(name)==1){
				for (Phrase b : PhraseList) {
					if(eTools.removeNotLetter(b.string).equals(name)) {
						classEditor.setSelection(new TextLocation(b.lineNumber,0),new TextLocation(b.lineNumber, b.lineCodeLength));
						addingTheShowingMessage("Bad Field Name Selected: "+name+" At Line "+b.lineNumber);
					}
				}
				find++;
			}
		}
		
		if(find==0){
			addingTheShowingMessage("Your Field Name is correct.");
		}
		
	}
}