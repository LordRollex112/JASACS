/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasacs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import javax.swing.JOptionPane;

/**
 *
 * @author 1412625
 */
public class CreateDependencies {
    ClassLocationObject clo = null;
    String cA;
    public CreateDependencies(ClassLocationObject c, String as){
        clo = c;
        cA = as;
    }

    public boolean createDependency(){
        boolean flag = false;
            if (clo != null) {           
            try {
                String preparePath = System.getProperty("user.dir") + "\\JASACS\\" + cA + "\\TestModel";
                String check_for_space = clo.getFullClassLocation();
                 if (check_for_space.contains(" ")){
                JOptionPane.showMessageDialog(null, "Please make sure no folder has space character i.e \" \".\n You"
                        + " can delete the space or use underscore (_ or anything)"
                        + " inplace of the space.\n"+check_for_space, "Space in one of the "
                                + "directory's name", JOptionPane.ERROR_MESSAGE);           
                                flag = false;
                    } else{
                GetFullClassPath gfp = new GetFullClassPath(new File(clo.getFullClassLocation()));
                File workdir = new File(preparePath, gfp.getPackagePath());
                if (!workdir.exists()) {
                    workdir.mkdirs();
                    System.out.println("directory created");
                } 
                     System.out.println("class name:>>> "+clo.getClassName());
                File f = new File(workdir, clo.getClassName() + ".class");
                //  fos = new FileOutputStream(f);
                
              
                Path source = Paths.get(clo.getFullClassLocation());
                Path target = Paths.get(f.getAbsolutePath());
                Files.copy(source, target, REPLACE_EXISTING);
                flag = true;
                }
            } catch (FileNotFoundException ex) {
                flag = false;
            } catch (IOException ex) {
                 flag = false;
            }           
        }else{
            System.out.println("class name:>>> "+clo.getClassName());
            }
            return flag;
    }
    
    
    
    
    
    public Object[] createStudDependency(boolean flagger){
        boolean flag = false;
        Object[] ret = new Object[2];
        ret[1] = null;
            if (clo != null) {           
            try {
                String preparePath = System.getProperty("user.dir") + "\\JASACS\\temp\\"+cA;
                String check_for_space = clo.getFullClassLocation();
                 if (check_for_space.contains(" ")){
                JOptionPane.showMessageDialog(null, "Please make sure no folder has space character i.e \" \".\n You"
                        + " can delete the space or use underscore (_ or anything)"
                        + " inplace of the space.\n"+check_for_space, "Space in one of the "
                                + "directory's name", JOptionPane.ERROR_MESSAGE);           
                                flag = false;
                    } else{
                GetFullClassPath gfp = new GetFullClassPath(new File(clo.getFullClassLocation()));
                File workdir = new File(preparePath, gfp.getPackagePath());
                if (!workdir.exists()) {
                    workdir.mkdirs();
                    System.out.println("directory created");
                } 
                System.out.println("class name:>>> "+clo.getClassName());
                File f = new File(workdir, clo.getClassName() + ".class");
                if(flagger){
                    ret[1] = f.getAbsolutePath();
                }
                //  fos = new FileOutputStream(f);
                
              
                Path source = Paths.get(clo.getFullClassLocation());
                Path target = Paths.get(f.getAbsolutePath());
                Files.copy(source, target, REPLACE_EXISTING);
                flag = true;
                }
            } catch (FileNotFoundException ex) {
                flag = false;
            } catch (IOException ex) {
                 flag = false;
            }           
        }else{
            System.out.println("class name:>>> "+clo.getClassName());
            }
            ret[0] = flag;
            
            return ret;
    }
}
