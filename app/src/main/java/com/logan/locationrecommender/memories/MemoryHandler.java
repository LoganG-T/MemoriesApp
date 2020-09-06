package com.logan.locationrecommender.memories;

import android.content.Context;

import com.logan.locationrecommender.functions.SaveAndLoad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MemoryHandler {
    JSONObject json_all_dates;
    JSONArray json_array;
    List<MemoryYear> all_dates;

    public void LoadAllMemories(Context context){
        SaveAndLoad save = new SaveAndLoad(context);
        try {
            String s = save.Load("memories.json");
            if(s.equals("{\"all_dates\":[]}")){
                // This is the empty JSONObject and requires data to be input or errors are everywhere
                CreateEmptyYear();
            }else{
                json_all_dates = new JSONObject(s);
                json_array = json_all_dates.getJSONArray("all_dates");
                CreateDates();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void LoadYearMemories(Context context, int year){
        SaveAndLoad save = new SaveAndLoad(context);
        try {
            json_all_dates = new JSONObject(save.Load("memories.json"));
            json_array = json_all_dates.getJSONArray("all_dates");
            CreateDates(year);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void CreateDates() throws JSONException {
        all_dates = new ArrayList<MemoryYear>();
        for(int i = 0; i < json_array.length(); i++){
            all_dates.add(new MemoryYear(json_array.getJSONObject(i)));
        }
    }
    public void CreateDates(int year) throws JSONException {
        all_dates = new ArrayList<MemoryYear>();
        for(int i = 0; i < json_array.length(); i++){
            if(json_array.getJSONObject(i).getInt("year") == year){
                all_dates.add(new MemoryYear(json_array.getJSONObject(i)));
            }
        }
    }

    private void CreateEmptyYear(){
        all_dates = new ArrayList<>();
        all_dates.add(new MemoryYear(Calendar.getInstance().get(Calendar.YEAR)));

    }

    public void SaveMemories(Context context){
        SaveAndLoad save = new SaveAndLoad(context);
        save.Save(ToString());

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
        System.out.println("CREATE NEW YEAR DATA");
        // Create a new year for the memory data and add the first memory to it
        CreateNewYearData(memory.GetYear(), memory);
    }

    public void CreateNewYearData(int year, Memory memory){
        all_dates.add(new MemoryYear(year, memory));
    }


    //Returns the JSONArray of the requested year or returns null if it does not exist
    public JSONArray GetYear(int year) throws JSONException {
        for(int i = 0; i < json_array.length(); i++){
            if(json_array.getJSONObject(i).getInt("year") == year){
                return json_array.getJSONObject(i).getJSONArray("months");
            }
        }
        return null;
    }

    public void PrintOut(){
        System.out.println(all_dates.get(0).ToString());
    }

    public String ToString(){
        String s = "{\"all_dates\":[";
        for(int c = 0; c < all_dates.size(); c++){
            s += all_dates.get(c).ToString();
            if(c + 1 < all_dates.size()){
                s += ",";
            }
        }
        s += "]}";
        return s;
    }
}
