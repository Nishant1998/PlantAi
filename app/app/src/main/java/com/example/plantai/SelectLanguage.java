package com.example.plantai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.File;
import java.io.Serializable;

public class SelectLanguage extends AppCompatActivity {


    int selected_lang;
    Button next;
    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView textView;
    private static final String SHARED = "Settings";
    private static final String SHARED_APP_LANG = "app_lang";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);
        next = findViewById(R.id.nextBtn);
        radioGroup = findViewById(R.id.lang_grp);
        textView = findViewById(R.id.sYL);

        //CREATING MEDIA STORAGE FILE
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() +"/Android/", "plantAi");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("App", "failed to create directory");
            }
        }

        //Check permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
       || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
       || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
//       || ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
//       || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED
//       || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED


            ) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.INTERNET,
//                    Manifest.permission.ACCESS_WIFI_STATE,
//                    Manifest.permission.ACCESS_NETWORK_STATE
                    }, 0);
        }

    }

    public void nextClick(View view) {
        save_setting(selected_lang);
        Intent intent = new Intent(SelectLanguage.this,Intro1.class);
        startActivity(intent);

    }

    public void radioClick(View view) {
        int radioBtn =  radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioBtn);

        if(radioButton.getText().equals("English"))
            selected_lang = 0;
        else if(radioButton.getText().equals("हिंदी"))
            selected_lang = 1;
        else if(radioButton.getText().equals("मराठी"))
            selected_lang = 2;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    && (grantResults[1] == PackageManager.PERMISSION_GRANTED)
                    && (grantResults[2] == PackageManager.PERMISSION_GRANTED)
//                    && (grantResults[3] == PackageManager.PERMISSION_GRANTED)
//                    && (grantResults[4] == PackageManager.PERMISSION_GRANTED)
//                    && (grantResults[5] == PackageManager.PERMISSION_GRANTED)
            ) {

            }
            else
                finish();
        }
    }

    //save settings
    void save_setting(int language_number)
    {
        SharedPreferences mySharedPreference = getSharedPreferences(SHARED,MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreference.edit();
        editor.putInt(SHARED_APP_LANG,language_number);
        editor.apply();
    }

}
