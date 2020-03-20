package com.example.plantai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Home extends AppCompatActivity implements View.OnClickListener  {


    Menu menu;

    private static final String SHARED = "Settings";
    private static final String SHARED_APP_LANG = "app_lang";
    int selected_lang;

//    init
    private TextView appleTextView;
    private TextView blueberryTextView;
    private TextView cherryTextView;
    private TextView cornTextView;
    private TextView grapeTextView;
    private TextView orangeTextView;
    private TextView peachTextView;
    private TextView pepperTextView;
    private TextView potatoTextView;
    private TextView raspberryTextView;
    private TextView soybeanTextView;
    private TextView strawberryTextView;
    private TextView tomatoTextView;





    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //refreash
//        Intent i = getIntent();
//        finish();
//        startActivity(i);
//        selected_lang = load_setting();

        __init__();

        //get setting and change language
        selected_lang = load_setting();
        System.out.println(" **** selected_lang = " + selected_lang);
        setLang(selected_lang);

        //finding frames
        {
            RelativeLayout appleGrp = findViewById(R.id.appleGrp);
            appleGrp.setOnClickListener(this);
            RelativeLayout blueberryGrp = findViewById(R.id.blueberryGrp);
            blueberryGrp.setOnClickListener(this);
            RelativeLayout cherryGrp = findViewById(R.id.cherryGrp);
            cherryGrp.setOnClickListener(this);
            RelativeLayout cornGrp = findViewById(R.id.cornGrp);
            cornGrp.setOnClickListener(this);
            RelativeLayout grapesGrp = findViewById(R.id.grapeGrp);
            grapesGrp.setOnClickListener(this);
            RelativeLayout orangeGrp = findViewById(R.id.orangeGrp);
            orangeGrp.setOnClickListener(this);
            RelativeLayout peachGrp = findViewById(R.id.peachGrp);
            peachGrp.setOnClickListener(this);
            RelativeLayout pepperBellGrp = findViewById(R.id.pepperBellGrp);
            pepperBellGrp.setOnClickListener(this);
            RelativeLayout potatoGrp = findViewById(R.id.potatoGrp);
            potatoGrp.setOnClickListener(this);
            RelativeLayout raspberryGrp = findViewById(R.id.raspberryGrp);
            raspberryGrp.setOnClickListener(this);
            RelativeLayout soybeanGrp = findViewById(R.id.soybeanGrp);
            soybeanGrp.setOnClickListener(this);
            RelativeLayout strawberryGrp = findViewById(R.id.strawberryGrp);
            strawberryGrp.setOnClickListener(this);
            RelativeLayout tomatoGrp = findViewById(R.id.tomatoGrp);
            tomatoGrp.setOnClickListener(this);

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    //TODO menu bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
//        setMenuText(selected_lang);
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


    //    __init__
    void __init__()
    {
        appleTextView = findViewById(R.id.applePlantName);
        blueberryTextView = findViewById(R.id.blueberryPlantName);
        cherryTextView = findViewById(R.id.cherryPlantName);
        cornTextView = findViewById(R.id.cornPlantName);
        grapeTextView = findViewById(R.id.grapePlantName);
        orangeTextView = findViewById(R.id.orangePlantName);
        peachTextView = findViewById(R.id.peachPlantName);
        pepperTextView = findViewById(R.id.pepperBellPlantName);
        potatoTextView = findViewById(R.id.potatoPlantName);
        raspberryTextView = findViewById(R.id.raspberryPlantName);
        soybeanTextView = findViewById(R.id.soybeanPlantName);
        strawberryTextView = findViewById(R.id.strawberryPlantName);
        tomatoTextView = findViewById(R.id.tomatoPlantName);
    }

    //TODO Change lang
    void setLang(int i)
    {
        switch (i)
        {
            case 0:
                System.out.println("CASE 0 ***");
                appleTextView.setText(getString(R.string.apple_name_0));
                blueberryTextView.setText(getString(R.string.blueberry_name_0));
                cherryTextView.setText(getString(R.string.cherry_name_0));
                cornTextView.setText(getString(R.string.corn_name_0));
                grapeTextView.setText(getString(R.string.grapes_name_0));
                orangeTextView.setText(getString(R.string.orange_name_0));
                peachTextView.setText(getString(R.string.peach_name_0));
                pepperTextView.setText(getString(R.string.pepper_bell_name_0));
                potatoTextView.setText(getString(R.string.patato_name_0));
                raspberryTextView.setText(getString(R.string.raspberry_name_0));
                soybeanTextView.setText(getString(R.string.soybean_name_0));
                strawberryTextView.setText(getString(R.string.strawberry_name_0));
                tomatoTextView.setText(getString(R.string.tomato_name_0));
                break;
            case 1:
                System.out.println("CASE 1 ***");
                appleTextView.setText(getString(R.string.apple_name_1));
                blueberryTextView.setText(getString(R.string.blueberry_name_1));
                cherryTextView.setText(getString(R.string.cherry_name_1));
                cornTextView.setText(getString(R.string.corn_name_1));
                grapeTextView.setText(getString(R.string.grapes_name_1));
                orangeTextView.setText(getString(R.string.orange_name_1));
                peachTextView.setText(getString(R.string.peach_name_1));
                pepperTextView.setText(getString(R.string.pepper_bell_name_1));
                potatoTextView.setText(getString(R.string.patato_name_1));
                raspberryTextView.setText(getString(R.string.raspberry_name_1));
                soybeanTextView.setText(getString(R.string.soybean_name_1));
                strawberryTextView.setText(getString(R.string.strawberry_name_1));
                tomatoTextView.setText(getString(R.string.tomato_name_1));
                break;
            case 2:
                System.out.println("CASE 2 ***");
                appleTextView.setText(getString(R.string.apple_name_2));
                blueberryTextView.setText(getString(R.string.blueberry_name_2));
                cherryTextView.setText(getString(R.string.cherry_name_2));
                cornTextView.setText(getString(R.string.corn_name_2));
                grapeTextView.setText(getString(R.string.grapes_name_2));
                orangeTextView.setText(getString(R.string.orange_name_2));
                peachTextView.setText(getString(R.string.peach_name_2));
                pepperTextView.setText(getString(R.string.pepper_bell_name_2));
                potatoTextView.setText(getString(R.string.patato_name_2));
                raspberryTextView.setText(getString(R.string.raspberry_name_2));
                soybeanTextView.setText(getString(R.string.soybean_name_2));
                strawberryTextView.setText(getString(R.string.strawberry_name_2));
                tomatoTextView.setText(getString(R.string.tomato_name_2));
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

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.appleGrp:
                intent = new Intent(this, PlantD.class);
                intent.putExtra("plant_num", 1);
                startActivity(intent);
                break;

            case R.id.blueberryGrp:
                intent = new Intent(this, PlantD.class);
                intent.putExtra("plant_num", 2);
                startActivity(intent);
                break;

            case R.id.cherryGrp:
                intent = new Intent(this, PlantD.class);
                intent.putExtra("plant_num", 3);
                startActivity(intent);
                break;

            case R.id.cornGrp:
                intent = new Intent(this, PlantD.class);
                intent.putExtra("plant_num", 4);
                startActivity(intent);
                break;

            case R.id.grapeGrp:
                intent = new Intent(this, PlantD.class);
                intent.putExtra("plant_num", 5);
                startActivity(intent);
                break;

            case R.id.orangeGrp:
                intent = new Intent(this, PlantD.class);
                intent.putExtra("plant_num", 6);
                startActivity(intent);
                break;
            case R.id.peachGrp:
                intent = new Intent(this, PlantD.class);
                intent.putExtra("plant_num", 7);
                startActivity(intent);
                break;
            case R.id.pepperBellGrp:
                intent = new Intent(this, PlantD.class);
                intent.putExtra("plant_num", 8);
                startActivity(intent);
                break;
            case R.id.potatoGrp:
                intent = new Intent(this, PlantD.class);
                intent.putExtra("plant_num", 9);
                startActivity(intent);
                break;
            case R.id.raspberryGrp:
                intent = new Intent(this, PlantD.class);
                intent.putExtra("plant_num", 10);
                startActivity(intent);
                break;
            case R.id.soybeanGrp:
                intent = new Intent(this, PlantD.class);
                intent.putExtra("plant_num", 11);
                startActivity(intent);
                break;
            case R.id.strawberryGrp:
                intent = new Intent(this, PlantD.class);
                intent.putExtra("plant_num", 12);
                startActivity(intent);
                break;
            case R.id.tomatoGrp:
                intent = new Intent(this, PlantD.class);
                intent.putExtra("plant_num", 13);
                startActivity(intent);
                break;
        }
    }
}
