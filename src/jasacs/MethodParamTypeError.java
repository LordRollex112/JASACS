/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasacs;

import java.util.List;

/**
 *
 * @author jcgolov
 */
public class MethodParamTypeError {
    
    private final String methodName;
    private final List solutionParams;
    private final List expectedType;
    private final List usedType;
            
    public MethodParamTypeError(String methodName, List solutionParams, List expectedType, List usedType){
        this.methodName = methodName;
        this.expectedType = expectedType;
        this.usedType = usedType;
        this.solutionParams = solutionParams;
    }
    
    
    @Override
    public String toString(){
        String st = "";
        st += methodName + "\t";
        st += "expected: " + solutionParams + "\t";
        st += "found: " + expectedType + "\t";
        st += usedType + "\t";
        return st;
    }
}
