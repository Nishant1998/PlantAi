package com.example.plantai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class PlantD extends AppCompatActivity {

    private static final String TAG = "PlantD";
    
    private static final String SHARED = "Settings";
    private static final String SHARED_APP_LANG = "app_lang";
    int selected_lang;
    int plantNumber;
    ImageView imageView;
    TextView headText,infoText;
    int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 10;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_d);

    }

    /** onStart get context and set content **/
    @Override
    protected void onStart() {
        super.onStart();
        imageView = findViewById(R.id.plantDicPic);
        headText = findViewById(R.id.plantDicName);
        infoText = findViewById(R.id.plantDicContent);

        plantNumber = getIntent().getIntExtra("plant_num",0);
        //get setting and change language
        selected_lang = load_setting();
        setLang(selected_lang);

    }


    /** OnClick Camera Button - start camera intent **/
    public void startCamera(View view) {

        Log.d(TAG, "startCamera: Clicked on camera button");
        
        StrictMode.VmPolicy.Builder newbuilder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(newbuilder.build());

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String filepath = Environment.getExternalStorageDirectory()
                +"/Android/PlantAi/test.jpg";
        File outFile = new File(filepath);
        Uri outuri = Uri.fromFile(outFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outuri);

        Log.d(TAG, "startCamera: Starting camera.");
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (resultCode == RESULT_OK) {

                    Log.d(TAG, "onActivityResult: Reading clicked image.");
                    System.out.println("GOT RESULT CALLING CROP_IMAGE ###");
                    Log.d(TAG, "onActivityResult: Starting crop image activity");
                    Intent intent = new Intent(this,CropImage.class);
                    startActivity(intent);
                } else if (resultCode == RESULT_CANCELED) {
                    Log.d(TAG, "onActivityResult: Cancelled.");
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    
    /**  MENU BAR   **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent=null;
        switch (item.getItemId())
        {
            case R.id.op_profile:
                intent = new Intent(this,profile.class);
                break;
            case R.id.op_reports:
                intent = new Intent(this,Reports.class);
                break;
            case R.id.op_setting:
                intent = new Intent(this,Settings.class);
                break;
            case R.id.op_about:
                intent = new Intent(this,About.class);
                break;

        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
    
    /**  LOADING SETTINGS & SET LANGUAGE **/
    void setLang(int i) {
        switch (plantNumber)
        {
            case 1:
                imageView.setImageResource(R.drawable.apple);
                {
                switch (i)
                {
                    case 0:
                        headText.setText(getString(R.string.apple_name_0));
                        infoText.setText(getString(R.string.apple_info_0));
                        break;
                    case 1:
                        headText.setText(getString(R.string.apple_name_1));
                        infoText.setText(getString(R.string.apple_info_1));
                        break;
                    case 2:
                        headText.setText(getString(R.string.apple_name_2));
                        infoText.setText(getString(R.string.apple_info_2));
                        break;
                }
            }
                break;

            case 2:
                imageView.setImageResource(R.drawable.blueberry);
                {
                switch (i)
                {
                    case 0:
                        headText.setText(getString(R.string.blueberry_name_0));
                        infoText.setText(getString(R.string.blueberry_info_0));
                        break;
                    case 1:
                        headText.setText(getString(R.string.blueberry_name_1));
                        infoText.setText(getString(R.string.blueberry_info_1));
                        break;
                    case 2:
                        headText.setText(getString(R.string.blueberry_name_2));
                        infoText.setText(getString(R.string.blueberry_info_2));
                        break;
                }
            }
                break;

            case 3:
                imageView.setImageResource(R.drawable.cherry);
                {
                switch (i)
                {
                    case 0:
                        headText.setText(getString(R.string.cherry_name_0));
                        infoText.setText(getString(R.string.cherry_info_0));
                        break;
                    case 1:
                        headText.setText(getString(R.string.cherry_name_1));
                        infoText.setText(getString(R.string.cherry_info_1));
                        break;
                    case 2:
                        headText.setText(getString(R.string.cherry_name_2));
                        infoText.setText(getString(R.string.cherry_info_2));
                        break;
                }
            }
                break;

            case 4:
                imageView.setImageResource(R.drawable.corn);
                {
                switch (i)
                {
                    case 0:
                        headText.setText(getString(R.string.corn_name_0));
                        infoText.setText(getString(R.string.corn_info_0));
                        break;
                    case 1:
                        headText.setText(getString(R.string.corn_name_1));
                        infoText.setText(getString(R.string.corn_info_1));
                        break;
                    case 2:
                        headText.setText(getString(R.string.corn_name_2));
                        infoText.setText(getString(R.string.corn_info_2));
                        break;
                }
            }
                break;

            case 5:
                imageView.setImageResource(R.drawable.grapes);
                {
                switch (i)
                {
                    case 0:
                        headText.setText(getString(R.string.grapes_name_0));
                        infoText.setText(getString(R.string.grapes_info_0));
                        break;
                    case 1:
                        headText.setText(getString(R.string.grapes_name_1));
                        infoText.setText(getString(R.string.grapes_info_1));
                        break;
                    case 2:
                        headText.setText(getString(R.string.grapes_name_2));
                        infoText.setText(getString(R.string.grapes_info_2));
                        break;
                }
            }
                break;

            case 6:
                imageView.setImageResource(R.drawable.orange);
                {
                switch (i)
                {
                    case 0:
                        headText.setText(getString(R.string.orange_name_0));
                        infoText.setText(getString(R.string.orange_info_0));
                        break;
                    case 1:
                        headText.setText(getString(R.string.orange_name_1));
                        infoText.setText(getString(R.string.orange_info_1));
                        break;
                    case 2:
                        headText.setText(getString(R.string.orange_name_2));
                        infoText.setText(getString(R.string.orange_info_2));
                        break;
                }
            }
                break;

            case 7:
                imageView.setImageResource(R.drawable.peach);
            {
                switch (i)
                {
                    case 0:
                        headText.setText(getString(R.string.peach_name_0));
                        infoText.setText(getString(R.string.peach_info_0));
                        break;
                    case 1:
                        headText.setText(getString(R.string.peach_name_1));
                        infoText.setText(getString(R.string.peach_info_1));
                        break;
                    case 2:
                        headText.setText(getString(R.string.peach_name_2));
                        infoText.setText(getString(R.string.peach_info_2));
                        break;
                }
            }
                break;

            case 8:
                imageView.setImageResource(R.drawable.pepper);
            {
                switch (i)
                {
                    case 0:
                        headText.setText(getString(R.string.pepper_bell_name_0));
                        infoText.setText(getString(R.string.pepper_bell_info_0));
                        break;
                    case 1:
                        headText.setText(getString(R.string.pepper_bell_name_1));
                        infoText.setText(getString(R.string.pepper_bell_info_1));
                        break;
                    case 2:
                        headText.setText(getString(R.string.pepper_bell_name_2));
                        infoText.setText(getString(R.string.pepper_bell_info_2));
                        break;
                }
            }
                break;

            case 9:
                imageView.setImageResource(R.drawable.potato);
            {
                switch (i)
                {
                    case 0:
                        headText.setText(getString(R.string.patato_name_0));
                        infoText.setText(getString(R.string.patato_info_0));
                        break;
                    case 1:
                        headText.setText(getString(R.string.patato_name_1));
                        infoText.setText(getString(R.string.patato_info_1));
                        break;
                    case 2:
                        headText.setText(getString(R.string.patato_name_2));
                        infoText.setText(getString(R.string.patato_info_2));
                        break;
                }
            }
                break;

            case 10:
                imageView.setImageResource(R.drawable.raspberry);
            {
                switch (i)
                {
                    case 0:
                        headText.setText(getString(R.string.raspberry_name_0));
                        infoText.setText(getString(R.string.raspberry_info_0));
                        break;
                    case 1:
                        headText.setText(getString(R.string.raspberry_name_1));
                        infoText.setText(getString(R.string.raspberry_info_1));
                        break;
                    case 2:
                        headText.setText(getString(R.string.raspberry_name_2));
                        infoText.setText(getString(R.string.raspberry_info_2));
                        break;
                }
            }
                break;

            case 11:
                imageView.setImageResource(R.drawable.soybean);
            {
                switch (i)
                {
                    case 0:
                        headText.setText(getString(R.string.soybean_name_0));
                        infoText.setText(getString(R.string.soybean_info_0));
                        break;
                    case 1:
                        headText.setText(getString(R.string.soybean_name_1));
                        infoText.setText(getString(R.string.soybean_info_1));
                        break;
                    case 2:
                        headText.setText(getString(R.string.soybean_name_2));
                        infoText.setText(getString(R.string.soybean_info_2));
                        break;
                }
            }
                break;

            case 12:
                imageView.setImageResource(R.drawable.strawberry);
            {
                switch (i)
                {
                    case 0:
                        headText.setText(getString(R.string.strawberry_name_0));
                        infoText.setText(getString(R.string.strawberry_info_0));
                        break;
                    case 1:
                        headText.setText(getString(R.string.strawberry_name_1));
                        infoText.setText(getString(R.string.strawberry_info_1));
                        break;
                    case 2:
                        headText.setText(getString(R.string.strawberry_name_2));
                        infoText.setText(getString(R.string.strawberry_info_2));
                        break;
                }
            }
                break;

            case 13:
                imageView.setImageResource(R.drawable.tomato);
            {
                switch (i)
                {
                    case 0:
                        headText.setText(getString(R.string.tomato_name_0));
                        infoText.setText(getString(R.string.tomato_info_0));
                        break;
                    case 1:
                        headText.setText(getString(R.string.tomato_name_1));
                        infoText.setText(getString(R.string.tomato_info_1));
                        break;
                    case 2:
                        headText.setText(getString(R.string.tomato_name_2));
                        infoText.setText(getString(R.string.tomato_info_2));
                        break;
                }
            }
                break;
        }
    }
    int load_setting() {
        SharedPreferences mySharedPreference = getSharedPreferences(SHARED,MODE_PRIVATE);
        int i = mySharedPreference.getInt(SHARED_APP_LANG,0);
        return i;
    }

}
