package com.example.plantai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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

        if (id == 999) {
            String imagePath = getIntent().getStringExtra("image_path");

            if (imagePath != null) {
                File imgFile = new File(imagePath);
                if (imgFile.exists()) {
                    Uri imageUri = Uri.fromFile(imgFile);
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageURI(imageUri);
                    viewPager.setVisibility(View.GONE);
                } else {
                    Log.e("ImageSlider", "Image file does not exist at path: " + imagePath);
                }
            } else {
                Log.e("ImageSlider", "No image path received in intent");
            }
        }
        else if (id == 998) {
            String imagePath = getIntent().getStringExtra("image_path");

            if (imagePath != null) {
                Uri imageUri = Uri.parse(imagePath);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageURI(imageUri);
                viewPager.setVisibility(View.GONE);
            }
            else {
                Log.e("ImageSlider", "No image path received in intent");
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
