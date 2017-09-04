
package jasacs;

/**
 *
 * @author jcgolov
 */
public class ClassLocationObject {
    
    private String classLocation;
    private String fullClassLocation;
    private String fullProjectLocation;
    private String className;
    private String packageName;
    private String fullClassDetails;
    
    public String getClassLocation(){
        return classLocation;
    } 
    
     public String getPackageName(){
        return packageName;
    } 
    
    public String getClassName(){
        return className;
    } 
    
    public String getProjectLocation(){
        return fullProjectLocation;
    } 
    
    public String getFullClassDetails(){
        return fullClassDetails;
    }
    
    public String getFullClassLocation(){
        return fullClassLocation;
    } 
    
    public void setClassLocation(String info){
        classLocation = info;
    }  
    
    public void setFullClassLocation(String info){
        fullClassLocation = info;
        String cn = info.substring(0, info.indexOf(".class"));
        setclassName(cn);
    } 
    public void setFullprojectLocation(String info){
        fullProjectLocation = info;
    } 
    
    private void setclassName(String info){
        String[] n = info.split("\\\\");
        className = n[n.length - 1];
    } 
    
    public void setPackageName(String info){
        packageName = info;
    } 
    
     public void setFullClassDetails(){
        fullClassDetails = getPackageName()+"."+getClassName();
    } 
}
