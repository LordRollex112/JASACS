/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasacs;

import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import org.openide.util.Exceptions;

/**
 *
 * @author 1412625
 */
public class tester {
   
    public static void main(String[] args) throws FileNotFoundException{

//      File file = new File("H:\\Desktop\\");
//        System.out.println(System.getProperty("user.dir"));
//File xc = new File("H:\\Desktop\\wetin.class");
//File temp = new File(System.getProperty("user.dir")+"\\wetin.class");
//    InputStream is = null;
//    OutputStream os = null;
//    try {
//        is = new FileInputStream(xc);
//        os = new FileOutputStream(temp);
//        byte[] buffer = new byte[1024];
//        int length;
//        while ((length = is.read(buffer)) > 0) {
//            os.write(buffer, 0, length);
//        }
//            is.close();
//        os.close();
//    }   catch (FileNotFoundException ex) {
//            Exceptions.printStackTrace(ex);
//        } 

// File filet = new File("H:\\Desktop\\");

//try {
    // Convert File to a URL
//    URL url = filet.toURL();          // file:/c:/myclasses/
//    URL[] urls = new URL[]{url};

    // Create a new class loader with the directory
//    ClassLoader cl = new URLClassLoader(urls);
//
//    // Load in the class; MyClass.class should be located in
//    // the directory file:/c:/myclasses/com/mycompany
// Class<?> cls = cl.loadClass("Testing");
//    
//    Field id = cls.getDeclaredField("studentID");  
//     Object val = id.get(null);
//    System.out.println("I just got the class name: "+val.toString());
//} catch (MalformedURLException e) {
//    e.printStackTrace();
//} catch (ClassNotFoundException e) {
//    e.printStackTrace();
//}       catch (NoSuchFieldException ex) {
//            Exceptions.printStackTrace(ex);
//        } catch (SecurityException ex) {
//            Exceptions.printStackTrace(ex);
//        } catch (IllegalAccessException ex) {
//            Exceptions.printStackTrace(ex);
//        }
//       } 
//File f =  new File("H:\\NetBeansProjects\\JASACS\\jasacs\\wetin");
  //  del(f);
     //   deletetempFolder(f);
        System.out.println(packTraverser("wetin"));
        String[] arr = packTraverser("wetin").split("\\\\");
        String cName = "";
        if(arr.length > 3){
            for(int i = 2; i<arr.length - 1; i++){
                cName =arr[i]+".";
            }
        }
        cName += arr[arr.length - 1];
        System.out.println("c Name: "+cName);
        
    }
       public static void  deletetempFolder(File f){
          if(f.isDirectory()){
          for(File fx : f.listFiles()){
              deletetempFolder(fx);          
          }             
          }

          f.delete();
            // Path p = Paths.get(dir);

            
        }
      public static boolean deleteRecursive(File path) throws FileNotFoundException{
        if (!path.exists()) throw new FileNotFoundException(path.getAbsolutePath());
        boolean ret = true;
        if (path.isDirectory()){
            for (File f : path.listFiles()){
                ret = ret && deleteRecursive(f);
            }
        }
        return ret && path.delete();
    }
      
      public void sit(int x, String y){

      }
     
      private String sit(int x){
          return "";
      }
      
      
    private static String packTraverser(String n){
    String travel = "";
    String preparePath =  System.getProperty("user.dir")+ "\\JASACS\\"+n;
    File f = new File(preparePath);
            //  System.out.println("is directory"+f.isFile());
    if(f.isDirectory()){
       for (File fx : f.listFiles()) {
           
            String s = n+"\\"+fx.getName();
       //     System.out.println("s --> "+s);
          travel +=  packTraverser(s);
            }
       
    
    }else{
    travel = n;
    }             
    return travel;
    }
}
