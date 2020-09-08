package com.logan.locationrecommender.Activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.logan.locationrecommender.R;
import com.logan.locationrecommender.memories.Memory;
import com.logan.locationrecommender.memories.MemoryHandler;

import java.util.Calendar;
import java.util.List;

public class ViewMemoryActivity extends AppCompatActivity {

    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_memory);
        calendar = Calendar.getInstance();

        LoadMonthMemories();
    }

    private void LoadMonthMemories(){
        int year = calendar.get(Calendar.YEAR);

        MemoryHandler memory_handler = new MemoryHandler();
        boolean x = memory_handler.LoadYearMemories(getApplicationContext(), year);
        if(x) {
            int month = calendar.get(Calendar.MONTH);
            List<Memory> memories = memory_handler.GetMonthMemories(year, month);
            if(memories == null){
                //The year does not exist in the save file
                return;
            }

            for (int i = 0; i < memories.size(); i++){
                CreateViewLayout(memories.get(i));
            }
        }
    }

    private void CreateViewLayout(Memory m){
        //The code below will create a new instance of the memory_view layout and add it to the total memory scroll view
        LinearLayout linear_layout = findViewById(R.id.linlay_view_all_memories);
        View new_layout = LayoutInflater.from(this).inflate(
                R.layout.memory_view_layout, null);
        linear_layout.addView(new_layout);

        LinearLayout hor_lay = new_layout.findViewById(R.id.linlay_view_layout);

        // Update the template layout with the data from the given memory
        for(int i = 0; i < hor_lay.getChildCount(); i++){
            if(hor_lay.getChildAt(i).getTag() == null){
                continue;
            }
            if(hor_lay.getChildAt(i).getTag().equals("title")){
                TextView t = (TextView)hor_lay.getChildAt(i);
                t.setText(m.GetTitle());
            }else if(hor_lay.getChildAt(i).getTag().equals("date")){
                TextView t = (TextView)hor_lay.getChildAt(i);
                t.setText(m.GetTextDate());
            }
        }
    }
}
