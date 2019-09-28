package com.example.earthquakereports;

class word {
    private double magnitude;
    private String placeAndLandmark;
    private long timeInMilliseconds;

    word(double mag, String placeAndLandmark, long date){
        this.magnitude = mag;
        this.placeAndLandmark = placeAndLandmark;
        this.timeInMilliseconds = date;
    }

     double getMagnitude() {
        return magnitude;
    }
     String getPlaceAndLandmark() {
        return this.placeAndLandmark;
    }
     long getTimeInMilliseconds() {
        return this.timeInMilliseconds;
    }
}
