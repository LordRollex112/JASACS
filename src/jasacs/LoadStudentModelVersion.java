/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasacs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openide.util.Exceptions;

/**
 *
 * @author 1412625
 */
public class LoadStudentModelVersion {
    JSONObject testModelJsonObject = null;
    String assName;
    String s_ID;
    public LoadStudentModelVersion(String aName, String sid){
        assName = aName;
        s_ID = sid;
    }
    public JSONObject startOperation(){
    
String preparePath =  System.getProperty("user.dir")+ "\\JASACS\\"+assName+"\\"+s_ID+"\\";
      //  System.out.println("returned: ->"+packTraverser(assName, s_ID));
      
        System.out.println("");
         String cg = packTraverser(assName, s_ID);
         FileInputStream fos = null;
         ObjectInputStream ois  = null;
         AssessObject ao  = null;
        try {
             fos = new FileInputStream(new File(preparePath+"loadpath.jasacs"));
              ois = new ObjectInputStream(fos);
               ao = (AssessObject) ois.readObject();
               ois.close();
               fos.close();
        } catch (FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (ClassNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
         
         String[] arr = ao.getLoadPath().split("\\\\");
      
            System.out.println("====> load path "+ao.getLoadPath());
      
            
        String cName = "";
        
        if(arr.length > 1){
            for(int i = 0; i<arr.length -1 ; i++){
                cName =arr[i]+".";
            }
        }
        
        
        cName += arr[arr.length - 1];
        System.out.println("c name =:" +cName);
        cName = cName.substring(0, cName.lastIndexOf(".class"));
        System.out.println("c Name: "+cName);
 
        try {
    // Convert File to a URL
    File f = new File(preparePath);

    URL url = f.toURI().toURL();           // file:/c:/myclasses/
    URL[] urls = new URL[]{url};

    // Create a new class loader with the directory
    ClassLoader cl = new URLClassLoader(urls);
    

    // Load in the class; MyClass.class should be located in
    // the directory file:/c:/myclasses/com/mycompany
            Class<?> cls = cl.loadClass(cName);  
           System.out.println("Loading test model object:"+cls.getCanonicalName()); 
            testModelJsonObject = new JSONObject();
            testModelJsonObject.put("Assessment", assName);
            testModelJsonObject.put("ID", "Test Model"); // student id goes here
            // json object for class
            // 
            JSONArray cinfoArray = new JSONArray();// json array object for class info
            JSONObject cinfoObject = new JSONObject();// json object for class
            cinfoObject.put("class name", cls.getSimpleName());
            if(cls.getPackage() == null){
            cinfoObject.put("package name", "null");
            }else{
            cinfoObject.put("package name", cls.getPackage().getName());
            }
      
         //   testModelJsonObject.put("superclass", sc.getName());
            //modifiers try subtracting the isModifier 
             
             int modi = cls.getModifiers();
//             if(cls.isInterface()){
//                 modi -= Modifier.INTERFACE;
//             }
             
                 
             if(Modifier.isPublic(modi)){
                 cinfoObject.put("modifier", "public");
             }
             else if(Modifier.isPrivate(modi)){
                 cinfoObject.put("modifier", "private");
             }
             else if(Modifier.isProtected(modi)){
                 cinfoObject.put("modifier", "protected");
             }else {
                 cinfoObject.put("modifier", "");
             }
             if(Modifier.isAbstract(modi)){
                 cinfoObject.put("abstract", "abstract");
             }else{
                 cinfoObject.put("abstract", "");
             }
             if(Modifier.isFinal(modi)){
                 cinfoObject.put("Final", "final");
             }else{
                 cinfoObject.put("Final", "");
             }
            System.out.println("modi "+modi);
            //System.out.println("modi "+);
            
            if(cls.isInterface()){
                cinfoObject.put("type name", "interface");
            }else {
               // if(cls.getGenericSuperclass().toString().contains("java.lang.Enum<")){
                if(cls.isEnum()){
                    cinfoObject.put("type name", "enum");
                }else{
                    cinfoObject.put("type name", "class");
                }
            }
            
            
                
                 JSONArray iArr = new JSONArray();
              Class[] inter = cls.getInterfaces();
              for (Class xc : inter){
                   if(cls.getPackage() == null){
                      iArr.add(xc.toGenericString());
                }else{
                    String hu = new String(xc.toGenericString().replace(cls.getPackage().getName()+".", ""));
                     iArr.add(hu);  
                }
                  
              }
            cinfoObject.put("Implemented Interface", iArr);
            
            if(cls.getGenericSuperclass() == null){
            cinfoObject.put("superclass", "");
             }
             else{
                if(cls.getPackage() == null){
            cinfoObject.put("superclass", cls.getGenericSuperclass().getTypeName());
                }else{
                    String hu = new String(cls.getGenericSuperclass().getTypeName().replace(cls.getPackage().getName()+".", ""));
             cinfoObject.put("superclass", hu);   
                }
             }
            
            
            
            
            cinfoArray.add(cinfoObject);
            testModelJsonObject.put("class", cinfoArray);  
            JSONArray coninfoArray = new JSONArray();
           
            
            Constructor[] con = cls.getDeclaredConstructors();
             
            for(Constructor c : con){
                
                JSONArray nuc = new JSONArray();
                JSONObject nuo = new JSONObject();
                int cModi = c.getModifiers();
                if(Modifier.isPrivate(cModi)){
                    nuo.put("modifier", "private");
                    System.out.println("Mpodifier: "+ nuo.get("modifier"));
                }else if(Modifier.isPublic(cModi)){
                    nuo.put("modifier", "public");
                    System.out.println("Mpodifier: "+ nuo.get("modifier"));
                }else if(Modifier.isProtected(cModi)){
                    nuo.put("modifier", "protected");
                    System.out.println("Mpodifier: "+ nuo.get("modifier"));
                }else {
                 nuo.put("modifier", "");
             }
                String name = "";
                if(cls.getPackage()!= null){
                 name = c.getName().replace(cls.getPackage().getName()+".", "");
                }else{
                name = c.getName();
                }
                 
                nuo.put("name", name);
           //     System.out.println("get tostring method cons: "+c.toString());
                System.out.println("parameter Type: ");
                Type[] types = c.getGenericParameterTypes();                
                for(Type t : types){
                    nuc.add(t.getTypeName());
                    System.out.println("t: "+t.getTypeName());
                }          
                nuo.put("parameters", nuc);
                System.out.println("-------------------------------");
                coninfoArray.add(nuo);
            }
            
            testModelJsonObject.put("constructors", coninfoArray);
            
            
            JSONArray methodinfoArray = new JSONArray();
            
            Method[] methods = cls.getDeclaredMethods();
            for(Method m : methods){
                
                JSONArray nuc = new JSONArray();
                JSONObject nuo = new JSONObject();
                int cModi = m.getModifiers();
                if(Modifier.isPrivate(cModi)){
                    nuo.put("modifier", "private");
                    System.out.println("Mpodifier: "+ nuo.get("modifier"));
                }else if(Modifier.isPublic(cModi)){
                    nuo.put("modifier", "public");
                    System.out.println("Mpodifier: "+ nuo.get("modifier"));
                }else if(Modifier.isProtected(cModi)){
                    nuo.put("modifier", "protected");
                    System.out.println("Mpodifier: "+ nuo.get("modifier"));
                }else {
                 nuo.put("modifier", "");
             }
                
           //     System.out.println("get tostring method cons: "+c.toString());
                System.out.println("parameter Type: ");
                Type[] types = m.getGenericParameterTypes();                
                for(Type t : types){
                     String p = t.getTypeName();
                                       p = p.replace("abstract", "");
                                       p = p.replace("final", "");
                                       p = p.replace("interface", "");
                                       p = p.replace("public", "");
                                       if(cls.getPackage()!= null){
                                           p = p.replace(cls.getPackage().getName()+".", "");
                                       }
                                       nuc.add(p);
                    System.out.println("t: "+t.getTypeName());
                }          
                nuo.put("parameters", nuc);
                String p = m.getName();
                                       p = p.replace("abstract", "");
                                       p = p.replace("final", "");
                                       p = p.replace("interface", "");
                                       p = p.replace("public", "");
                                       if(cls.getPackage()!= null){
                                           p = p.replace(cls.getPackage().getName()+".", "");
                                       }
                nuo.put("name", p);
                
               p = m.getGenericReturnType().getTypeName();
                                       p = p.replace("final", "");
                                       p = p.replace("interface", "");
                                       p = p.replace("public", "");
                                       if(cls.getPackage()!= null){
                                           p = p.replace(cls.getPackage().getName()+".", "");
                                       }
                nuo.put("return type", p);
                
                System.out.println("-------------------------------");
                methodinfoArray.add(nuo);
            }
            testModelJsonObject.put("methods", methodinfoArray);
            
                        //fields
            JSONArray fieldinfoArray = new JSONArray();
            
            Field[] fields = cls.getDeclaredFields();
            for(Field fi : fields){
                
                JSONArray nuc = new JSONArray();
                JSONObject nuo = new JSONObject();
                int cModi = fi.getModifiers();
                System.out.println("Modifier int:"+cModi);
                  nuo.put("name", fi.getName());  
                if(Modifier.isPrivate(cModi)){
                    nuo.put("modifier", "private");
                    System.out.println("field Modifier: "+ nuo.get("modifier"));
                }else if(Modifier.isPublic(cModi)){
                    nuo.put("modifier", "public");
                    System.out.println("field modifier: "+ nuo.get("modifier"));
                }else if(Modifier.isProtected(cModi)){
                    nuo.put("modifier", "protected");
                    System.out.println("field modifier: "+ nuo.get("modifier"));
                }else{
                    nuo.put("modifier", "");
                }
                if(Modifier.isFinal(cModi)){
                    nuo.put("final", "final");
                    System.out.println("final modifier: "+ nuo.get("final"));
                }
                                       String p = fi.getGenericType().getTypeName();
                                      // String p = fi.getGenericType().toString();
                                       p = p.replace("abstract", "");
                                       p = p.replace("final", "");
                                       p = p.replace("interface", "");
                                       p = p.replace("public", "");
                                       if(cls.getPackage()!= null){
                                           p = p.replace(cls.getPackage().getName()+".", "");
                                       }
                 nuo.put("generic type", p);                
                fieldinfoArray.add(nuo);
            }
            testModelJsonObject.put("fields", fieldinfoArray);
            
            JSONArray annoinfoArray = new JSONArray();
            
            Annotation[] annotations = cls.getAnnotations();
            annoinfoArray.addAll(Arrays.asList(annotations));
            testModelJsonObject.put("annotations", annoinfoArray);
            System.out.println("Gstring: "+ cls.toGenericString());                        
            System.out.println("Json: "+testModelJsonObject.toString());
                        
            cls = null;
} catch (MalformedURLException e) {
    Exceptions.printStackTrace(e);
}  catch (SecurityException ex) {
          Exceptions.printStackTrace(ex);
}catch (ClassNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
    

    return testModelJsonObject;
    
    
    }
    
    private static String packTraverser(String g, String n){
    String travel = "";
    String preparePath =  System.getProperty("user.dir")+ "\\JASACS\\"+g+"\\"+n;
    File f = new File(preparePath);
            //  System.out.println("is directory"+f.isFile());
    if(f.isDirectory()){
       for (File fx : f.listFiles()) {
           
            String s = n+"\\"+fx.getName();
           // System.out.println("s --> "+s);
          travel +=  packTraverser(g, s);
            }
       
    
    }else{
    travel = n;
    }             
    return travel;
    }
       public static void main(String[] args){
    LoadStudentModelVersion s = new LoadStudentModelVersion("Testing","1412625");
    s.startOperation();
    
    }
}
