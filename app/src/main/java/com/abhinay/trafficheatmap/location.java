package com.abhinay.trafficheatmap;

public class location {
    private String name;
    private double longitude;
    private double latitude;
    private double intensity;

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    public String getName() {
        return name;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getIntensity() {
        return intensity;
    }
    public location(String name,double latitude,double longitude,double intensity)
    {
        this.name=name;
        this.latitude=latitude;
        this.longitude=longitude;
        this.intensity=intensity;
    }
}
