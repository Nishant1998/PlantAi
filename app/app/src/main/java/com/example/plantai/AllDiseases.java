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

import org.json.JSONException;

import java.util.ArrayList;

public class AllDiseases extends AppCompatActivity implements DiseaseCardsAdapter.DiseaseCardOnClickListener{

    private static final String SHARED = "Settings";
    private static final String SHARED_APP_LANG = "app_lang";
    private String selected_lang;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DiseaseCard> diseaseCardList;
    private String[] diseaseName = new String[39];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_diseases);

        __init__();
        selected_lang = load_setting();
        setLang();

        //genrate data
        genrateDiseaseData();
        diseaseRecyclerViewConfig();

        // Register a callback for back button presses
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Your custom back action: navigate to a specific activity
                Intent intent = new Intent(AllDiseases.this, Home.class);
                startActivity(intent);
                finish();  // Optionally finish the current activity
            }
        });
    }

    private void diseaseRecyclerViewConfig() {
        //config
        recyclerView = findViewById(R.id.recyclerViewDisease);

        //performance
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new DiseaseCardsAdapter(diseaseCardList, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void genrateDiseaseData() {
        diseaseCardList = new ArrayList<>();

        // Static Mapping of Drawable IDs for each disease
        int[] diseaseImages = {
                R.drawable.dp00_0, R.drawable.dp01_0, R.drawable.dp02_0, R.drawable.dp03_0,
                R.drawable.dp04_0, R.drawable.dp05_0, R.drawable.dp06_0, R.drawable.dp07_0,
                R.drawable.dp08_0, R.drawable.dp09_0, R.drawable.dp10_0, R.drawable.dp11_0,
                R.drawable.dp12_0, R.drawable.dp13_0, R.drawable.dp14_0, R.drawable.dp15_0,
                R.drawable.dp16_0, R.drawable.dp17_0, R.drawable.dp18_0, R.drawable.dp19_0,
                R.drawable.dp20_0, R.drawable.dp21_0, R.drawable.dp22_0, R.drawable.dp23_0,
                R.drawable.dp24_0, R.drawable.dp25_0, R.drawable.dp26_0, R.drawable.dp27_0,
                R.drawable.dp28_0, R.drawable.dp29_0, R.drawable.dp30_0, R.drawable.dp31_0,
                R.drawable.dp32_0, R.drawable.dp33_0, R.drawable.dp34_0, R.drawable.dp35_0,
                R.drawable.dp36_0, R.drawable.dp37_0, R.drawable.dp38_0
        };

        for (int i = 0; i < diseaseName.length; i++) {
            // Load images using static mapping
            if(i==4) continue;
            int imageResource = diseaseImages[i];
            diseaseCardList.add(new DiseaseCard(imageResource, diseaseName[i], ""));
        }
    }



    private void __init__() {

    }

    private void setLang() {
        diseaseName[0] = getString(R.string.dname_01);
        diseaseName[1] = getString(R.string.dname_02);
        diseaseName[2] = getString(R.string.dname_03);
        diseaseName[3] = getString(R.string.dname_04);
        diseaseName[4] = getString(R.string.dname_05);
        diseaseName[5] = getString(R.string.dname_06);
        diseaseName[6] = getString(R.string.dname_07);
        diseaseName[7] = getString(R.string.dname_08);
        diseaseName[8] = getString(R.string.dname_09);
        diseaseName[9] = getString(R.string.dname_10);
        diseaseName[10] = getString(R.string.dname_11);
        diseaseName[11] = getString(R.string.dname_12);
        diseaseName[12] = getString(R.string.dname_13);
        diseaseName[13] = getString(R.string.dname_14);
        diseaseName[14] = getString(R.string.dname_15);
        diseaseName[15] = getString(R.string.dname_16);
        diseaseName[16] = getString(R.string.dname_17);
        diseaseName[17] = getString(R.string.dname_18);
        diseaseName[18] = getString(R.string.dname_19);
        diseaseName[19] = getString(R.string.dname_20);
        diseaseName[20] = getString(R.string.dname_21);
        diseaseName[21] = getString(R.string.dname_22);
        diseaseName[22] = getString(R.string.dname_23);
        diseaseName[23] = getString(R.string.dname_24);
        diseaseName[24] = getString(R.string.dname_25);
        diseaseName[25] = getString(R.string.dname_26);
        diseaseName[26] = getString(R.string.dname_27);
        diseaseName[27] = getString(R.string.dname_28);
        diseaseName[28] = getString(R.string.dname_29);
        diseaseName[29] = getString(R.string.dname_30);
        diseaseName[30] = getString(R.string.dname_31);
        diseaseName[31] = getString(R.string.dname_32);
        diseaseName[32] = getString(R.string.dname_33);
        diseaseName[33] = getString(R.string.dname_34);
        diseaseName[34] = getString(R.string.dname_35);
        diseaseName[35] = getString(R.string.dname_36);
        diseaseName[36] = getString(R.string.dname_37);
        diseaseName[37] = getString(R.string.dname_38);
        diseaseName[38] = getString(R.string.dname_39);
    }

    String load_setting() {
        SharedPreferences mySharedPreference = getSharedPreferences(SHARED, MODE_PRIVATE);
        return mySharedPreference.getString(SHARED_APP_LANG, "en");
    }

    @Override
    public void OnDiseaseCardClick(int position) {
        Intent intent = new Intent(AllDiseases.this, AboutDisease.class);
        intent.putExtra("did", position+1);
        startActivity(intent);
    }

    //TODO MENU
    //TODO menu bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent=null;
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
}
