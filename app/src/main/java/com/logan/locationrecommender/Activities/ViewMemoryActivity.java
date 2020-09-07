package com.logan.locationrecommender.Activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.logan.locationrecommender.R;

public class ViewMemoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_memory);

        //The code below will create a new instance of the memory_view layout and add it to the total memory scroll view
        LinearLayout linear_layout = findViewById(R.id.linlay_view_all_memories);
        View new_layout = LayoutInflater.from(this).inflate(
                R.layout.memory_view_layout, null);
        linear_layout.addView(new_layout);


    }
}
