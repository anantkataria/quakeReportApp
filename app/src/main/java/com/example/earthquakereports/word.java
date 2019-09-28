package com.example.earthquakereports;

class word {
    private double magnitude;
    private String placeAndLandmark;
    private long timeInMilliseconds;
    private String url;

     word(double mag, String placeAndLandmark, long date, String url){
          this.magnitude = mag;
          this.placeAndLandmark = placeAndLandmark;
          this.timeInMilliseconds = date;
          this.url = url;
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

     public String getUrl() {
         return this.url;
     }
}
