/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.distanceFinder.tests;

import edu.vanier.distanceFinder.controllers.PostalCodeController;

/**
 *
 * @author Mervin
 */
public class Driver {
    public static void main(String[] args) {
            testParse();
        
    }
    
    public static void testParse(){
        PostalCodeController pcc = new PostalCodeController("src\\main\\resources\\zipcodes.csv");
        pcc.parse();
    }
    
    public static void testDistamceTo(String from){
        
    }
    
    public static void testNearbyLocations(String from){
        
    }
}
