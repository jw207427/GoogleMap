package com.example.android.googlemap;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by jw402 on 10/4/2017.
 */

public class DataParser {

    private HashMap<String, String> getDuration(JSONArray googleDirectionJson)
    {
        HashMap<String, String> googleDirectionMap = new HashMap<>();
        String duration = "";
        String distance = "";

        Log.d("json response", googleDirectionJson.toString());

        try {
            duration = googleDirectionJson.getJSONObject(0).getJSONObject("duration").getString("text");
            distance = googleDirectionJson.getJSONObject(0).getJSONObject("distance").getString("text");

            googleDirectionMap.put("duration", duration);
            googleDirectionMap.put("distance", distance);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return googleDirectionMap;
    }
    private HashMap<String, String> getPlace(JSONObject googlePlaceJason){
        HashMap<String, String> googlePlaceMap = new HashMap<>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";

        try{
            if(!googlePlaceJason.isNull("name"))
            {
                placeName = googlePlaceJason.getString("name");
            }
            if(!googlePlaceJason.isNull("vicinity")){
                vicinity = googlePlaceJason.getString("vicinity");
            }
            latitude = googlePlaceJason.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude= googlePlaceJason.getJSONObject("geometry").getJSONObject("location").getString("lng");

            reference = googlePlaceJason.getString("reference");

            googlePlaceMap.put("place_name", placeName);
            googlePlaceMap.put("vicinity", vicinity);
            googlePlaceMap.put("lat", latitude);
            googlePlaceMap.put("lng", longitude);
            googlePlaceMap.put("reference", reference);
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return googlePlaceMap;
    }

    public HashMap<String, String> parseDirections(String jsonData)
    {
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try{
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("leg"); //legs array
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getDuration(jsonArray);
    }
}
