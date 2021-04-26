package com.test.geodata.model;

public class LatLon {
    private double[] latLons = new double[2];

    public LatLon(double lat, double lon){
        this.latLons[0] = lat;
        this.latLons[1] = lon;
    }

    public double[] getLatLons(){return latLons;}
}
