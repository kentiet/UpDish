package com.example.ken.updish.Model;

/**
 * Created by tanthinh on 3/8/18.
 */

public class Location
{
    private int id;
    private String name;
    private String streetNumber;
    private String streetName;
    private String postalCode;
    private String city;
    private String province;
    private long longtitude;
    private long latitude;

    /**
     * Empty Location - user hasn't set yet
     */
    public Location()
    {
        this.id = 0;
        this.streetNumber = "";
        this.streetName = "";
        this.postalCode = "";
        this.city = "";
        this.province = "";
        this.longtitude = 0;
        this.latitude = 0;
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
    public Location(int id, String name, String streetNumber, String streetName,
                    String postalCode, String city, String province, long lo, long la)
    {
        this.id = id;
        this.name = name;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.postalCode = postalCode;
        this.city = city;
        this.province = province;
        this.longtitude = lo;
        this.latitude = la;
    }

    /**
     * Get full address from a location
     * @return String contains full address
     */
    public String retrieveAddress()
    {
        return streetNumber + " " + streetName + ", " + city + ", " + province + " " + postalCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(long longtitude) {
        this.longtitude = longtitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }
}
