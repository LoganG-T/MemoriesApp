package com.logan.locationrecommender.memories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MemoryYear {
    int year;
    List<MemoryMonth> months;

    public MemoryYear(){

    }

    public MemoryYear(JSONObject j){
        try {
            year = j.getInt("year");
            months = new ArrayList<MemoryMonth>();
            JSONArray json_months = j.getJSONArray("months");
            for(int i = 0; i < json_months.length(); i++){
                months.add(new MemoryMonth(json_months.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
