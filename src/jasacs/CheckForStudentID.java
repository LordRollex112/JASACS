/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasacs;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import javax.swing.JOptionPane;
import org.openide.util.Exceptions;


/**
 *
 * @author 1412625
 */
public class CheckForStudentID {
    ClassLocationObject clo;
    public CheckForStudentID(ClassLocationObject o){
        clo = o;
    }
    public CheckForStudentID(){
      
    }
    public String getStudentID(){
        String s_id = "";
        try {
            String preparePath =  System.getProperty("user.dir")+ "\\JASACS\\temp";
            GetFullClassPath gfp = new GetFullClassPath(new File(clo.getFullClassLocation()));
            File workdir = new File(preparePath, gfp.getPackagePath());
            if(!workdir.exists()){
                workdir.mkdirs();
                System.out.println("temp directory created");
            }
            File f = new File(workdir, clo.getClassName()+".class");
            Path source = Paths.get(clo.getFullClassLocation());
            Path target = Paths.get(f.getAbsolutePath());
            Files.copy(source, target, REPLACE_EXISTING);
            System.out.println("User directory: "+preparePath);
            s_id = studentID(preparePath, gfp.getPackagePath(), clo.getClassName());
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    
        return s_id;
    }
    private String studentID(String projectPath, String pkg, String cs){
        String ID = "";
        String pnc = "";
        if(pkg.length() > 0){
        pnc = pkg.replaceAll("\\\\", "\\.");    
        pnc += ".";
        }
        pnc = pnc + cs;
        System.out.println("User : "+projectPath);
        System.out.println("Package: "+pkg);
        System.out.println("Package n Class : "+pnc);
        try {
    // Convert File to a URL
    File f = new File(projectPath, "\\");
    
            System.out.println("pakage looking:"+pkg);

    URL url = f.toURI().toURL();           // file:/c:/myclasses/
    URL[] urls = new URL[]{url};

    // Create a new class loader with the directory
    ClassLoader cl = new URLClassLoader(urls);
    
    
    // Load in the class; MyClass.class should be located in
    // the directory file:/c:/myclasses/com/mycompany
    Class<?> cls = cl.loadClass(pnc);  
    Field id = cls.getDeclaredField("studentID");  
    Object val = id.get(null);
    System.out.println("Student ID: "+val.toString());
    ID = val.toString();
} catch (MalformedURLException e) {
    e.printStackTrace();
} catch (ClassNotFoundException e) {
    e.printStackTrace();
} catch (NoSuchFieldException ex) {
    JOptionPane.showMessageDialog(null, "No \"Student ID\" field declared in this assessment");
            
           return ID;
          
} catch (SecurityException ex) {
          Exceptions.printStackTrace(ex);
}  catch (IllegalAccessException ex) {
            Exceptions.printStackTrace(ex);
        }
        return ID;
    }
  
    
    
    
    
  public String studentID(String cs, String assess){
        String ID = "";
String preparePath =  System.getProperty("user.dir")+ "\\JASACS\\temp\\"+assess+"\\";

        try {
    // Convert File to a URL
      File f = new File(preparePath);

    URL url = f.toURI().toURL();           // file:/c:/myclasses/
    URL[] urls = new URL[]{url};

    // Create a new class loader with the directory
    ClassLoader cl = new URLClassLoader(urls);
       String cn = cs.replace(preparePath, "");
            System.out.println("cn: "+cn);
    cn = cn.substring(0, cn.lastIndexOf(".class"));
        String[] arr = cn.split("\\\\");
        String cName = "";
        if(arr.length > 1){
            for(int i = 0; i<arr.length -1 ; i++){
                cName =arr[i]+".";
            }
        }
        cName += arr[arr.length - 1];
    
    System.out.println("cn: "+cName);
    



    // Create a new class loader with the directory
   // ClassLoader cl = new URLClassLoader(urls);
    
    
    // Load in the class; MyClass.class should be located in
    // the directory file:/c:/myclasses/com/mycompany
    Class<?> cls = cl.loadClass(cName);  
    Field id = cls.getDeclaredField("studentID");  
    Object val = id.get(null);
    System.out.println("Student ID: "+val.toString());
    ID = val.toString();
} catch (ClassNotFoundException e) {
    e.printStackTrace();
} catch (NoSuchFieldException ex) {
    JOptionPane.showMessageDialog(null, "No \"Student ID\" field declared in this assessment");
            
           return ID;
          
} catch (SecurityException ex) {
          Exceptions.printStackTrace(ex);
}  catch (IllegalAccessException ex) {
            Exceptions.printStackTrace(ex);
        } catch (MalformedURLException ex) {
            Exceptions.printStackTrace(ex);
        }
        return ID;
    }
}
