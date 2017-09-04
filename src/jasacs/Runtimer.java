/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasacs;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 *
 * @author 1412625
 */
public class Runtimer{

    public static void main(String[] args) throws Exception{
        Runtime runtime = Runtime.getRuntime();
        String str = "";
        Process process = runtime.exec("javap -package H:\\NetBeansProjects\\JASACS\\JASACS\\helloThere\\TestModel\\helloThere.class");
        InputStream error = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(error);
        BufferedReader br = new BufferedReader(isr);
        String line;
        int i = 0;
     while ((line = br.readLine()) != null) {
      //   System.out.println("line: "+line);
        if(i == 1){
            str += line;
  //          System.out.println(" >> "+str);
            break;
        }
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
         System.out.println(s);
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
    }
}
