package com.logan.locationrecommender.memories;

import android.net.Uri;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Memory {
    String title;
    //Lat, Lon
    String[] location;
    Calendar date;
    //Image URI for an image in gallery
    //imageView.setImageURI(selectedImage); to set image view as this image
    //For example usage https://zaidisoft.com/android-imageview-display-image-from-gallery-or-camera/#:~:text=Now%20we%20have%20both%20activity,stored%20on%20the%20phone%20gallery.
    List<Uri> images;
    String notes;

    //default constructor
    public Memory(){
        images = new ArrayList<Uri>();
    }

    //Copy constructor
    public Memory(Memory m){

    }


    public void SetTitle(String t){
        title = t;
    }
    public void SetLocation(String[] l){
        location = l;
    }
    public void SetDate(Calendar c){
        date = (Calendar) c.clone();
    }
    public void SetImage(List<Uri> i){
        images = i;
    }
    public void SetNotes(String n){
        notes = n;
    }
    public void AddImage(Uri i){
        images.add(i);
    }

}
