package com.example.earthquakereports;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class wordAdapter extends ArrayAdapter<word> {
    wordAdapter(Context context, ArrayList<word> words){
        super(context, 0, words);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        word word = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        TextView mag = convertView.findViewById(R.id.magnitude);
        DecimalFormat formatter = new DecimalFormat("0.0");
        String output = formatter.format(word.getMagnitude());
        mag.setText(output);

        //set the proper background color on the magnitude circle
        //Fetch the background from the textview, which is GradientDrawable
        GradientDrawable magnitudeCircle = (GradientDrawable) mag.getBackground();

        //Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(word.getMagnitude());

        //set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        TextView place = convertView.findViewById(R.id.location_offset);
        TextView landmark = convertView.findViewById(R.id.primary_location);
        String placeAndLandmark = word.getPlaceAndLandmark();
        String[] placeAndLandmarkSeparated = placeAndLandmark.split("of");
        if(placeAndLandmarkSeparated.length > 1) {
            place.setText(placeAndLandmarkSeparated[0] + " of");
            landmark.setText(placeAndLandmarkSeparated[1]);
        }
        else {
            place.setText("near the");
            landmark.setText(placeAndLandmarkSeparated[0]);
        }


        Date dateObject = new Date(word.getTimeInMilliseconds());

        TextView dateView = convertView.findViewById(R.id.date);
        String formattedDate = formatDate(dateObject);
        dateView.setText(formattedDate);

        TextView timeView = convertView.findViewById(R.id.time);
        String formattedTime = formatTime(dateObject);
        timeView.setText(formattedTime);

        return convertView;
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        return timeFormat.format(dateObject);
    }

    private int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceId;
        int magFloor = (int)Math.floor(magnitude);
        switch (magFloor){
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;

        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
