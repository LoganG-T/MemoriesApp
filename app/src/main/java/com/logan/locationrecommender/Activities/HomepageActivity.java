package com.logan.locationrecommender.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.logan.locationrecommender.R;

public class HomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
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
