package com.example.android.googlemap;

import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.io.IOException;


/**
 * Created by jw402 on 10/4/2017.
 */

public class GetDirectionsData extends AsyncTask<Object,String, String> {
    GoogleMap mMap;
    String url;
    String googleDirectionsData;
    String duration, distance;
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
        return googleDirectionsData;
    }

    @Override
    protected void onPostExecute(String s){
        String[] directionsList;
        DataParser parser = new DataParser();
        directionsList = parser.parseDirections(s);
        displayDirection(directionsList);
    }

    public void displayDirection(String[] directionsList){
        int count = directionsList.length;
        for(int i=0; i<count; i++){
            PolylineOptions pOptions = new PolylineOptions();
            pOptions.color(Color.RED);
            pOptions.width(10);
            pOptions.addAll(PolyUtil.decode(directionsList[i]));

            mMap.addPolyline(pOptions);

        }
    }
}
