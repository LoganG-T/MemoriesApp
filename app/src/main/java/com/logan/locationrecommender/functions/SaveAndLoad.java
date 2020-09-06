package com.logan.locationrecommender.functions;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SaveAndLoad {

    private Context context;

    public SaveAndLoad(Context c){
        context = c;
    }

    public boolean Save(String save_string){
        String FILENAME = "memories.json";
        //String jsonString = jsonHandler.get_jsonString();

        try {
            JSONObject jsonObj = new JSONObject(save_string);

            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(jsonObj.toString().getBytes());
            fos.close();

            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String Load(String file_name){
        try {
            FileInputStream fis = context.openFileInput(file_name);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException fileNotFound) {
            return null;
        } catch (IOException ioException) {
            return null;
        }
    }

    //https://stackoverflow.com/questions/40168601/android-how-to-save-json-data-in-a-file-and-retrieve-it
    public boolean CheckFileExists(String file_name){
        String path = context.getFilesDir().getAbsolutePath() + "/" + file_name;
        File file = new File(path);
        return file.exists();
    }

    public boolean CreateNewFile(String file_name, String json_data){

        //Example json string { "all_dates" :[{"year":2020,"months":[{"month":"january", "data":[]]}, {"year":2019,"months":[]}] }
        String default_json = "{\"all_dates\":[]}";
        try {
            FileOutputStream fos = context.openFileOutput(file_name, Context.MODE_PRIVATE);
            if (json_data != null) {
                if(json_data == "" || json_data == "{}"){
                    fos.write(default_json.getBytes());
                }else {
                    fos.write(json_data.getBytes());
                }
            }
            fos.close();
            return true;
        }catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void DeleteFile(String s){
        context.deleteFile(s);
    }
}
