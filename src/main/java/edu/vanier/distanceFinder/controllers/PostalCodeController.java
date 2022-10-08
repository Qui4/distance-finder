/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.distanceFinder.controllers;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import edu.vanier.distanceFinder.models.PostalCode;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mervin
 */
public class PostalCodeController {
    
    /**
     * Setting up the classes variables
     */
    private HashMap<String,PostalCode> postalCodes;
    private String csvFilePath;
    /**
     * Initiates a new PostalCodeController Object
     * @param csvFilePath defines the file path of the CSV file containing the data
     */
    public PostalCodeController(String csvFilePath) {
        this.csvFilePath = csvFilePath;
        postalCodes = new HashMap<String,PostalCode>();
    }
   /**
    * Returns a HashMap from the specified file path.
    * This method will store the values of the file into
    * an array line by line and will then instantiate a
    * new PostalCode Object to which it will assign specified
    * position of the created array to a variable in PostalCode
    * 
    * @return a HashMap of the parsed CSV file(postalCOdes) 
    */
    public HashMap<String,PostalCode> parse(){
        //adding a new reader item
       CSVReader reader;
        try {
            //instanciating reader item
            reader = new CSVReaderBuilder(new FileReader(csvFilePath)).build();
            //instaciating new array storing the current line
            String [] nextLine = null;
           try {
               //while there is a next line
               while ((nextLine = reader.readNext()) != null) {
                   //instanciating a new PostalCode
                   PostalCode p = new PostalCode();
                   //assigning values from the array to the PostalCode
                   p.setId(Integer.parseInt(nextLine[0]));
                   p.setCountry(nextLine[1]);
                   p.setPostalCode(nextLine[2]);
                   
                   //for cases where the line contains 8 items
                   if (nextLine.length==8){
                        p.setLatitude(nextLine[6]);
                        p.setLongitude(nextLine[7]); 
                   }
                   //for cases where the line contains 9 items
                   else if(nextLine.length==9){
                       p.setLatitude(nextLine[7]);
                       p.setLongitude(nextLine[8]);
                   }
                   //default case
                   else{
                       p.setProvince(nextLine[4]);
                       p.setLatitude(nextLine[5]);
                       p.setLongitude(nextLine[6]);    
                   }
                   //adding the Postalcode to the hashmap
                   this.postalCodes.put(p.getPostalCode(),p);
               }
               //printing the hashmap(for verif)
               //System.out.println( this.postalCodes);
           } catch (IOException ex) {
               Logger.getLogger(PostalCodeController.class.getName()).log(Level.SEVERE, null, ex);
             } 
             catch (CsvValidationException ex) {
               Logger.getLogger(PostalCodeController.class.getName()).log(Level.SEVERE, null, ex);
             }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PostalCodeController.class.getName()).log(Level.SEVERE, null, ex);
        }
     return postalCodes;
    }
    
    /**
     * 
     * @param from
     * @param to
     * @return 
     */
    public double distanceTo(String fromKey, String toKey){
        
        PostalCode from = postalCodes.get(fromKey);
        PostalCode to = postalCodes.get(toKey);
        
        double lonFrom = Double.parseDouble(from.getLongitude().strip().toUpperCase());
        double lonTo = Double.parseDouble(to.getLongitude().strip().toUpperCase());
        double latFrom = Double.parseDouble(from.getLatitude().strip().toUpperCase());
        double latTo = Double.parseDouble(to.getLatitude().strip().toUpperCase());
        
        
      // distance between latitudes and longitudes
        double dLat = Math.toRadians(latTo - latFrom);
        double dLon = Math.toRadians(lonTo - lonFrom);
 
        // convert to radians
        latFrom = Math.toRadians(latFrom);
        latTo = Math.toRadians(latTo);
 
        // apply formula
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                   Math.cos(latFrom) *Math.cos(latTo)*
                   Math.pow(Math.sin(dLon / 2), 2) ;
        double rad = 6371;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));;
        return rad * c;
    }
    
    public HashMap<String,PostalCode> nearbyLocations(String from){
        HashMap<String,PostalCode> pCodeHolder = new HashMap<String,PostalCode>();
        postalCodes.forEach((key, pCode) -> {
         if(distanceTo(from,key)<100.00){
            pCodeHolder.put(key, pCode);
         }
        });
        return pCodeHolder;
    }

    public HashMap<String, PostalCode> getPostalCodes() {
        return this.postalCodes;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
