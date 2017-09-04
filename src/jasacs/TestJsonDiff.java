/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasacs;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Livinvg
 */
public class TestJsonDiff {

    JSONObject jso = new JSONObject();

    JSONObject version;//student version
    JSONObject model;//test model

    public TestJsonDiff(JSONObject mod, JSONObject ver) {
        model = mod;
        version = ver;
    }

    public String testClassDescriptions() {
        String result = "<table>";
        String cdleft = "";
            String cd1 = "";
            String cd2 = "";
            String cd3 = "";
            String cd4 = "";
            String cd5 = "";
            String cd6="";
            String cd7="";
        JSONArray ma = (JSONArray) model.get("class");
        JSONArray va = (JSONArray) version.get("class");
        JSONObject m = (JSONObject) ma.get(0);
        JSONObject v = (JSONObject) va.get(0);
{System.out.println("the test model-----------------------------------------");
    System.out.println(m);
            if (m.get("package name").toString().equalsIgnoreCase(v.get("package name").toString())) {
                result += "<tr><td>Package: "+m.get("package name")+"</td><td>The "+v.get("type name")+" is in the right Package</td></tr>";
            } else {
                result += "<tr><td>Package: <span class='error'>"+v.get("package name")+"</span></td><td><span class='error'>The "+v.get("type name")+" is in the wrong Package</span></td></tr>";
            }
            
            
             if (m.get("Final").toString().equalsIgnoreCase(v.get("Final").toString())) {
                cdleft += m.get("Final").toString()+" ";
                
                if(!m.get("Final").equals("") && !(m.get("Final") != null)){
                cd3  += "<td>The right is rightly defined as "+v.get("Final")+" </td></tr>";
                }
            } else {
                cdleft += "<span class='error'>"+v.get("Final").toString()+" </span>";
                cd3 += "<td><span class='error'>The "+m.get("type name")+" is suppose to be declared "+m.get("Final")+" </span></td></tr>";
            }
             if (m.get("modifier").toString().equalsIgnoreCase(v.get("modifier").toString())) {
                cdleft += m.get("modifier").toString()+" ";
                cd1  += "<tr><td>The right modifier is used for the "+v.get("type name")+" definition</td></tr>";
            } else {
                cdleft += "<span class='error'>"+v.get("modifier").toString()+" </span>";
                cd1 += "<tr><td><span class='error'>The wrong modifier is used for the "+v.get("type name")+" definition</span></td></tr>";
            }
            
              if (m.get("abstract").toString().equalsIgnoreCase(v.get("abstract").toString())) {
                cdleft += v.get("abstract").toString()+" ";
                  System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+v.get("abstract"));
                cd2  += "<tr><td>The class is rightly defined as "+v.get("abstract")+" </td></tr>";
            } else {
                cdleft += "<span class='error'>"+v.get("abstract").toString()+" </span>";
                cd2 += "<tr><td><span class='error'>The class is  "+v.get("abstract")+", it should be declared as "+m.get("abstract")+" </span></td></tr>";
            }            
             

            
            if (m.get("type name").toString().equalsIgnoreCase(v.get("type name").toString())) {
                cdleft += v.get("type name").toString()+" ";
                cd4 += "<tr><td>The class is rightly defined as a(n) " + m.get("type name") + "</td></tr>";
            } else {
                cdleft += "<span class='error'>"+v.get("type name").toString()+" </span>";
                cd4 += "<tr><td><span class='error'>The class is wrongly defined as a(n) " + v.get("type name") + " instead of a(n) " + m.get("type name") + "</span></td></tr>";
            }
            if (m.get("class name").toString().equalsIgnoreCase(v.get("class name").toString())) {
                cdleft += v.get("class name").toString()+" ";
                cd5 += "<tr><td>The class name is rightly defined</td></tr>";
            } else {
                cdleft += "<span class='error'>"+v.get("class name").toString()+" </span>";
                cd5 += "<tr><td><span class='error'>The class name is wrongly defined, it should be " + m.get("class name") + " instead of a(n) " + v.get("class name") + "</span></td></tr>";
            }
    

            // testing the super class          
            if (m.get("type name").toString().equalsIgnoreCase("class")
                    || m.get("type name").toString().equalsIgnoreCase("enum")) {

                if(v.get("type name").toString().equalsIgnoreCase("interface")){
                    result += "The is an interface, it should have been declared as a class<br/>";
                }else{
                    if(m.get("type name").toString().equals("enum") && v.get("type name").toString().equals("enum")){
                     //  result += "The is enum class extend the right superclass the Test Model<br/>";
                     // note if model == enum and stuent version is a class or vice versa
                    }else{
                    
                        if (m.get("superclass").toString().equalsIgnoreCase(v.get("superclass").toString())) {
                            System.out.println("true ************************************************");
                        if(m.get("superclass").toString().equals("java.lang.Object") || m.get("superclass").toString().equals("")){
                        
                        }else{
                                      String p = m.get("superclass").toString();
                                       p = p.replace("abstract", "");
                                       p = p.replace("final", "");
                                       p = p.replace("interface", "");
                                       p = p.replace("public", "");
                                    cdleft += "extends "+p+" ";
                                  cd6  += "<tr><td>The class extends the right base class</td></tr>";   
                          }
                        } else {
                            System.out.println("false ************************************************");
                        if(v.get("superclass").toString().equals("java.lang.Object")){
                             cd6  += "<tr><td><span class='error'>The class is suppose to extend "+m.get("superclass")+"</span></td></tr>";   
                        }else{
                                    String p = v.get("superclass").toString();
                                       p = p.replace("abstract", "");
                                       p = p.replace("final", "");
                                       p = p.replace("interface", "");
                                       p = p.replace("public", "");
                                  cdleft += "<span class='error'>extends "+p+" </span>";
                                  cd6  += "<tr><td><span class='error'>The class does not extend the right base class</span></td></tr>";   
                          }
                        }
                
                    }
              }
                
                
                
            } else {
                if(v.get("superclass").toString().equalsIgnoreCase(m.get("superclass").toString())){                   
                    if(v.get("superclass").toString().equalsIgnoreCase("java.lang.Object") || v.get("superclass").toString().equals("")){
                        
                    
                    }else{
                                    String p = v.get("superclass").toString();
                                       p = p.replace("abstract", "");
                                       p = p.replace("final", "");
                                       p = p.replace("interface", "");
                                       p = p.replace("public", "");
                    cdleft += "<span class='error'> extends "+p+" </span>";
                    cd6  += "<tr><td><span class='error'>The "+v.get("type name")+" does not extend the right base class</span></td></tr>"; 
                    }
                    
                
                }else{
                    if(v.get("superclass").equals("java.lang.Object")){
                    cdleft += "";
                    cd6  += "<tr><td><span class='error'>The "+v.get("type name")+" does not extend the right base class</span></td></tr>"; 
                    }else{
                     cdleft += "<span class='error'>extends "+v.get("superclass").toString()+" </span>";
                    cd6  += "<tr><td><span class='error'>The "+v.get("type name")+" does not extend the right base class</span></td></tr>"; 
                    
                    }
                // here is an array so do work here
//            if(m.get("Implemented Interface").toString().equalsIgnoreCase(v.get("Implemented Interface").toString())){
//            result +="The class extends the right superclass";           
//            }else {
//            result +="The class extends a different superclass from that of the Test Model";           
//            }   

            }

            }

        }
                        
                
                JSONArray iMA = (JSONArray) m.get("Implemented Interface");
                JSONArray iVA = (JSONArray) v.get("Implemented Interface");
                
