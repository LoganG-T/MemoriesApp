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

    public void LoadAllMemories(Context context){
        SaveAndLoad save = new SaveAndLoad();
        try {
            json_all_dates = new JSONArray(save.Load(context, "memories.json"));
            CreateDates();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void LoadYearMemories(Context context, int year){
        SaveAndLoad save = new SaveAndLoad();
        try {
            json_all_dates = new JSONArray(save.Load(context, "memories.json"));
            CreateDates(year);
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
    public void CreateDates(int year) throws JSONException {
        all_dates = new ArrayList<MemoryYear>();
        for(int i = 0; i < json_all_dates.length(); i++){
            if(json_all_dates.getJSONObject(i).getInt("year") == year){
                all_dates.add(new MemoryYear(json_all_dates.getJSONObject(i)));
            }
        }
    }

    // Memory is a JSONObject of -> string title, jsonaray location, jsonarray date, jsonarray images, string notes
    public void AddNewMemory(Memory memory){
        if(all_dates == null){
            all_dates = new ArrayList<>();
        }
        for(int i = 0; i < all_dates.size(); i++){
            if(all_dates.get(i).GetYear() == memory.GetYear()){
                all_dates.get(i).AddToMonth(memory.GetMonth(), memory);
                return;
            }
        }
        // Create a new year for the memory data and add the first memory to it
        CreateNewYearData(memory.GetYear(), memory);
    }

    public void CreateNewYearData(int year, Memory memory){
        all_dates.add(new MemoryYear(year, memory));
    }


    //Returns the JSONArray of the requested year or returns null if it does not exist
    public JSONArray GetYear(int year) throws JSONException {
        for(int i = 0; i < json_all_dates.length(); i++){
            if(json_all_dates.getJSONObject(i).getInt("year") == year){
                return json_all_dates.getJSONObject(i).getJSONArray("months");
            }
        }
        return null;
    }

    public void PrintOut(){
        System.out.println(all_dates.get(0).ToString());
    }
}
