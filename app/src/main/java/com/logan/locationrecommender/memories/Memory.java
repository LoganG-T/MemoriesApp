package com.logan.locationrecommender.memories;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Memory {
    String title;
    //Lat, Lon
    String[] location;
    Calendar date;
    //Image URI for an image in gallery
    //imageView.setImageURI(selectedImage); to set image view as this image
    //For example usage https://zaidisoft.com/android-imageview-display-image-from-gallery-or-camera/#:~:text=Now%20we%20have%20both%20activity,stored%20on%20the%20phone%20gallery.
    List<String> images;
    String notes;

    //START Constructors

    //default constructor
    public Memory(){
        images = new ArrayList<String>();
        location = new String[2];
        location[0] = "0";
        location[1] = "0";
    }

    //Copy constructor
    public Memory(Memory m){

    }

    public Memory(JSONObject j){
        try {
            title = j.getString("title");
            location = new String[2];
            location[0] = j.getJSONArray("location").getString(0);
            location[1] = j.getJSONArray("location").getString(1);

            images = new ArrayList<String>();
            JSONArray json_img = j.getJSONArray("images");
            for(int i = 0; i < json_img.length(); i++){
                System.out.println(json_img.getString(i));
                String s = json_img.getString(i);
                images.add(json_img.getString(i));
            }

            date = Calendar.getInstance();
            JSONArray cal = j.getJSONArray("date");
            int year = cal.getInt(0);
            int month = cal.getInt(1);
            int day = cal.getInt(2);
            date.set(year, month, day);

            notes = j.getString("notes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //END Constructors


    public void SetTitle(String t){
        title = t;
    }
    public void SetLocation(String[] l){
        location = l;
    }
    public void SetDate(Calendar c){
        date = (Calendar) c.clone();
    }
    public void SetImage(List<String> i){
        images = i;
    }
    public void SetNotes(String n){
        notes = n;
    }
    public void AddImage(String i){
        images.add(i);
    }

    public String GetTextDate(){
        String s = "";
        s += date.get(Calendar.DAY_OF_MONTH);
        s += "/";
        s += date.get(Calendar.MONTH);
        s += "/";
        s += date.get(Calendar.YEAR);

        return s;
    }
    public int GetYear(){
        return date.get(Calendar.YEAR);
    }
    public int GetMonth(){
        return date.get(Calendar.MONTH);
    }
    public String GetTitle(){
        return title;
    }
    public String GetNotes(){
        return notes;
    }
    public String GetFirstImage(){
        return images.get(0);
    }
    public String GetLocationString(){
        String s = "";
        s += location[0];
        s += ",";
        s += location[1];
        if(s.equals("0,0")){
            return "";
        }
        return s;
    }

    public boolean IsImages(){
        return images.size() > 0;
    }

    public JSONArray GetJsonDate(){
        JSONArray return_json = new JSONArray();

        return_json.put(date.get(Calendar.YEAR));
        return_json.put(date.get(Calendar.MONTH));
        return_json.put(date.get(Calendar.DAY_OF_MONTH));

        return return_json;
    }

    public JSONArray GetJsonImages(){
        JSONArray return_json = new JSONArray();
        for(int i = 0; i < images.size(); i++){
            return_json.put(images.get(i));
        }

        return return_json;
    }

    public JSONObject GetJsonMemory() throws JSONException {
        JSONObject return_json = new JSONObject();
        return_json.put("title", title);
        if(location != null){
            return_json.put("location", new JSONArray(location));
        }
        else{
            return_json.put("location", new JSONArray());
        }
        return_json.put("date", GetJsonDate());
        if(images != null && images.size() > 0) {
            return_json.put("images", GetJsonImages());
        }else{
            return_json.put("images", new JSONArray());
        }
        return_json.put("notes", notes);


        return return_json;
    }

    public String ToString(){
        String s = "{}";
        try {
            s = GetJsonMemory().toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return s;
    }
}
