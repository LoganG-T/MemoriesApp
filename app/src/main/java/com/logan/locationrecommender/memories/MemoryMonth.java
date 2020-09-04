package com.logan.locationrecommender.memories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MemoryMonth {
    String month;
    List<Memory> memories;

    public MemoryMonth(JSONObject j){
        try {
            month = j.getString("month");
            memories = new ArrayList<Memory>();
            JSONArray json_memories = j.getJSONArray("months");
            for(int i = 0; i < json_memories.length(); i++){
                memories.add(new Memory(json_memories.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
