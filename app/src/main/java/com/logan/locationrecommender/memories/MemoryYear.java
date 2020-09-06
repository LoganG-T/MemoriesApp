package com.logan.locationrecommender.memories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MemoryYear {
    int year;
    MemoryMonth[] months;

    //START Constructors

    public MemoryYear(){

    }

    public MemoryYear(JSONObject j){
        try {
            year = j.getInt("year");
            months = new MemoryMonth[12];
            JSONArray json_months = j.getJSONArray("months");
            for(int i = 0; i < json_months.length(); i++){
                months[i] = new MemoryMonth(json_months.getJSONObject(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public MemoryYear(int i, Memory m){
        year = i;
        months = new MemoryMonth[12];
        for(int c = 0; c < 12; c++){
            if(c == m.GetMonth()){
                months[c] = new MemoryMonth(c, m);
            }else {
                months[c] = new MemoryMonth(c);
            }
        }
    }

    // Create an empty memory year object
    public MemoryYear(int i){
        year = i;
        months = new MemoryMonth[12];
        for(int c = 0; c < 12; c++){
            months[c] = new MemoryMonth(c);
        }
    }

    //END Constructors

    public int GetYear(){
        return year;
    }

    //Month is zero-indexed -> january is 0 december is 11
    public void AddToMonth(int month, Memory memory){
        months[month].AddNewMemory(memory);
    }

    public String MonthsToString(){
        String s = "\"months\":[";
        for(int c = 0; c < 12; c++){
            s += months[c].ToString();
            if(c + 1 < 12){
                s += ",";
            }
        }
        s += "]";
        return s;
    }

    public String ToString(){
        String s = "{\"year\":" + year;
        s += ",";
        s += MonthsToString();

        s += "}";
        return s;
    }

}
