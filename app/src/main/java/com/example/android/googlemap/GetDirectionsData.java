package com.example.android.googlemap;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;


/**
 * Created by jw402 on 10/4/2017.
 */

public class GetDirectionsData extends AsyncTask<Object,String, String> {
    GoogleMap mMap;
    String url;
    String googleDirectionsData;
    String duration;
    String distance;
    LatLng latLng;

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap)objects[0];
        url = (String)objects[1];
        latLng = (LatLng) objects[2];

        DownloadUrl downloadUrl = new DownloadUrl();
        try{
            googleDirectionsData = downloadUrl.readUrl(url);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //DownloadUrl downloadUrl = new DownloadUrl();
        return googleDirectionsData;
    }

    @Override
    protected void onPostExecute(String s){
        HashMap<String, String> directionList = null;
        DataParser dataParser = new DataParser();
        directionList = dataParser.parseDirections(s);
        duration = directionList.get("duration");
        distance = directionList.get("distance");

        mMap.clear();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.draggable(true);
        markerOptions.title("Duration = "+ duration);
        markerOptions.snippet("Distance = " + distance);

        mMap.addMarker(markerOptions);
    }
}
