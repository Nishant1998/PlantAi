package com.example.plantai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;

public class ReportD extends AppCompatActivity implements View.OnClickListener, DiseaseCardsAdapter.DiseaseCardOnClickListener{

    private static final String TAG = "ReportD";

    private static final String SHARED = "Settings";
    private static final String SHARED_APP_LANG = "app_lang";
    private String selected_lang;

    private ImageButton ib;
    private TextView dateAndTime;
    private ImageView uiv;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DiseaseCard> diseaseCardList;
    private String[] diseaseName = new String[39];

    DatabaseHelper myDB;
    int[] max = new int[3];
    private String date,time,path=null;
    int[] prb = new int[3];

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_d);

        __init__();
        selected_lang = load_setting();
        setLang();

        int id = getIntent().getIntExtra("Col_id",0);


        Cursor cursor = myDB.getData(id+"");
        String data = null;
        if (cursor.moveToNext()) {
            date = cursor.getString(1);
            time = cursor.getString(2);
            max[0] = cursor.getInt(3);
            max[1] = cursor.getInt(4);
            max[2] = cursor.getInt(5);
            prb[0] = cursor.getInt(6);
            prb[1] = cursor.getInt(7);
            prb[2] = cursor.getInt(8);
            path = cursor.getString(9);
        }

        loadSavedImage(path);
        dateAndTime.setText(date + " " + time);

        //genrate data
        try {
            genrateDiseaseData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        diseaseRecyclerViewConfig();


    }

    // Load saved image into ImageView
    private void loadSavedImage(String imageUri) {
        if (imageUri != null) {
            Uri uri = Uri.parse(imageUri);
            uiv.setImageURI(uri); // ✅ Set ImageView from URI
            Log.d(TAG, "Image loaded from: " + imageUri);
        } else {
            Log.e(TAG, "Failed to load image: URI is null");
        }
    }


    private void diseaseRecyclerViewConfig() {
        //config
        recyclerView = findViewById(R.id.recyclerViewDiseaseReport);

        //performance
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new DiseaseCardsAdapter(diseaseCardList, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void genrateDiseaseData() throws JSONException {
        for (int i = 0; i < 3; i++) {
            int diseaseIndex = max[i]; // Get disease index

            // Generate dynamic resource name (dp00_0, dp01_0, ..., dp37_0)
            String resourceName = String.format("dp%02d_0", diseaseIndex);
            int resourceId = getResources().getIdentifier(resourceName, "drawable", getPackageName());

            if (resourceId != 0) { // Ensure valid resource
                diseaseCardList.add(new DiseaseCard(resourceId, diseaseName[diseaseIndex], prb[i] + "%"));
            } else {
                Log.e(TAG, "Missing drawable: " + resourceName);
            }
        }
        Log.d(TAG, "genrateDiseaseData: " + diseaseCardList);
    }


    //TODO Load Setting
    String load_setting() {
        SharedPreferences mySharedPreference = getSharedPreferences(SHARED, MODE_PRIVATE);
        return mySharedPreference.getString(SHARED_APP_LANG, "en");
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

    private void __init__() {
        ib = findViewById(R.id.imageButtonReport);
        uiv = findViewById(R.id.userImageViewReport);
        recyclerView = findViewById(R.id.recyclerViewReports);
        diseaseCardList = new ArrayList<>();
        myDB = new DatabaseHelper(this);
        dateAndTime = findViewById(R.id.dateAndTimeTextReport);
    }

    public void onClick(View view) {
        Intent intent = null;
        if (view.getId() == R.id.imageButtonReport) {
            intent = new Intent(ReportD.this, Reports.class);
            Log.d("test", "image Button press");
        } else if (view.getId() == R.id.userImageViewReport) {
            intent = new Intent(ReportD.this, ImageSlider.class);
            intent.putExtra("id", 998);
            intent.putExtra("image_path", path);
        }

        startActivity(intent);
    }

    @Override
    public void OnDiseaseCardClick(int position) {
        Intent intent = new Intent(ReportD.this, AboutDisease.class);
        intent.putExtra("did", max[position]+1);
        startActivity(intent);
    }
}
