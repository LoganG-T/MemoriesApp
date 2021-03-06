package com.logan.locationrecommender.functions;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.logan.locationrecommender.Activities.ViewMemoryActivity;
import com.logan.locationrecommender.R;


public class MonthSpinner extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public MonthSpinner(ViewMemoryActivity vm){
        view_class = vm;
    }

    ViewMemoryActivity view_class;

    public void CreateSpinner(int r_id, int month){
        view_class.SetSelectedMonth(0);
        Spinner spinner = (Spinner) view_class.findViewById(r_id);

        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view_class,
                R.array.months_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setSelection(month);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        view_class.SetSelectedMonth(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
