package com.example.plantai;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import java.util.ArrayList;

public class Home extends AppCompatActivity implements PlantCardsAdapter.PlantCardOnClickListener {
    Menu menu;

    private static final String SHARED = "Settings";
    private static final String SHARED_APP_LANG = "app_lang";
    private String selected_lang;

    private ArrayList<PlantCard> plantCardArrayList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String[] plantsName = new String[13];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //get setting and change language
        selected_lang = load_setting();
        setLang();


        //genrate data
        genratePlantData();
        plantRecyclerViewConfig();

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finishAffinity(); // Close the app
            }
        });
    }
    /** onRestart **/
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    /** Recycler View **/
    // CONFIG RecyclerView
    private void plantRecyclerViewConfig() {
        //config
        recyclerView = findViewById(R.id.recyclerViewPlants);

        //performance
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new PlantCardsAdapter(plantCardArrayList,this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
    // DATA FOR recyclerView.
    private void genratePlantData() {
        plantCardArrayList = new ArrayList<>();
        plantCardArrayList.add(new PlantCard(R.drawable.apple, plantsName[0]));
        plantCardArrayList.add(new PlantCard(R.drawable.blueberry, plantsName[1]));
        plantCardArrayList.add(new PlantCard(R.drawable.cherry, plantsName[2]));
        plantCardArrayList.add(new PlantCard(R.drawable.corn, plantsName[3]));
        plantCardArrayList.add(new PlantCard(R.drawable.grapes, plantsName[4]));
        plantCardArrayList.add(new PlantCard(R.drawable.orange, plantsName[5]));
        plantCardArrayList.add(new PlantCard(R.drawable.peach, plantsName[6]));
        plantCardArrayList.add(new PlantCard(R.drawable.pepper, plantsName[7]));
        plantCardArrayList.add(new PlantCard(R.drawable.potato, plantsName[8]));
        plantCardArrayList.add(new PlantCard(R.drawable.raspberry, plantsName[9]));
        plantCardArrayList.add(new PlantCard(R.drawable.soybean, plantsName[10]));
        plantCardArrayList.add(new PlantCard(R.drawable.strawberry, plantsName[11]));
        plantCardArrayList.add(new PlantCard(R.drawable.tomato, plantsName[12]));
    }
    //OnCLickListener
    @Override
    public void OnPlantCardClick(int position) {
        Intent intent = new Intent(this, PlantD.class);
        intent.putExtra("plant_num", position+1);
        startActivity(intent);
    }

    /**  MENU BAR   **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
//        setMenuText(selected_lang);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        if (item.getItemId() == R.id.op_all_diseases) {
            intent = new Intent(this, AllDiseases.class);
        } else if (item.getItemId() == R.id.op_reports) {
            intent = new Intent(this, Reports.class);
        } else if (item.getItemId() == R.id.op_setting) {
            intent = new Intent(this, Settings.class);
        } else if (item.getItemId() == R.id.op_about) {
            intent = new Intent(this, About.class);
        }

        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    /**  LOADING SETTINGS & SET LANGUAGE **/
    void setLang() {
        plantsName[0] = getString(R.string.apple_name);
        plantsName[1] = getString(R.string.blueberry_name);
        plantsName[2] = getString(R.string.cherry_name);
        plantsName[3] = getString(R.string.corn_name);
        plantsName[4] = getString(R.string.grapes_name);
        plantsName[5] = getString(R.string.orange_name);
        plantsName[6] = getString(R.string.peach_name);
        plantsName[7] = getString(R.string.pepper_bell_name);
        plantsName[8] = getString(R.string.potato_name);
        plantsName[9] = getString(R.string.raspberry_name);
        plantsName[10] = getString(R.string.soybean_name);
        plantsName[11] = getString(R.string.strawberry_name);
        plantsName[12] = getString(R.string.tomato_name);
    }
    String load_setting() {
        SharedPreferences mySharedPreference = getSharedPreferences(SHARED, MODE_PRIVATE);
        return mySharedPreference.getString(SHARED_APP_LANG, "en");
    }



}
