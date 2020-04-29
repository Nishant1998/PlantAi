package com.example.plantai;

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
    private int selected_lang;

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
        setLang(selected_lang);


        //genrate data
        genratePlantData();
        plantRecyclerViewConfig();


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
        switch (item.getItemId()) {
            case R.id.op_all_diseases:
                intent = new Intent(this, AllDiseases.class);
                break;
            case R.id.op_reports:
                intent = new Intent(this, Reports.class);
                break;
            case R.id.op_setting:
                intent = new Intent(this, Settings.class);
                break;
            case R.id.op_about:
                intent = new Intent(this, About.class);
                break;

        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    /**  LOADING SETTINGS & SET LANGUAGE **/
    void setLang(int i) {
        switch (i) {
            case 0:
                System.out.println("CASE 0 ***");
                plantsName[0] = getString(R.string.apple_name_0);
                plantsName[1] = getString(R.string.blueberry_name_0);
                plantsName[2] = getString(R.string.cherry_name_0);
                plantsName[3] = getString(R.string.corn_name_0);
                plantsName[4] = getString(R.string.grapes_name_0);
                plantsName[5] = getString(R.string.orange_name_0);
                plantsName[6] = getString(R.string.peach_name_0);
                plantsName[7] = getString(R.string.pepper_bell_name_0);
                plantsName[8] = getString(R.string.patato_name_0);
                plantsName[9] = getString(R.string.raspberry_name_0);
                plantsName[10] = getString(R.string.soybean_name_0);
                plantsName[11] = getString(R.string.strawberry_name_0);
                plantsName[12] = getString(R.string.tomato_name_0);
                break;
            case 1:
                System.out.println("CASE 1 ***");
                plantsName[0] = getString(R.string.apple_name_1);
                plantsName[1] = getString(R.string.blueberry_name_1);
                plantsName[2] = getString(R.string.cherry_name_1);
                plantsName[3] = getString(R.string.corn_name_1);
                plantsName[4] = getString(R.string.grapes_name_1);
                plantsName[5] = getString(R.string.orange_name_1);
                plantsName[6] = getString(R.string.peach_name_1);
                plantsName[7] = getString(R.string.pepper_bell_name_1);
                plantsName[8] = getString(R.string.patato_name_1);
                plantsName[9] = getString(R.string.raspberry_name_1);
                plantsName[10] = getString(R.string.soybean_name_1);
                plantsName[11] = getString(R.string.strawberry_name_1);
                plantsName[12] = getString(R.string.tomato_name_1);
                break;
            case 2:
                System.out.println("CASE 2 ***");
                plantsName[0] = getString(R.string.apple_name_2);
                plantsName[1] = getString(R.string.blueberry_name_2);
                plantsName[2] = getString(R.string.cherry_name_2);
                plantsName[3] = getString(R.string.corn_name_2);
                plantsName[4] = getString(R.string.grapes_name_2);
                plantsName[5] = getString(R.string.orange_name_2);
                plantsName[6] = getString(R.string.peach_name_2);
                plantsName[7] = getString(R.string.pepper_bell_name_2);
                plantsName[8] = getString(R.string.patato_name_2);
                plantsName[9] = getString(R.string.raspberry_name_2);
                plantsName[10] = getString(R.string.soybean_name_2);
                plantsName[11] = getString(R.string.strawberry_name_2);
                plantsName[12] = getString(R.string.tomato_name_2);
                break;
        }
    }
    int load_setting() {
        SharedPreferences mySharedPreference = getSharedPreferences(SHARED, MODE_PRIVATE);
        int i = mySharedPreference.getInt(SHARED_APP_LANG, 0);
        return i;
    }

}
