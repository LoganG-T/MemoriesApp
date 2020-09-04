package com.logan.locationrecommender.memories;

import android.content.Context;

import com.logan.locationrecommender.functions.SaveAndLoad;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MemoryHandler {
    JSONArray json_all_dates;
    List<MemoryYear> all_dates;

    public void LoadMemories(Context context){
        SaveAndLoad save = new SaveAndLoad();
        try {
            json_all_dates = new JSONArray(save.Load(context, "memories.json"));
            CreateDates();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void CreateDates() throws JSONException {
        all_dates = new ArrayList<MemoryYear>();
        for(int i = 0; i < json_all_dates.length(); i++){
            all_dates.add(new MemoryYear(json_all_dates.getJSONObject(i)));
        }
    }
}
