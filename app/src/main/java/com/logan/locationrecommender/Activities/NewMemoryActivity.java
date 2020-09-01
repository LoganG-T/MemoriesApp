package com.logan.locationrecommender.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Debug;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.logan.locationrecommender.R;

import java.util.Calendar;

public class NewMemoryActivity extends AppCompatActivity {

    private LocationManager location_manager;
    private PopupWindow open_popout;
    private View open_popout_view;
    private Calendar memory_calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_memory);
        memory_calendar = Calendar.getInstance();

        New_Current_Date();
    }

    // START Location functions

    public void New_Current_Location(View view) {
        location_manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //Check for app permissions if and ask for them if they are not granted
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("LOCATION DENIED ");
            int REQUEST_CODE = 1;
            requestPermissions(new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, REQUEST_CODE);
            requestPermissions(new String[] { Manifest.permission.ACCESS_COARSE_LOCATION }, REQUEST_CODE);
            requestPermissions(new String[] { Manifest.permission.INTERNET }, REQUEST_CODE);
            return;
        }
        location_manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
    }

    public void New_Select_Location(View view) {
        //This requires google maps API for the user to select a location on a map
        //TODO: https://developers.google.com/maps/documentation/android-sdk/current-place-tutorial
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            System.out.println("LOCATION CHANGED " + location.getLatitude() + " " + location.getLongitude());
            location_manager.removeUpdates(mLocationListener);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    // END Location functions

    //START Date functions

    public void New_Start_Select_Date(View view){
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        open_popout_view = inflater.inflate(R.layout.popout_calendar, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popup_window = new PopupWindow(open_popout_view, width, height, focusable);

        // show the popup window
        popup_window.showAtLocation(view, Gravity.CENTER, 0, 0);
        open_popout = popup_window;

        Button b = open_popout_view.findViewById(R.id.popout_calendar_confirm);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                New_End_Select_Date(v);
            }
        });
        CalendarView c = open_popout_view.findViewById(R.id.popout_calendar);
        c.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                memory_calendar.set(year, month, dayOfMonth);
            }
        });

        // dismiss the popup window when touched
        open_popout_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popup_window.dismiss();
                return true;
            }
        });
    }

    public void New_End_Select_Date(View view) {
        if(open_popout != null) {
            TextView date_display = (TextView)findViewById(R.id.txt_new_display_date);
            date_display.setText(memory_calendar.get(Calendar.DAY_OF_MONTH) + "/" + (memory_calendar.get(Calendar.MONTH) + 1) + "/" + memory_calendar.get(Calendar.YEAR));

            open_popout.dismiss();
            open_popout = null;
            open_popout_view = null;
        }
    }

    private void New_Current_Date(){
        TextView date_display = (TextView)findViewById(R.id.txt_new_display_date);
        memory_calendar = Calendar.getInstance();
        date_display.setText(memory_calendar.get(Calendar.DAY_OF_MONTH) + "/" + (memory_calendar.get(Calendar.MONTH) + 1) + "/" + memory_calendar.get(Calendar.YEAR));
    }

    //END Date functions

    public void New_Select_Image(View view){

    }

    public void New_Confirm_Button(View view){

    }
}
