package com.logan.locationrecommender.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.logan.locationrecommender.R;
import com.logan.locationrecommender.functions.SaveAndLoad;
import com.logan.locationrecommender.memories.MemoryHandler;

public class HomepageActivity extends AppCompatActivity {

    final String MEMORY_FILE = "memories.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        SaveAndLoad save = new SaveAndLoad(getApplicationContext());
        if(! save.CheckFileExists(MEMORY_FILE)){
            System.out.println("File does not exist");
            save.CreateNewFile(MEMORY_FILE, "");
        }else{
            MemoryHandler memoryHandler = new MemoryHandler();
            memoryHandler.LoadAllMemories(getApplicationContext());
            memoryHandler.PrintOut();
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
        Intent intent = new Intent(this, SettingsActivity.class);

        startActivity(intent);
    }
}
