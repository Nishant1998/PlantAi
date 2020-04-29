package com.example.plantai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

public class ImageSlider extends AppCompatActivity {

    int id;
    ViewPager viewPager;
    ImageView imageView;

    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);

        viewPager = findViewById(R.id.viewPager);
        imageView = findViewById(R.id.userImageViewSlider);
        id = getIntent().getIntExtra("id",0);
        try {
            path = getIntent().getStringExtra("path");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(id == 999)
        {
            File imgFile = new File(Environment.getExternalStorageDirectory()
                    + "/Android/PlantAi/test.jpg");

            if (imgFile.exists()) {
                Uri imageUri = Uri.fromFile(imgFile);
                if (imageUri != null) {
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageURI(imageUri);
                    viewPager.setVisibility(View.GONE);
                }
            }
        }
        else if(id == 998)
        {
            File imgFile = new File(path);

            if (imgFile.exists()) {
                Uri imageUri = Uri.fromFile(imgFile);
                if (imageUri != null) {
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageURI(imageUri);
                    viewPager.setVisibility(View.GONE);
                }
            }
        }
        else
        {
            ImageAdapter imageAdapter = new ImageAdapter(this,id);
            viewPager.setAdapter(imageAdapter);
        }
    }

    public void onCancel(View view) {
        onBackPressed();
    }
}
