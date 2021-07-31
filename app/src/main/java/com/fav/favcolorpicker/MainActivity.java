package com.fav.favcolorpicker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.fav.coloryfy.FavColors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FavColors favColors = new FavColors(
                MainActivity.this, // Context
                255, // Default Alpha value
                255, // Default Red value
                212, // Default Green value
                67 // Default Blue value
        );
        setContentView(R.layout.activity_main);


        //showing color picker
        favColors.show();



    }
}