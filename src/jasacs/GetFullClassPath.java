/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasacs;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;
import org.openide.util.Exceptions;

/**
 *
 * @author 1412625
 */
public class GetFullClassPath {
    private String packagePath = "";
    private String className = "";
    
    public GetFullClassPath(File f){

    processFile(f);
    
    
    }
    private void processFile(File f){
        
  
        try {
            Runtime runtime = Runtime.getRuntime();
            String str = "";
            Process process = runtime.exec("javap -package "+f.getAbsolutePath());
            InputStream error = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(error);
            BufferedReader br = new BufferedReader(isr);
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                //   System.out.println("line: "+line); // to make sure that it prints the second line which has the class declareation
             //   if(i == 1){
                    str += line;
                              System.out.println(" >> "+line);
               //     break;
             //   }
                i++;
            }
            
            str = str.substring(0, str.length() - 1);
            if(str.contains("class ")){
                //     System.out.println("This is a Class: ");
                str = str.substring(str.indexOf("class ") + 6);
                str = str.substring(0,str.indexOf(" "));
                
                //    System.out.println("Stripped Class: "+str);
                
            }else if(str.contains("interface ")){
                //     System.out.println("This is a Interface: ");
                str = str.substring(str.indexOf("interface ") + 10);
                str = str.substring(0,str.indexOf(" "));
                //
                //        System.out.println("Stripped Interface: "+str);
            }else if(str.contains("enum ")){
                //        System.out.println("This is an Enum: ");
                str = str.substring(str.indexOf("enum ") + 5);
                str = str.substring(0,str.indexOf(" "));
                
                //        System.out.println("Stripped Enum: "+str);
            }
            //           System.out.println("-------------------------------------------------------------------------");
            //          System.out.println();
            //         System.out.println("-------------------------------------------------------------------------");
            
            String[] burst = str.split("\\.");
            for(String s : burst){
                System.out.println("bursted string: " + s);
            }
            className = burst[burst.length-1];
            if(burst.length == 2){
                packagePath = burst[0];
            }
            if(burst.length > 2){
                packagePath = burst[0];
                for(int j = 1; j < burst.length -2 ; i++){
                    packagePath +=  "."+burst[j]; 
                }
                    
            }
            
            //      OutputStream os = new FileOutputStream(new File("a.txt"));
            //      byte[] data = new byte[100];
            
//        while ((i = error.read(data)) > 0) {
//            os.write(data);
//        }
//System.out.println("_+++++++++++++++++++++++++++++++++");
//System.out.println(str);

            error.close();
            isr.close();
            br.close();
//       os.close();
        } catch (IOException ex) {
            System.out.println("ex: "+ex.getMessage());
            Exceptions.printStackTrace(ex);
        }
    }

    public String getPackagePath() {
        return packagePath;
    }

    public String getClassName() {
        return className;
    }
}