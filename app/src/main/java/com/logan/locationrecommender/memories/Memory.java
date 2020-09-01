package com.logan.locationrecommender.memories;

import android.net.Uri;

import java.util.Calendar;

public class Memory {
    String title;
    //Lat, Lon
    String[] location;
    Calendar date;
    //Image URI for an image in gallery
    //imageView.setImageURI(selectedImage); to set image view as this image
    //For example usage https://zaidisoft.com/android-imageview-display-image-from-gallery-or-camera/#:~:text=Now%20we%20have%20both%20activity,stored%20on%20the%20phone%20gallery.
    Uri image;
    String notes;
}
