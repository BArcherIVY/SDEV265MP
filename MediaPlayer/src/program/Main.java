package program;

import java.io.File;

import javax.swing.JFileChooser;

public class Main {
	public static void main(String[] args) {
		JFileChooser chooser = new JFileChooser(); //file prompt
		chooser.setDialogTitle("Select a Folder");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = chooser.showOpenDialog(null);
		
		if (result == JFileChooser.APPROVE_OPTION) {
			
            File selectedFile = chooser.getSelectedFile();
            System.out.println("Selected path: " + selectedFile.getAbsolutePath());
            fileControl.Annex.RunAnnex(selectedFile.getAbsolutePath());
        } else {
        	
            System.out.println("No selection made.");
            
        }
        
    }
}
