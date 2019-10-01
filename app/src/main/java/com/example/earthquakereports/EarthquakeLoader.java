package com.example.earthquakereports;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<word>> {

     private String USGS_REQUEST_URL;

     EarthquakeLoader(Context context, String url){
          super(context);
          this.USGS_REQUEST_URL = url;
     }

     @Override
     protected void onStartLoading() {
          forceLoad();
     }

     @Nullable
     @Override
     public List<word> loadInBackground() {
          if (USGS_REQUEST_URL == null)
               return null;
          return QueryUtils.fetchEarthquakeData(USGS_REQUEST_URL);
     }
}
