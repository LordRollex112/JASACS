/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasacs;

import java.io.File;

/**
 *
 * @author 1412625
 */
public class TestDep {
    public static void main(String[] args) {
        File f = new File("H:\\Desktop\\Multimedia.class");
        GetFullClassPath g = new GetFullClassPath(f);   
    }
}