package com.test.geodata.model;

import java.util.List;

import java.util.ArrayList;

public class GeoData {
    private double lat;
    private double lon;
    private String displayName;
    private List<LatLon> coordinatesList = new ArrayList<>();
    private double[][] coordinates;

    public GeoData(double lat, double lon, String displayName){
        this.lat = lat;
        this.lon = lon;
        this.displayName = displayName;
    }

    public void addLatLon(LatLon latLon){coordinatesList.add(latLon);}

    public double getLat(){return lat;}

    public double getLon() {
        return lon;
    }

    public String getDisplayName() {return displayName;}

    public double[][] getCoordinates() {
        double[][] array = new double[coordinatesList.size()][];
        for (int i = 0; i<array.length; i++){
            LatLon latLon = coordinatesList.get(i);
            array[i] = latLon.getLatLons();
        }
        return array;
    }
}
