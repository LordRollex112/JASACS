/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasacs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.net.URLClassLoader;
import org.openide.util.Exceptions;

/**
 *
 * @author 1412625
 */
public class Deserializer {
       public static void main(String [] args) {
      AssessObject e = null;
      try {
         FileInputStream fileIn = new FileInputStream("H:\\NetBeansProjects\\JASACS\\JASACS\\");
         ObjectInputStream in = new ObjectInputStream(fileIn);
         e = (AssessObject) in.readObject();
         in.close();
         fileIn.close();
   
         
         File file = new File("H:\\NetBeansProjects\\JASACS\\JASACS\\wetin\\");


    // Convert File to a URL
    URL url = file.toURI().toURL();          // file:/c:/myclasses/
    URL[] urls = new URL[]{url};

    // Create a new class loader with the directory
    ClassLoader cl = new URLClassLoader(urls);
    
    // Load in the class; MyClass.class should be located in
    // the directory file:/c:/myclasses/com/mycompany
    Class cls = cl.loadClass("mastapam.wetin");
          System.out.println("Class Name: "+cls.getName());
       
         
         
         
      }catch(IOException i) {
         i.printStackTrace();
         return;
      }    catch (ClassNotFoundException ex) {
               Exceptions.printStackTrace(ex);
           }
      

   }
}