                System.out.println("size of ima-----------------------------------------aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa:"+iVA.size());
               if (iMA.isEmpty() & iMA.equals(iVA)) {
 //                   result += "Perfect, no interface implenmentation is required";
//                    System.out.println("Checking: " + result);
                } 
                 else if(iVA.size() > 0){
                     if(iMA.size() > 0){
                      cdleft += "implements ";
                     }else {
                     cdleft += "<span class='error'>implements </span>";
                     }  
                    
                    int found = 0;
                    int notFound = 0;
                    String[] mar = new String[iMA.size()];
                    String[] var = new String[iVA.size()];
                    for (int i = 0; i < mar.length; i++) {
                        mar[i] = iMA.get(i).toString();
                    }
                    for (int i = 0; i < var.length; i++) {
                        boolean xt = false;
                        var[i] = iVA.get(i).toString();
                        for(String cv : mar){
                            if(cv.equals(var[i])){
                                xt = true;
                                break;
                            }
                        }
                        String p = "";
                        if(xt){
                         p = iVA.get(i).toString();
                        }else{
                         p = "<span class='error'>"+iVA.get(i).toString()+"</span>";
                        }
                  
                   p = p.replace("abstract", "");
                   p = p.replace("final", "");
                   p = p.replace("interface", "");
                   p = p.replace("public", "");
                 
                        cdleft += p;
                        if(i != var.length - 1){
                        cdleft += ", ";
                        }
                    }

                    for (String s : var) {
                        boolean p = false;
                        for (String nx : mar) {
                            if (s.equalsIgnoreCase(nx)) {
                                p = true;
                                break;
                            }
                        }
                        if (p) {
                            found = found + 1;
                        } else {
                            notFound = notFound + 1;
                            
                        }
                        String cgy = new String(cdleft.replace(s, "<span class='error'>"+s+"</span>"));
                        System.out.println("cgy =================================================+++++++++++++++++++++++++++++++++++++++++++++"+cgy);
                        cdleft = new String(cgy);
                        
                    }

                    System.out.println("found: " + found);
                    System.out.println("not found: " + notFound);
                    if (found == mar.length & notFound == 0) {
                        cd7 += "<tr><td>All interfaces have been rightly implemented</td></tr>";
                    } else if (found == mar.length & notFound > 0) {
                        cd7 += "<tr><td>All interfaces have been rightly defined,<span class='error'> however, additional interfaces were implemented</span></td></tr>";
                    } else if ((found < mar.length & found > 0) & notFound == 0) {
                        cd7 += "<tr><td><span class='error'>Not all interfaces were implemented</td></tr>";
                    } else if ((found < mar.length & found > 0) & notFound > 0) {
                        cd7 += "<tr><td><span class='error'> Some of the interfaces implemented were not required</td></tr>";
                    } else {

                        cd7 += "<tr><td><span class='error'>The interfaces implemented were not required</td></tr>";
                    }

                }      
            result += "<tr><td rowspan='10'>"+cdleft+"</td></tr>";
            result += cd3+cd1+cd2+cd4+cd5+cd6+cd7;
               System.out.println("result: " + result);
        return result+"</table>";
    }

    public String testFields() {
        String result = "";
        JSONArray m = (JSONArray) model.get("fields");
        JSONArray v = (JSONArray) version.get("fields");
           String remark ="<tr><td colspan='2'> Remark: ";

     
            int found = 0;
            int notFound = 0;  
//            int pFound = 0;
//            int nopFound = 0;
            JSONObject[] mar = new JSONObject[m.size()];
            JSONObject[] var = new JSONObject[v.size()];
            for (int i = 0; i < mar.length; i++) {
                mar[i] = (JSONObject) m.get(i);
            }
            for (int i = 0; i < var.length; i++) {
                var[i] = (JSONObject) v.get(i);
            }
            
            for(JSONObject js : var){
                boolean p = false;
                for(JSONObject ms : mar){
                    if(js.toJSONString().equals(ms.toJSONString())){
                        p = true;
                        break;
                    }
                }
                if (p) {
                    
                      result +="<tr><td>"+js.get("modifier")+ " "+js.get("generic type")+" "+js.get("name")+"</td><td>This field is rightly defined</td></tr>";
                    found = found + 1;
                } else {
                      result +="<tr><td><span class='error'>"+js.get("modifier")+ " "+js.get("generic type")+" "+js.get("name")+"</span></td><td><span class='error'>This attribute is not required</span></td></tr>";

                    notFound = notFound + 1;
                    
                
                }
            }
     
            System.out.println("found: " + found);
            System.out.println("not found: " + notFound);
       
            if (mar.length == 0 & notFound == 0) {
                remark += "Perfect, no attributes declaretion is required";
            }else if(mar.length == 0 & notFound > 0){
               remark += "No attributes declaration is required, <span class='error'>however, "+notFound+" unrequired attributes were declared</span>";
            }
            else if (found == mar.length & notFound == 0) {
                remark += "The required "+found+" attributes have been rightly declared";
            }
            else if (found == mar.length & notFound > 0) {
                remark += "The "+found+" required attribute(s) have been rightly declared, <span class='error'>however, "+notFound+" unrequired attributes were declared</span>";
            } else if ((found < mar.length & found > 0) & notFound == 0) {
                remark += "<span class='error'>Not all Methods are defined</span>";
            } else if ((found < mar.length & found > 0) & notFound > 0) {
                remark += "<span class='error'>"+found+" of "+mar.length+ " required attributes were declared. "+notFound+" unrequired attributes were declared</span>";
            } else if(mar.length > 0 & notFound == 0){
                remark += "<span class='error'>"+mar.length+" required attribute were not found</span>";
            }else {
                remark += "<span class='error'>Unrequired attribute were found</span>";
            }

      
               remark +="</td></tr>";
            
        System.out.println("result: " + result);
        return "<table>"+result+remark+"</table>";
    }
    public String testAnnotations() {
        String result = "";
        JSONArray m = (JSONArray) model.get("annotations");
        JSONArray v = (JSONArray) version.get("annotations");
           String remark ="<tr><td colspan='2'> Remark: ";

     
            int found = 0;
            int notFound = 0;  
//            int pFound = 0;
//            int nopFound = 0;
            String[] mar = new String[m.size()];
            String[] var = new String[v.size()];
            for (int i = 0; i < mar.length; i++) {
                mar[i] =  m.get(i).toString();
            }
            for (int i = 0; i < var.length; i++) {
                var[i] =  v.get(i).toString();
            }
            
            for(String js : var){
                boolean p = false;
                for(String ms : mar){
                    if(js.equals(ms)){
                        p = true;
                        break;
                    }
                }
                if (p) {
                    
                      result +="<tr><td>"+js+"</td><td>This annotation is rightly defined</td></tr>";
                    found = found + 1;
                } else {
                      result +="<tr><td><span class='error'>"+js+"</span></td><td><span class='error'>This annotation is not required</span></td></tr>";

                    notFound = notFound + 1;
                    
                
                }
            }
     
            System.out.println("found: " + found);
            System.out.println("not found: " + notFound);
       
            if (mar.length == 0 & notFound == 0) {
                remark += "Perfect, no annotations declaretion is required";
            }else if(mar.length == 0 & notFound > 0){
               remark += "No annotations declaration is required, <span class='error'>however, "+notFound+" unrequired annotations were declared</span>";
            }
            else if (found == mar.length & notFound == 0) {
                remark += "The required "+found+" annotations have been rightly declared";
            }
            else if (found == mar.length & notFound > 0) {
                remark += "The "+found+" required annotation(s) have been rightly declared, <span class='error'>however, "+notFound+" unrequired annotations were declared</span>";
            } else if ((found < mar.length & found > 0) & notFound == 0) {
                remark += "<span class='error'>Not all Methods are defined</span>";
            } else if ((found < mar.length & found > 0) & notFound > 0) {
                remark += "<span class='error'>"+found+" of "+mar.length+ " required annotations were declared. "+notFound+" unrequired annotations were declared</span>";
            } else {
                remark += "<span class='error'>"+notFound+" unrequired annotation were found</span>";
            }
               remark +="</td></tr>";
            
        System.out.println("result: " + result);
        return "<table>"+result+remark+"</table>";
    }
    

    public String testConstructors() {
        String result = "";
        JSONArray m = (JSONArray) model.get("constructors");
        JSONArray v = (JSONArray) version.get("constructors");
        String name = ((JSONObject)((JSONArray) version.get("class")).get(0)).get("class name").toString();
        
 
            int found = 0;
            int notFound = 0;  
//            int pFound = 0;
//            int nopFound = 0;
            JSONObject[] mar = new JSONObject[m.size()];
            JSONObject[] var = new JSONObject[v.size()];
            for (int i = 0; i < mar.length; i++) {
                mar[i] = (JSONObject) m.get(i);
            }
            for (int i = 0; i < var.length; i++) {
                var[i] = (JSONObject) v.get(i);
            }
            
            for(JSONObject js : var){
              //get the name first  
                JSONArray tp = (JSONArray) js.get("parameters"); // stud ver
               
      
                boolean p = false;
              
                for(JSONObject ms : mar){
                    
                    if(js.equals(ms)){
                        p = true;
                        // test its declaration                      
                  
                    }
                }
         
                
     
                
                
                
                if (p) {
                    result +="<tr><td>"+js.get("modifier")+ " "+name+" (";
                            
                            
                            JSONArray pa = (JSONArray)js.get("parameters");
                     
                         
                            for(int xc = 0; xc <pa.size(); xc++){
                              result +=  pa.get(xc).toString();
                              if(xc < pa.size() - 1)
                                  result +=", ";
                            }
//                           result += designer;
                          result +=   ")</td><td>This constructor is rightly defined</td></tr>";
                    found = found + 1;
                } else {
                    
                     result +="<tr><td><span class='error'>"+js.get("modifier")+ " "+name+" (";
                            
                            
                            JSONArray pa = (JSONArray)js.get("parameters");
                     
                         
                            for(int xc = 0; xc <pa.size(); xc++){
                              result +=  pa.get(xc).toString();
                              if(xc < pa.size() - 1)
                                  result +=", ";
                            }
                          //  result += designer;
                          result +=   ")</span></td><td><span class='error'>This constructor is not required</span></td></tr>";
                    
                    
                    notFound = notFound + 1;
                }
            }

String remark ="<tr><td colspan='2'> Remark: ";
            System.out.println("found: " + found);
            System.out.println("not found: " + notFound);
            
        if (mar.length == 0 & notFound == 0) {
                remark += "Perfect, no constructor declaretion is required";
            }else if(mar.length == 0 & notFound > 0){
               remark += "No constructor declaration is required, <span class='error'>however, "+notFound+" unrequired constructors were declared</span>";
            }
            else if (found == mar.length & notFound == 0) {
                remark += "The required "+found+" constructors have been rightly declared";
            }
            else if (found == mar.length & notFound > 0) {
                remark += "The "+found+" required constructor(s) have been rightly declared, <span class='error'>however, "+notFound+" unrequired constructors were declared</span>";
            } else if ((found < mar.length & found > 0) & notFound == 0) {
                remark += "<span class='error'>Not all Methods are defined</span>";
            } else if ((found < mar.length & found > 0) & notFound > 0) {
                remark += "<span class='error'>"+found+" of "+mar.length+ " required constructors were declared. "+notFound+" unrequired constructors were declared</span>";
            }
             else if(mar.length > 0 & notFound == 0){
                remark += "<span class='error'>"+mar.length+" required constructor were not found</span>";
            }
            else {
                remark += "<span class='error'>"+notFound+" unrequired constructor were found</span>";
            }

        remark +="</td></tr>";
            
        System.out.println("result: " + result);
        return "<table>"+result+remark+"</table>";
    }

    public String testMethods() {
        String result = "";
        JSONArray m = (JSONArray) model.get("methods");
        JSONArray v = (JSONArray) version.get("methods");
        

   
            int found = 0;
            int notFound = 0;  
//            int pFound = 0;
//            int nopFound = 0;
            JSONObject[] mar = new JSONObject[m.size()];
            JSONObject[] var = new JSONObject[v.size()];
            for (int i = 0; i < mar.length; i++) {
                mar[i] = (JSONObject) m.get(i);
            }
            for (int i = 0; i < var.length; i++) {
                var[i] = (JSONObject) v.get(i);
            }
            
            for(JSONObject js : var){
                boolean p = false;
                for(JSONObject ms : mar){
                    if(js.equals(ms)){
                        p = true;
                        break;
                    }
                }
                if (p) {
                    
                     result +="<tr><td>"+js.get("modifier")+ " "+js.get("return type")+ " "+js.get("name")+" (";
                            
                            
                            JSONArray pa = (JSONArray)js.get("parameters");
                     
                         
                            for(int xc = 0; xc <pa.size(); xc++){
                              result +=  pa.get(xc).toString();
                              if(xc < pa.size() - 1)
                                  result +=", ";
                            }
                            
                          result +=   ")</td><td>This method is rightly defined</td></tr>";
                    
                    
                    found = found + 1;
                } else {
                    
                       result +="<tr><td><span class='error'>"+js.get("modifier")+ " "+js.get("return type")+ " "+js.get("name")+" (";
                            
                            
                            JSONArray pa = (JSONArray)js.get("parameters");
                     
                         
                            for(int xc = 0; xc <pa.size(); xc++){
                              result +=  pa.get(xc).toString();
                              if(xc < pa.size() - 1)
                                  result +=", ";
                            }
                            
                          result +=   ")</span></td><td><span class='error'>This method is not required</span></td></tr>";
                    
                    
                    
                    
                    notFound = notFound + 1;
                }
            }
String remark ="<tr><td colspan='2'> Remark: ";

            System.out.println("found: " + found);
            System.out.println("not found: " + notFound);
            
            if (mar.length == 0 & notFound == 0) {
                remark += "Perfect, no Methods declaretion is required";
            }else if(mar.length == 0 & notFound > 0){
               remark += "No Methods declaration is required, <span class='error'>however, "+notFound+" unrequired methods were declared</span>";
            }
            else if (found == mar.length & notFound == 0) {
                remark += "The required "+found+" methods have been rightly declared";
            }
            else if (found == mar.length & notFound > 0) {
                remark += "The "+found+" required method(s) have been rightly declared, <span class='error'>however, "+notFound+" unrequired methods were declared</span>";
            } else if ((found < mar.length & found > 0) & notFound == 0) {
                remark += "<span class='error'>Not all Methods are defined</span>";
            } else if ((found < mar.length & found > 0) & notFound > 0) {
                remark += "<span class='error'>"+found+" of "+mar.length+ " required methods were declared. "+notFound+" unrequired methods were declared</span>";
            }
             else if(mar.length > 0 & notFound == 0){
                remark += "<span class='error'>"+mar.length+" required methods were not found</span>";
            }
            
            else {
                remark += "<span class='error'>"+notFound+" unrequired method were found</span>";
            }

        
        remark +="</td></tr>";
            
        System.out.println("result: " + result);
        return "<table>"+result+remark+"</table>";
    }

    public static void main(String[] args) {
        JSONObject jsob = new JSONObject();
        JSONObject jsoc = new JSONObject();

        JSONArray jacob = new JSONArray();
        jacob.add("");

        jsob.put("constructors", jacob);

        JSONArray jamb = new JSONArray();
        jamb.add("public abstract void mastapam.RecordSwitcherInterface.gotoCurrent()");
        jamb.add("public abstract void mastapam.RecordSwitcherInterface.gotoPrevious()");
        jamb.add("public abstract void mastapam.RecordSwitcherInterface.gotoNext()");
        jamb.add("public abstract void mastapam.RecordSwitcherInterface.gotoAll()");
        jamb.add("public abstract void mastapam.RecordSwitcherInterface.gotoStart()");
        jamb.add("public abstract void mastapam.RecordSwitcherInterface.gotoFinish()");
        jsob.put("methods", jamb);

        JSONArray jafb = new JSONArray();
        jafb.add("public static final java.lang.String mastapam.RecordSwitcherInterface.studentID");

        jsob.put("fields", jafb);

        jsob.put("Assessment", "RecordSwitcherInterface");

        jsob.put("ID", "Test Model");

        JSONObject jcb = new JSONObject();

        JSONArray jacb = new JSONArray();
        jcb.put("package name", "package mastapam");
        jcb.put("class name", "mastapam.RecordSwitcherInterface");
        jcb.put("superclass", "jacob");
        jcb.put("modifier", "abstract");
        jcb.put("type name", "interface");
        jacb.add(jcb);

        jsob.put("class", jacb);

        JSONArray jacoc = new JSONArray();
        jacoc.add("");

        jsoc.put("constructors", jacoc);

        JSONArray jamc = new JSONArray();
        jamc.add("public abstract void mastapam.RecordSwitcherInterface.gotoCurrent()");
        jamc.add("public abstract void mastapam.RecordSwitcherInterface.gotoPrevious()");
        jamc.add("public abstract void mastapam.RecordSwitcherInterface.gotoNext()");
        jamc.add("public abstract void mastapam.RecordSwitcherInterface.gotoAll()");
        jamc.add("public abstract void mastapam.RecordSwitcherInterface.gotoStart()");
        jamc.add("public abstract void mastapam.RecordSwitcherInterface.gotoFinish()");
        jsoc.put("methods", jamc);

        JSONArray jafc = new JSONArray();
        jafc.add("public static final java.lang.String mastapam.RecordSwitcherInterface.studentID");

        jsoc.put("fields", jafc);

        jsoc.put("Assessment", "RecordSwitcherInterface");

        jsoc.put("ID", "Test Model");

        JSONObject jcc = new JSONObject();

        JSONArray jacc = new JSONArray();
        jcc.put("package name", "package mastapam");
        jcc.put("class name", "mastapam.RecordSwitcherInterface");
        jcc.put("superclass", "jacob");
        jcc.put("modifier", "abstract");
        jcc.put("type name", "interface");
        jacc.add(jcc);

        jsoc.put("class", jacc);

        TestJsonDiff tj = new TestJsonDiff(jsob, jsoc);
        tj.testFields();
    }
}