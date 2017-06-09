package kelvey;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import bluej.extensions.BClass;
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
	private Set<Block> blockList = null;
	
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
						
						checkClassName(bc);
							
						checkMethodName(bc);
						
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
	
	@SuppressWarnings("null")
	private boolean simplifyFiles(BClass curclass) throws Exception{
		
		addingTheShowingMessage("==>Simplify Code<==");
		
		curclass.compile(true);
		
		try {
			classEditor = curclass.getEditor();
		} catch (Exception e) {
			addingTheShowingMessage("Can't find Editor,");
			return false;
			//这家伙的className没问题。
		}
		
		if(classEditor == null){ return false; }
		
		Set<Block> codeSet = new HashSet<Block>(); 
		for (int i = 0; i < classEditor.getLineCount(); i++) {
			TextLocation tLocation = new TextLocation(i,classEditor.getLineLength(i)-1);
			String line = classEditor.getText(new TextLocation(i,0),tLocation);
			codeSet.addAll(eTools.turnCodeIntoParts(line, i));
		}
		addingTheShowingMessage("finished");
		blockList = codeSet;
		
		return true;
	}
	
	private void checkClassName(BClass curClass) throws Exception{
		addingTheShowingMessage("==>Check Class Name<==");
		
		Editor classEditor = null;
		try {
			classEditor = curClass.getEditor();
		} catch (Exception e) {
			addingTheShowingMessage("Can't find Editor,");
			//这家伙的className没问题。
		}
		
		if(classEditor == null){ return ; }
		
		String className = curClass.getName();
		try {
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

						addingTheShowingMessage("【HighLightCompleted. A Bad Class Name Has Been Selected.】");

						classEditor.setReadOnly(false);
						return;
					}
					
				}
				addingTheShowingMessage("We can't find your class Name! Please check for any complie error.");
				classEditor.setReadOnly(false);
			}
		} catch (Exception e) {
			addingTheShowingMessage(e.toString());
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
		
		for (BMethod bMethod : MethodList) {
			String name = bMethod.getName();
			
			if(eTools.isFirstLetterUpperCase(name)==1){
				for (Block b : blockList) {
					addingTheShowingMessage(b.string);
					if(b.string.equals(name)) {
						classEditor.setSelection(new TextLocation(b.lineNumber,0),new TextLocation(b.lineNumber, b.lineCodeLength));
						addingTheShowingMessage("Bad Method Name Selected.");
					}
				}
			}
		}
	}
	
	
}