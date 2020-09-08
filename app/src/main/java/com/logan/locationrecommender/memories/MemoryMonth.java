package com.logan.locationrecommender.memories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MemoryMonth {
    String month;
    List<Memory> memories;

    //START Constructors

    public MemoryMonth(JSONObject j){
        try {
            month = j.getString("month");
            memories = new ArrayList<Memory>();
            JSONArray json_memories = j.getJSONArray("memories");
            for(int i = 0; i < json_memories.length(); i++){
                memories.add(new Memory(json_memories.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public MemoryMonth(int m){
        SetMonth(m);
        memories = new ArrayList<Memory>();
    }

    public MemoryMonth(int m, Memory memory){
        SetMonth(m);
        memories = new ArrayList<Memory>();
        memories.add(memory);
    }

    //END Constructors

    public void AddNewMemory(Memory m) {
        memories.add(m);
    }

    public int MemoryCount(){
        return memories.size();
    }

    public List<Memory> GetMemories(){
        return memories;
    }

    private void SetMonth(int m){
        switch (m){
            case 0:
                month = "January";
                break;
            case 1:
                month = "February";
                break;
            case 2:
                month = "March";
                break;
            case 3:
                month = "April";
                break;
            case 4:
                month = "May";
                break;
            case 5:
                month = "June";
                break;
            case 6:
                month = "July";
                break;
            case 7:
                month = "August";
                break;
            case 8:
                month = "September";
                break;
            case 9:
                month = "October";
                break;
            case 10:
                month = "November";
                break;
            default:
                month = "December";
                break;
        }
    }

    private String MemoriesToString(){
        String s = "\"memories\":[";
        if(memories != null && memories.size() > 0) {
            for (int i = 0; i < memories.size(); i++) {
                s += memories.get(i).ToString();
                if(i + 1 < memories.size()){
                    s += ",";
                }
            }
        }
        s += "]";
        return s;
    }

    public String ToString(){
        String s = "{\"month\":\"" + month + "\"";
        s += ",";
        s += MemoriesToString();

        s += "}";
        return s;
    }
}
