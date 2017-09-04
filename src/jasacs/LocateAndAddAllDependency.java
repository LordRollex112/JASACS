/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasacs;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.openide.util.Exceptions;

/**
 *
 * @author 1412625
 */
public final class LocateAndAddAllDependency {
    String cpath = "";
    String projPath;
    String cname;
    String pack;
    String prepare = System.getProperty("user.dir") + "\\JASACS\\testtemp";
    File parent;
    String lostFile;
    List<String> listOfDependency = new ArrayList();
    

    public LocateAndAddAllDependency(String s){   
    if(s.contains(" ")){
        JOptionPane.showMessageDialog(null, s+" has space, please changremove the space in the directory and start again");
        return;
    }
        cpath = s;
        System.out.println("sx: "+s);  //--------------------------------------------------------------------------------------------------
  
    System.out.println("cpath = "+cpath);   
    File nf = new File(s);
    parent = new File(nf.getParent());
    GetFullClassPath gp = new GetFullClassPath(nf);
    pack = gp.getPackagePath();
    cname = gp.getClassName();
    
     if(startJob()){
         testForDependencyJob();
         
     }else{
     
     
     }
    }
    public boolean startJob(){
    
        try {
            System.out.println("-------**********");
            System.out.println(pack);
            System.out.println(cname);
            System.out.println("-------**********");
            
            File dir = new File(prepare, pack);
            if (!dir.exists()) {
                dir.mkdirs();
                System.out.println("directory created");
            }
            File f = new File(dir, cname+ ".class");
            if (f.exists()) {
                
                System.out.println("file exists");
            }
            
            Path source = Paths.get(cpath);
            Path target = Paths.get(f.getAbsolutePath());
             Files.copy(source, target, REPLACE_EXISTING);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
    
    public void testForDependencyJob(){   
        try {
            File f = new File(prepare);           
            URL url = f.toURI().toURL();           // file:/c:/myclasses/
            URL[] urls = new URL[]{url};
            ClassLoader cl = new URLClassLoader(urls);
                    String[] arr = pack.split("\\\\");
        String cName = "";
            if(!arr[0].equalsIgnoreCase(""))
            for(int i = 0; i<arr.length ; i++){
                cName =arr[i]+".";
            }
        cName += cname;
    
        System.out.println("cn: "+cName);

        Class<?> cls = cl.loadClass(cName);  
        Constructor[] hc = cls.getDeclaredConstructors();
        Field[] hf = cls.getDeclaredFields();
        Method[] hm = cls.getDeclaredMethods();
        System.out.println("Loading test model object:"+cls.getSimpleName());   
        cls = null;
        } catch (MalformedURLException | ClassNotFoundException | NoClassDefFoundError ex) {           
            System.out.println("exepion: "+ex.getMessage());
            String[] x = ex.getMessage().split("/");
            System.out.println("lost file: "+lostFile);
            lostFile = x[x.length-1]+".class";
            String ff = locateThisFile(parent.getAbsolutePath());
            System.out.println(ff);
            if(ff == null){
             x = ex.getMessage().split("/");
                lostFile = x[x.length-1]+".class";
                String vb = new String(lostFile.replace(";", ""));
              System.out.println(lostFile);
              lostFile = vb;
              System.out.println(lostFile);
            }
             ff = locateThisFile(parent.getAbsolutePath());
            String kk = ff.substring(parent.getAbsolutePath().length() + 1);
            System.out.println("kk: "+kk);
            listOfDependency.add(kk);
            System.out.println("size: "+listOfDependency.size());
            LocateAndAddAllDependency l = new LocateAndAddAllDependency(ff);
            for(String xc : l.listOfDependency){
            this.listOfDependency.add(xc);
            }
            LocateAndAddAllDependency b = new LocateAndAddAllDependency(cpath);
        }  
    }
    private String locateThisFile(String sub){
        String ff = null;
        // SEARCH THE PARENT FOLDER FOR THE FILE 
        System.out.println("sub to list: "+sub);
        File fp = new File(sub);
        File[] f = fp.listFiles();
        System.out.println("-----------------------------");
        
        for(File c : f){
            System.out.println("*** :"+c.getAbsolutePath());
            if(c.isDirectory()){
                System.out.println("*** :directrory");
                ff =  locateThisFile(c.getAbsolutePath());
               if(ff != null){
                   break;
               }
            }else{
                System.out.println("*** :file: "+c.getName()+" against "+lostFile);
                
                if(c.getName().equals(lostFile)){
                    System.out.println("inside inner: lost file search:"+lostFile);
                    System.out.println("*****************************************************************************************************File found here");
                    return c.getAbsolutePath();
                }     
            }
        }
        return ff;   
    }
}
