/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasacs;


import java.io.Serializable;


/**
 *
 * @author 1412625
 */
public class AssessObject implements Serializable{

    private final String loadpath;

    public AssessObject(String lp){
        loadpath = lp;
    }
    public String getLoadPath(){
    return loadpath;
    }
}
