package com.example.earthquakereports;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<word>> {

     private static final String USGS_REQUEST_URL =
             "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";

     private wordAdapter adapter;
     private ListView earthquakeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

         earthquakeListView = findViewById(R.id.list);
         adapter = new wordAdapter(this, new ArrayList<word>());
         earthquakeListView.setAdapter(adapter);

         earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                   word currentEarthquake = adapter.getItem(i);
                   Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());
                   Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                   startActivity(websiteIntent);
              }
         });

//         quakeAsyncTask task = new quakeAsyncTask();
//         task.execute(USGS_REQUEST_URL);
         getSupportLoaderManager().initLoader(1, null, this).forceLoad();
    }

     @NonNull
     @Override
     public Loader<List<word>> onCreateLoader(int id, @Nullable Bundle args) {
          return new EarthquakeLoader(MainActivity.this, USGS_REQUEST_URL);
     }

     @Override
     public void onLoadFinished(@NonNull Loader<List<word>> loader, List<word> data) {
          //show everything on UI
          adapter.clear();
              if (data != null){
                   adapter.addAll(data);
              }
     }

     @Override
     public void onLoaderReset(@NonNull Loader<List<word>> loader) {
          adapter.clear();
     }

//     private class quakeAsyncTask extends AsyncTask<String, Void, List<word>>{
//
//         @Override
//         protected List<word> doInBackground(String... urls) {
//              if(urls.length < 1 || urls[0] == null )
//                   return null;
//
//              return QueryUtils.fetchEarthquakeData(USGS_REQUEST_URL);
//         }
//
//         @Override
//         protected void onPostExecute(List<word> earthquakes){
//              adapter.clear();
//              if (earthquakes != null){
//                   adapter.addAll(earthquakes);
//              }
//
//         }
//    }

}
