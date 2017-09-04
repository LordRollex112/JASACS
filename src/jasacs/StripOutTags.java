/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasacs;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniel
 */
public class StripOutTags {
    String s = "<html><h1><u><center>1211111's Version for Assessment :Construct</center></u></h1><br/><h3> <u>Class Description</u></h3><table><tr><td>Package: Package: <span class='error'&gt;testcaseb</span></td><td>&lt;span class='error'&gt;The class is in the wrong Package</span></td></tr><tr>&lt;td rowspan='7'&gt; public  class Construct </td></tr><tr><td>The right modifier is used for the class definition</td></tr><tr><td>The class is rightly defined as  </td></tr><tr><td>The class is rightly defined as a(n) class</td></tr><tr><td>The class name is rightly defined</td></tr></table><br/><h3> <u>Constructors</u></h3><table><tr><td>public Construct (java.util.Listpublic Construct (java.util.List&lt;java.lang.Integer&gt;, java.util.List, java.util.List&lt;java.lang.String&gt;)</td><td>This constructor is rightly defined</td></tr><tr><td>public Construct (java.util.Listpublic Construct (java.util.List&lt;java.lang.Integer&gt;, java.lang.String)</td><td>This constructor is rightly defined</td></tr><tr><td>public Construct (int, java.lang.String)</td><td>This constructor is rightly defined</td></tr><tr><td>public Construct (java.lang.String, java.lang.String)</td><td>This constructor is rightly defined</td></tr><tr><td>public Construct (java.lang.String, java.lang.String, java.lang.String)</td><td>This constructor is rightly defined</td></tr><tr>&lt;td colspan='2'&gt; Remark: All Constructors have been rightly defined</td></tr></table><br/><h3> <u>Fields</u></h3><table><tr><td>&lt;span class='error'&gt;public class java.lang.String studentID</span></td><td>&lt;span class='error'&gt;This field is an alien</span></td></tr><tr><td>private class java.lang.String test1</td><td>This field is rightly defined</td></tr><tr><td>private class java.lang.String test2</td><td>This field is rightly defined</td></tr><tr><td>private class java.lang.String test3</td><td>This field is rightly defined</td></tr><tr>&lt;td colspan='2'&gt; Remark: All Fields have been rightly defined,  Remark: All Fields have been rightly defined, &lt;span class='error'&gt;however, additional Fields defined</span></td></tr></table><br/><h3> <u>Methods</u></h3><table><tr><td>public void setTest1 (java.lang.String)</td><td>This method is rightly defined</td></tr><tr><td>public java.lang.String getTest2 ()</td><td>This method is rightly defined</td></tr><tr><td>public void setTest2 (java.lang.String)</td><td>This method is rightly defined</td></tr><tr><td>public java.lang.String getTest1 ()</td><td>This method is rightly defined</td></tr><tr><td>public java.lang.String getTest3 ()</td><td>This method is rightly defined</td></tr><tr><td>public java.util.Listpublic java.util.List&lt;java.lang.String&gt; getAllMembers (java.util.List getAllMembers (java.util.List&lt;java.lang.Integer&gt;)</td><td>This method is rightly defined</td></tr><tr><td>public void setTest3 (java.lang.String)</td><td>This method is rightly defined</td></tr><tr>&lt;td colspan='2'&gt; Remark: All Methods have been rightly defined</td></tr></table><br/><h3><u>Annotations</u></h3><table><tr>&lt;td colspan='2'&gt; Remark: Perfect, no annotation definition is required</td></tr></table>";
         
    List<String> tags = new ArrayList();
    
    public StripOutTags(){
    tags.add("<html>");
    tags.add("<td rowspan='7'>");
    tags.add("<td rowspan='10'>");
    
    tags.add("</html>");
    tags.add("<table>");
    tags.add("</table>");
    tags.add("</p>");
    tags.add("<p>");
    tags.add("<body>");
    tags.add("</body>");
    tags.add("<span>");
    tags.add("</span>");
    tags.add("<th>");
    tags.add("</th>");
    tags.add("<td>");
    tags.add("</td>");
    tags.add("<tr>");
    tags.add("</tr>");
    tags.add("<div>");
    tags.add("</div>");
    tags.add("<h1>");
    tags.add("<u>");
    tags.add("</u>");
    tags.add("</center>");
    tags.add("<center>");
    tags.add("<br/>");
    tags.add("</h1>");
    tags.add("<h2>");
    tags.add("</h2>");
    tags.add("<h3>");
    tags.add("</h3>");
    tags.add("<h4>");
    tags.add("</h4>");
    tags.add("</h5>");
    tags.add("<h5>");
    tags.add("<h6>");
    tags.add("</h6>");
    tags.add("</span>");
    tags.add("<span class='error'>"); 
    tags.add("<td colspan='2'>"); 
    tags.add("<td rowspan='2'>"); 
    
    
    }
    public String filterOutTag(String z){
    String tag = "";
          int ltag = 0;
       int rtag = 0;
       String xc = z;
       while (xc.contains("<")){     
          tag += xc.substring(0, xc.indexOf("<"));
           ltag = xc.indexOf("<");
            rtag = xc.indexOf(">");
            String ss = xc.substring(ltag, rtag+1);
       if (tags.contains(ss)){
           tag += ss;           
       }
       else{
           tag += xc.substring(0, xc.indexOf("<"));
           System.out.println("char at last indes "+ ss.charAt(ss.length() - 1));
           String cv = new String(ss.replace(""+ss.charAt(ss.length() - 1), "&gt;"));  
           String cva = new String(cv.replace(""+ss.charAt(0), "&lt;"));  
           tag += cva;      
       }

       xc = xc.substring(rtag + 1);
       } 
    return tag;
    }
    public static void main(String[] args) {
        StripOutTags d = new StripOutTags();
        d.filterOutTag(d.s);
 

    }
    
}
