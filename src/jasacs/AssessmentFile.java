/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasacs;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author 1412625
 */
public class AssessmentFile {
    
    public AssessmentFile(){
        
    }

        public ClassLocationObject getNewAssessmentFile() {
        final JFileChooser fc = new JFileChooser();
        ClassLocationObject clo = null;
        FileFilter filter = new FileNameExtensionFilter("Class File", "class");
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(filter);
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            clo = new ClassLocationObject();
            clo.setClassLocation(file.getName());
            clo.setFullClassLocation(file.getAbsolutePath());
            clo.setFullprojectLocation(getProjectDirectory());
            
        } else {
            System.out.println("Open command cancelled by user.\n");
        }
        return clo;
    }
  private String getProjectDirectory() {
        try {
            String workingDir = System.getProperty("user.dir");
            return workingDir;
        } catch (Exception e) {
            //ignore the exception
            return "Error obtaining the project folder";
        }
    }
}