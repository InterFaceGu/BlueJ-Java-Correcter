package kelvey;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import bluej.extensions.BClass;
import bluej.extensions.BlueJ;
import bluej.extensions.MenuGenerator;
import bluej.extensions.editor.*;

class MenuBuilder extends MenuGenerator {
 
	public BlueJ bJ;
	
	@Override
	public JMenuItem getClassMenuItem(BClass bc) {
		
		return new JMenuItem(
			
			new AbstractAction() {
		
			private static final long serialVersionUID = 5839447996442724262L;
			{putValue(AbstractAction.NAME, "ClickClassName");}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BClass[] classList;
//				try {
//					 classList = bp.getCurrentClasses();
//				} catch (ProjectNotOpenException | PackageNotFoundException e2) {
//					JOptionPane.showMessageDialog(null, "Can't find the Java file. Please create on and [OPEN] it.");
//					e2.printStackTrace();
//					return;
//				}
//				for (BClass bClass : classList) {
//					
//				}
				checkAndChangeClassName(bc);
				ExtensionTools.addComment(bc,"//Checked by ParserExtension. \n");
				
				JOptionPane.showMessageDialog(null, "Completed!");
				return;
			}
			
		});
	}

	private void checkAndChangeClassName(BClass curClass){
		Editor classEditor = null;
		
		String TestString = "";
		try {
			classEditor = curClass.getEditor();
		} catch (Exception e) {
			System.out.println("Can't find Editor,Because:"+curClass);
			return;
			//这家伙的className没问题。
		}
		
		if(classEditor == null){ return; }
		
		String className = curClass.getName();
		
		if(ExtensionTools.isFirstLetterUpperCase(className) == 1) { 
			ExtensionTools.addComment(curClass, "//Your Class Name is correct.\n");
			return;
			
		} else{
			classEditor.setReadOnly(true);
			for (int i = 0; i < classEditor.getLineCount(); i++) {
				for (int j = 0; j < classEditor.getLineLength(i); j++) {
					TextLocation tLocation = new TextLocation(i,j);
					String line = classEditor.getText(new TextLocation(i,0),tLocation);
					if(line.contains("class")&&line.contains(className)&&(!line.contains("*"))){
						classEditor.setSelection(new TextLocation(i,0), tLocation);

						JOptionPane.showMessageDialog(null, "HighLightCompleted.");

						classEditor.setReadOnly(false);
						return;
					}
				}
			}
			ExtensionTools.addComment(curClass, "//We can't find your class Name! Please check for any complie error.\n");
			classEditor.setReadOnly(false);
		}
	}
	
}