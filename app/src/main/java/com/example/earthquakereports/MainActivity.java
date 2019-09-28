package com.example.earthquakereports;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

     private static final String USGS_REQUEST_URL =
             "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

//        //fake list of earthquake locations
//        ArrayList<word> earthquakes = new ArrayList<>();
//        earthquakes.add(new word("7.2", "San Francisco", "Feb 2, 2016"));
//        earthquakes.add(new word("7.2", "San Francisco", "Feb 2, 2016"));
//        earthquakes.add(new word("7.2", "San Francisco", "Feb 2, 2016"));
//        earthquakes.add(new word("7.2", "San Francisco", "Feb 2, 2016"));
//        earthquakes.add(new word("7.2", "San Francisco", "Feb 2, 2016"));
//        earthquakes.add(new word("7.2", "San Francisco", "Feb 2, 2016"));
//        earthquakes.add(new word("7.2", "San Francisco", "Feb 2, 2016"));

//         ArrayList<word> earthquakes = QueryUtils.extractEarthquakes();
//
//         //a reference to the listview in the layout
//         ListView earthquakeListView = findViewById(R.id.list);
//
//         //create a new ArrayAdapter of earthquakes
////        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, earthquakes);
//         wordAdapter adapter = new wordAdapter(this, earthquakes);
//
//         //set the adapter on the listview
//         //so the list can be populated in the user interface
//         earthquakeListView.setAdapter(adapter);

         quakeAsyncTask task = new quakeAsyncTask();
         task.execute(USGS_REQUEST_URL);
    }

    private class quakeAsyncTask extends AsyncTask<String, Void, ArrayList<word>>{

         @Override
         protected ArrayList<word> doInBackground(String... urls) {
              if(urls.length < 1 || urls[0] == null )
                   return null;

              return QueryUtils.fetchEarthquakeData(USGS_REQUEST_URL);
         }

         @Override
         protected void onPostExecute(ArrayList<word> earthquakes){
              if (earthquakes == null) {
                   return;
              }

              updateUi(earthquakes);
         }
    }

    private void updateUi(ArrayList<word> earthquakes){
         ListView earthquakeListView = findViewById(R.id.list);
         wordAdapter adapter = new wordAdapter(this, earthquakes);
         earthquakeListView.setAdapter(adapter);
    }

}
