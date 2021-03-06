package com.example.plantai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

public class Intro1 extends AppCompatActivity  {

    private static final String SHARED = "Settings";
    private static final String SHARED_APP_LANG = "app_lang";
    int selected_lang;
    Button next;
    TextView textH;
    TextView textD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro1);
        next = findViewById(R.id.nextBtn);
        textH = findViewById(R.id.intro1_head);
        textD = findViewById(R.id.intro1_dis);

        //get setting and change language
        selected_lang = load_setting();
        System.out.println(" **** selected_lang = " + selected_lang);
        setLang(selected_lang);

    }

    public void nextClick(View view) {
        Intent intent = new Intent(this,Intro2.class);
        startActivity(intent);

    }

    //TODO Change lang
    void setLang(int i)
    {
        switch (i)
        {
            case 0:
                System.out.println("CASE 0 ***");
                next.setText(getString(R.string.next_0));
                textH.setText(getString(R.string.intro_h1_0));
                textD.setText(getString(R.string.intro_c1_0));
                break;
            case 1:
                System.out.println("CASE 1 ***");
                next.setText(getString(R.string.next_1));
                textH.setText(getString(R.string.intro_h1_1));
                textD.setText(getString(R.string.intro_c1_1));
                break;
            case 2:
                System.out.println("CASE 2 ***");
                next.setText(getString(R.string.next_2));
                textH.setText(getString(R.string.intro_h1_2));
                textD.setText(getString(R.string.intro_c1_2));
                break;
        }
    }

    //TODO Load Setting
    int load_setting()
    {
        SharedPreferences mySharedPreference = getSharedPreferences(SHARED,MODE_PRIVATE);
        int i = mySharedPreference.getInt(SHARED_APP_LANG,0);
        return i;
    }

}
