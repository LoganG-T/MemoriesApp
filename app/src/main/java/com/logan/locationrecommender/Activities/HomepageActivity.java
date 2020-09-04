package com.logan.locationrecommender.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.logan.locationrecommender.R;
import com.logan.locationrecommender.functions.SaveAndLoad;

public class HomepageActivity extends AppCompatActivity {

    final String MEMORY_FILE = "memories.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        SaveAndLoad save = new SaveAndLoad();
        if(! save.CheckFileExists(getApplicationContext(), MEMORY_FILE)){
            save.CreateNewFile(getApplicationContext(), MEMORY_FILE, "");
        }
    }

    public void ViewMemoriesButton(View view){
        Intent intent = new Intent(this, ViewMemoryActivity.class);

        startActivity(intent);
    }

    public void AddMemoryButton(View view){
        Intent intent = new Intent(this, NewMemoryActivity.class);

        startActivity(intent);
    }

    public void ViewFavouritesButton(View view){

    }

    public void SettingsButton(View view){

    }
}
