package kelvey;
import java.awt.event.ActionEvent;
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
	@Override
	public JMenuItem getClassMenuItem(BClass bc) {
		JMenuItem Meau = new JMenuItem(
				
				new AbstractAction() {
			
				private static final long serialVersionUID = 5839447996442724262L;
				{putValue(AbstractAction.NAME, "Parser Check");}
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(simplifyFiles(bc)){
						checkClassName(bc);
						try {
							checkMethodName(bc);
						} catch (ProjectNotOpenException | ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							addingTheShowingMessage("An Exception happend. Please make sure your file is Complieable.");
						}
						
					}else{
						addingTheShowingMessage("An Exception happend. Please make sure your file is Complieable.");
					}
					addingTheShowingMessage("========================\nChecked by ParserExtension.");
					JOptionPane.showMessageDialog(null, addingTheShowingMessage("Completed!"));
					return;
				}
				
		});
		return Meau;
	}
	
	private boolean simplifyFiles(BClass curclass){
		
		addingTheShowingMessage("==>Simplify Code<==");
		
		String TestString = "";
		try {
			classEditor = curclass.getEditor();
		} catch (Exception e) {
			addingTheShowingMessage("Can't find Editor,");
			return false;
			//这家伙的className没问题。
		}
		
		if(classEditor == null){ return false; }
		
		Set<Block> codeSet = null; 
		for (int i = 0; i < classEditor.getLineCount(); i++) {
			TextLocation tLocation = new TextLocation(i,classEditor.getLineLength(i));
			String line = classEditor.getText(new TextLocation(i,0),tLocation);
			
			codeSet.addAll(ExtensionTools.turnCodeIntoParts(line, i));
		}
		
		blockList = codeSet;
		
		return true;
	}
	private void checkClassName(BClass curClass){
		addingTheShowingMessage("==>Check Class Name<==");
		String className = curClass.getName();
		
		if(ExtensionTools.isFirstLetterUpperCase(className) == 1) { 
			addingTheShowingMessage("Your Class Name is correct.");
			return;
			
		} else{
			classEditor.setReadOnly(true);
			for (int i = 0; i < classEditor.getLineCount(); i++) {
				TextLocation tLocation = new TextLocation(i,classEditor.getLineLength(i));
				String line = classEditor.getText(new TextLocation(i,0),tLocation);
				if(line.contains("class")&&line.contains(className)&&(!line.contains("*"))){
					classEditor.setSelection(new TextLocation(i,0), tLocation);

					addingTheShowingMessage("HighLightCompleted.");

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
	
	private void checkMethodName(BClass curClass) throws ProjectNotOpenException, ClassNotFoundException{
		addingTheShowingMessage("==>Check Method Name<==");
		BMethod[] MethodList = curClass.getMethods();
		
		for (BMethod bMethod : MethodList) {
			String name = bMethod.getName();
			
			if(ExtensionTools.isFirstLetterUpperCase(name)!=1){
				for (Block b : blockList) {
					if(b.string.equals(name)){
						classEditor.setSelection(new TextLocation(b.lineNumber,0),new TextLocation(b.lineNumber, b.lineCodeLength));
					}
				}
			}
		}
	}
	
}