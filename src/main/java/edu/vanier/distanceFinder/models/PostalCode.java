/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.distanceFinder.models;

/**
 *
 * @author Mervin
 */
public class PostalCode {
    
    /**
     * Setting up the classes variables
     */
    private int id;
    private String country;
    private String postalCode;
    private String province;
    private String longitude;
    private String latitude;
    /**
     * Retrieves the id of a object
     * @return  id  
     */
    public int getId() {
        return this.id;
    }
    /**
     * Sets the id to a specified value
     * @param id specifies the id of the object
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Retrieves the country of an object
     * @return country
     */
    public String getCountry() {
        return this.country;
    }
    /**
     * Sets the country to a specific value
     * @param country specifies the country of the object
     */
    public void setCountry(String country) {
        this.country = country;
    }
    /**
     * Retrieves the postalCode of the object
     * @return postalCode
     */
    public String getPostalCode() {
        return this.postalCode;
    }
    /**
     * Sets the postalCode of an object to a specific value
     * @param postalCode the first 3 digits of a postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    /**
     * Retrieve the province of an object
     * @return province
     */
    public String getProvince() {
        return this.province;
    }
    /**
     * Sets the province of an object
     * @param province the province from which the postal code comes from
     */
    public void setProvince(String province) {
        this.province = province;
    }
    /**
     * Retrieve the longitudinal location of the postal code
     * @return longitude
     */
    public String getLongitude() {
        return this.longitude;
    }
    /**
     * Sets the longitudinal location of the postal code to a specific value
     * @param longitude the longitudinal location of the postal code
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    /**
     * Retrieve the latitudinal location of the postal code
     * @return latitude
     */
    public String getLatitude() {
        return latitude;
    }
    /**
     * Sets the latitudinal location of the postal code to a specific value
     * @param latitude the latitudinal location of the postal code
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    
    @Override
    public String toString(){
      return "Id:"+this.id +" country: "+this.country+" postal code: "+this.postalCode+" province: "+this.province+" longitude: "+this.longitude+" latitude: "+this.latitude+"\n";
    }
    
    
    
    
    
}
