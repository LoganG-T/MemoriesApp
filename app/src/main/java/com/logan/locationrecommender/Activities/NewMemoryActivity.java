package com.logan.locationrecommender.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.logan.locationrecommender.R;
import com.logan.locationrecommender.memories.Memory;
import com.logan.locationrecommender.memories.MemoryHandler;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class NewMemoryActivity extends AppCompatActivity {

    private LocationManager location_manager;
    private PopupWindow open_popout;
    private View open_popout_view;
    private Calendar memory_calendar;
    private Memory memory;

    private MemoryHandler memoryHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_memory);

        memoryHandler = new MemoryHandler();
        memoryHandler.LoadAllMemories(getApplicationContext());
        memoryHandler.PrintOut();

        memory_calendar = Calendar.getInstance();

        memory = new Memory();

        New_Current_Date();
    }

    // START Location functions

    public void New_Current_Location(View view) {
        location_manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //Check for app permissions if and ask for them if they are not granted
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("LOCATION DENIED ");
            int REQUEST_CODE = 1;
            requestPermissions(new String[] { Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.INTERNET }, REQUEST_CODE);
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
            String[] s = new String[2];
            s[0] = Double.toString(location.getLatitude());
            s[1] = Double.toString(location.getLongitude());
            try {
                //Get the current location from lat/long to name
                //Info on setting up google maps API ::
                //https://console.cloud.google.com/apis/credentials
                //https://www.tutorialspoint.com/how-to-show-current-location-on-a-google-map-on-android
                //https://developer.android.com/reference/android/location/Geocoders
                Geocoder geocoder = new Geocoder(getApplicationContext());
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                String cityName = addresses.get(0).getAddressLine(0);
                String stateName = addresses.get(0).getAddressLine(1);
                String countryName = addresses.get(0).getAddressLine(2);
                System.out.println("LOCATION " + cityName + " " + stateName + " " + countryName);
            } catch (IOException e) {
                e.printStackTrace();
            }

            memory.SetLocation(s);
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
                memory.SetDate(memory_calendar);
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
        memory.SetDate(memory_calendar);
    }

    //END Date functions

    //START Image functions

    public void New_Select_Image(View view){
        //https://stackoverflow.com/questions/10165302/dialog-to-pick-image-from-gallery-or-from-camera
        Intent picked_photo = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        picked_photo.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        //Request code is used to respond with onActivityResult, which is dealt with in the switch statement
        startActivityForResult(picked_photo , 1);
    }

    protected void onActivityResult(int request_code, int result_code, Intent image_intent) {
        super.onActivityResult(request_code, result_code, image_intent);
        switch(request_code) {
            //Photos selected from phone gallery
            case 1:
                if(result_code == RESULT_OK){
                    if(image_intent.getClipData() != null) {

                        int count = image_intent.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                        for (int i = 0; i < count; i++) {
                            Uri selected_image = image_intent.getClipData().getItemAt(i).getUri();
                            String image_path = GetPath(getApplicationContext( ), selected_image);
                            System.out.println("!!! " + image_path);
                            //TODO: Save the image into the memory
                            memory.AddImage(image_path);
                        }
                        //Load the first selected image into the text view on screen
                        ImageView img = (ImageView)findViewById(R.id.img_new_selected_img);
                        Picasso.with(getApplicationContext()).load(image_intent.getClipData().getItemAt(0).getUri()).into(img);
                    }
                    else if(image_intent.getData() != null){
                        Uri selected_image = image_intent.getData();
                        //TODO: Save the image into the memory
                    }
                }
                break;
        }
    }

    //https://stackoverflow.com/questions/20324155/get-filepath-and-filename-of-selected-gallery-image-in-android
    private String GetPath(Context context, Uri uri){
        String result = "";
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver( ).query( uri, proj, null, null, null );
        if(cursor != null){
            if ( cursor.moveToFirst( ) ) {
                int column_index = cursor.getColumnIndexOrThrow( proj[0] );
                result = cursor.getString( column_index );
            }
            cursor.close( );
        }
        return result;
    }

    //END Image functions

    public void New_Confirm_Button(View view){
        TextView txt_title = findViewById(R.id.edit_new_title);
        memory.SetTitle(txt_title.getText().toString());
        if(memory.GetTitle().equals("")){
            memory.SetTitle(memory.GetTextDate());
        }

        TextView txt_notes = findViewById(R.id.edit_new_note);
        memory.SetNotes(txt_notes.getText().toString());

        memoryHandler.AddNewMemory(memory);

        memoryHandler.SaveMemories(getApplicationContext());
        memoryHandler.PrintOut();

        // This will takes you back to the home page but the back button takes you back to this page
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
    }
}
