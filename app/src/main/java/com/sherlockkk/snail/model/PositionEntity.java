package com.sherlockkk.snail.model;

/**
 * ClassName:PositionEntity <br/>
 * Function: 封装的关于位置的实体 <br/>
 */
public class PositionEntity {

    public double latitue;

    public double longitude;

    public String address;

    public String city;

    public PositionEntity() {
    }

    public PositionEntity(double latitude, double longtitude, String address,
            String city) {
        this.latitue = latitude;
        this.longitude = longtitude;
        this.address = address;
    }

}
