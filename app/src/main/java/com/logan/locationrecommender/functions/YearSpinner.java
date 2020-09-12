package com.logan.locationrecommender.functions;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.logan.locationrecommender.Activities.ViewMemoryActivity;
import com.logan.locationrecommender.R;

import java.util.List;

public class YearSpinner extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public YearSpinner(ViewMemoryActivity vm, List<String> s){
        view_class = vm;
        years = s;
    }

    ViewMemoryActivity view_class;
    List<String> years;

    public void CreateSpinner(int r_id){
        view_class.SetSelectedMonth(0);
        Spinner spinner = (Spinner) view_class.findViewById(r_id);

        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view_class,
                android.R.layout.simple_spinner_item, years);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        view_class.SetSelectedYear(Integer.parseInt(years.get(position)));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
