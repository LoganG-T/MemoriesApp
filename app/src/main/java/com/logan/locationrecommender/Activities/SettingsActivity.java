package com.logan.locationrecommender.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.logan.locationrecommender.R;
import com.logan.locationrecommender.functions.SaveAndLoad;

public class SettingsActivity extends AppCompatActivity {

    final String MEMORY_FILE = "memories.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

    }


    public void Settings_Delete_All_Data(View view){
        SaveAndLoad save = new SaveAndLoad(getApplicationContext());
        if(! save.CheckFileExists(MEMORY_FILE)){
            // Display popout saying the file does not exist
        }else{
            // Display a popout that asks for confirmation

            //Delete the file if confirmation accepted
            save.DeleteFile(MEMORY_FILE);
            //Create new file after deletion? -> remove this later and put it somewhere else
            save.CreateNewFile(MEMORY_FILE, "");
        }
    }
}
