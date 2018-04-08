package com.example.ken.updish.Model;

/**
 * Created by tanthinh on 3/8/18.
 */

public class Location
{
    private int id;
    private String name;
    private String address;
    private long longtitude;
    private long latitude;

    /**
     * Empty Location - user hasn't set yet
     */
    public Location()
    {
        this.id = 0;
        this.address = "";
        this.longtitude = 0;
        this.latitude = 0;
    }
    /**
     * Location constructor
     * @param name
     * @param address
     */
    public Location(int id, String name, String address, long lo, long la)
{
    this.id = id;
    this.name = name;
    this.address = address;
    this.longtitude = lo;
    this.latitude = la;
}

    /**
     * Get full address from a location
     * @return String contains full address
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
