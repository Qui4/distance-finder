/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.distanceFinder.tests;

import edu.vanier.distanceFinder.controllers.PostalCodeController;
import edu.vanier.distanceFinder.models.PostalCode;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Mervin
 */
public class Driver {
    
    
    public static void main(String[] args) throws IOException {
        
        PostalCodeController pcc = testParse("src\\main\\resources\\zipcodes.csv");  
        Scanner sc = new Scanner(System.in);
        System.out.println("distanceTo ");
        
        
        
        //String fromKey = sc.nextLine(); 
        testDistanceTo("h4h");
        


        
        
        
    }
    
    public static PostalCodeController testParse(String filepath){
        PostalCodeController pcc = new PostalCodeController(filepath);
        pcc.parse();
        System.out.println(pcc.getPostalCodes().get("H7H"));
        System.out.println(pcc.getPostalCodes().get("N7L"));
        return pcc;
    }
    
    public static void testDistanceTo(String fromKey){
        
        PostalCodeController pcc = new PostalCodeController("src\\main\\resources\\zipcodes.csv");
        pcc.parse();
        
        HashMap<String, PostalCode> postalCodes = pcc.getPostalCodes();
        
        Scanner sc = new Scanner(System.in); 
        String toKey = "X0A";
        
        System.out.println(pcc.distanceTo(fromKey.strip().toUpperCase(), toKey));
        
        
        
    }
    
    public static void testNearbyLocations(String from){
        
    }
}
