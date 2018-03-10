package com.example.ken.updish.Model;

/**
 * Created by tanthinh on 3/8/18.
 */

public class Location
{
    private String name;
    private String streetNumber;
    private String streetName;
    private String postalCode;
    private String city;
    private String province;

    /**
     * Empty Location - user hasn't set yet
     */
    public Location()
    {
        this.streetNumber = "";
        this.streetName = "";
        this.postalCode = "";
        this.city = "";
        this.province = "";
    }
    /**
     * Location constructor
     * @param name
     * @param streetNumber
     * @param streetName
     * @param postalCode
     * @param city
     * @param province
     */
    public Location(String name, String streetNumber, String streetName,
                    String postalCode, String city, String province)
    {
        this.name = name;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.postalCode = postalCode;
        this.city = city;
        this.province = province;
    }

    /**
     * Get full address from a location
     * @return String contains full address
     */
    public String retrieveAddress()
    {
        return streetNumber + " " + streetName + ", " + city + ", " + province + " " + postalCode;
    }

}
