package com.logan.locationrecommender.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.logan.locationrecommender.R;
import com.logan.locationrecommender.memories.Memory;
import com.logan.locationrecommender.memories.MemoryHandler;
import com.squareup.picasso.Picasso;

import java.io.File;
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

    private void CreateViewLayout(final Memory m){
        //The code below will create a new instance of the memory_view layout and add it to the total memory scroll view
        LinearLayout linear_layout = findViewById(R.id.linlay_view_all_memories);
        View new_layout = LayoutInflater.from(this).inflate(
                R.layout.memory_view_layout, null);
        linear_layout.addView(new_layout);

        final LinearLayout ver_lay = new_layout.findViewById(R.id.linlay_view_layout_holder);
        final LinearLayout hor_lay = new_layout.findViewById(R.id.linlay_view_layout);

        // Update the template layout with the data from the given memory
        for(int i = 0; i < hor_lay.getChildCount(); i++){
            if(hor_lay.getChildAt(i).getTag() == null){
                continue;
            }
            if(hor_lay.getChildAt(i).getTag().equals("title")){
                TextView t = (TextView)hor_lay.getChildAt(i);
                t.setText(m.GetTitle());
            }
            else if(hor_lay.getChildAt(i).getTag().equals("image")){
                if(m.IsImages()) {
                    System.out.println("Loaded image");
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        int REQUEST_CODE = 7;
                        requestPermissions(new String[] { Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
                    }else {
                        ImageView t = (ImageView) hor_lay.getChildAt(i);
                        String s = m.GetFirstImage();
                        System.out.println("FILE PATH STRING " + s + " | " + m.GetFirstImage());
                        System.out.println("FILE PATH SNG " + Environment.getExternalStorageDirectory());
                        File f = new File(s);
                        Uri uri = Uri.fromFile(f);
                        Picasso.with(getApplicationContext()).load(uri).into(t);
                        //Picasso.with(getApplicationContext()).load("file://"+s).into(t);
                        //Picasso.with(getApplicationContext()).load(f).into(t);
                        //t.setImageURI(uri);
                    }
                }else{
                    ImageView t = (ImageView) hor_lay.getChildAt(i);
                    t.setVisibility(View.INVISIBLE);
                }
            }
            else if(hor_lay.getChildAt(i).getTag().equals("date")){
                TextView t = (TextView)hor_lay.getChildAt(i);
                t.setText(m.GetTextDate());
            }
            else if(hor_lay.getChildAt(i).getTag().equals("button")){
                Button b = (Button)hor_lay.getChildAt(i);
                //Only add the view button if there is additional information to view else remove it
                if(!m.GetLocationString().equals("") && !m.GetLocationString().equals("")) {

                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DisplayFullMemory(v, ver_lay, m);
                        }
                    });
                }
                else{
                    hor_lay.removeView(b);
                }

            }
        }
    }

    private void DisplayFullMemory(View view, LinearLayout ver_lay, Memory m){
        View new_layout = LayoutInflater.from(this).inflate(
                R.layout.memory_view_layout_extention, null);
        ver_lay.addView(new_layout);
        LinearLayout lay_display = new_layout.findViewById(R.id.linlay_view_layout);

        //Update the new layout to display this memories additional details
        for(int i = 0; i < lay_display.getChildCount(); i++){
            //To check for children without tags and prevent a crash
            if(lay_display.getChildAt(i).getTag() == null){
                continue;
            }

            if(lay_display.getChildAt(i).getTag().equals("notes")){
                TextView t = (TextView)lay_display.getChildAt(i);
                t.setText(m.GetNotes());
            }
            else if(lay_display.getChildAt(i).getTag().equals("location")){
                TextView t = (TextView)lay_display.getChildAt(i);
                t.setText(m.GetLocationString());
            }
        }
    }





    // IMAGE URI INTENT
    protected void onActivityResult(int request_code, int result_code, Intent image_intent) {
        super.onActivityResult(request_code, result_code, image_intent);
        switch(request_code) {
            //Photos selected from phone gallery
            case 1:
                if(result_code == RESULT_OK){
                    System.out.println("!!! " + image_intent.getExtras().size());
                }
                break;
        }
    }
}
