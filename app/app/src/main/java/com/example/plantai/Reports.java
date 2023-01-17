package com.example.plantai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;

public class Reports extends AppCompatActivity implements ReportCardsAdapter.ReportCardOnClickListener {

    DatabaseHelper myDB;

    private static final String SHARED = "Settings";
    private static final String SHARED_APP_LANG = "app_lang";
    private int selected_lang;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ReportCard> reportCardList;
    private String[] diseaseName = new String[38];
    ArrayList<Integer> id = new ArrayList<>();

    CardView cardView;
    private TextView empText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);


        __init__();
        selected_lang = load_setting();
        setLang(selected_lang);

        //genrate data
        viewAll();
        reportRecyclerViewConfig();
    }

    private void genrateReportData(String diseaseName, String date) {
        reportCardList.add(new ReportCard(diseaseName, date));
    }

    private void reportRecyclerViewConfig() {
        //config
        recyclerView = findViewById(R.id.recyclerViewReports);

        //performance
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ReportCardsAdapter(reportCardList, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void viewAll() {
        Cursor cursor = myDB.getAllData();

        if (cursor.getCount() == 0) {
            cardView.setVisibility(View.VISIBLE);
        }
        else {cardView.setVisibility(View.GONE);}


        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            this.id.add(id);
            String date = cursor.getString(1);
            String time = cursor.getString(2);
            int max = cursor.getInt(3);

            genrateReportData(diseaseName[max], date + " " + time);
        }

    }

    private void __init__() {
        myDB = new DatabaseHelper(this);
        reportCardList = new ArrayList<>();
        cardView = findViewById(R.id.notScannedYet);

        empText = findViewById(R.id.emptyReportText);
    }

    private void setLang(int selected_lang) {
        switch (selected_lang) {
            case 0: {
                {
                    diseaseName[0] = getString(R.string.dname_01_0);
                    diseaseName[1] = getString(R.string.dname_02_0);
                    diseaseName[2] = getString(R.string.dname_03_0);
                    diseaseName[3] = getString(R.string.dname_04_0);
                    diseaseName[4] = getString(R.string.dname_05_0);
                    diseaseName[5] = getString(R.string.dname_06_0);
                    diseaseName[6] = getString(R.string.dname_07_0);
                    diseaseName[7] = getString(R.string.dname_08_0);
                    diseaseName[8] = getString(R.string.dname_09_0);
                    diseaseName[9] = getString(R.string.dname_10_0);
                    diseaseName[10] = getString(R.string.dname_11_0);
                    diseaseName[11] = getString(R.string.dname_12_0);
                    diseaseName[12] = getString(R.string.dname_13_0);
                    diseaseName[13] = getString(R.string.dname_14_0);
                    diseaseName[14] = getString(R.string.dname_15_0);
                    diseaseName[15] = getString(R.string.dname_16_0);
                    diseaseName[16] = getString(R.string.dname_17_0);
                    diseaseName[17] = getString(R.string.dname_18_0);
                    diseaseName[18] = getString(R.string.dname_19_0);
                    diseaseName[19] = getString(R.string.dname_20_0);
                    diseaseName[20] = getString(R.string.dname_21_0);
                    diseaseName[21] = getString(R.string.dname_22_0);
                    diseaseName[22] = getString(R.string.dname_23_0);
                    diseaseName[23] = getString(R.string.dname_24_0);
                    diseaseName[24] = getString(R.string.dname_25_0);
                    diseaseName[25] = getString(R.string.dname_26_0);
                    diseaseName[26] = getString(R.string.dname_27_0);
                    diseaseName[27] = getString(R.string.dname_28_0);
                    diseaseName[28] = getString(R.string.dname_29_0);
                    diseaseName[29] = getString(R.string.dname_30_0);
                    diseaseName[30] = getString(R.string.dname_31_0);
                    diseaseName[31] = getString(R.string.dname_32_0);
                    diseaseName[32] = getString(R.string.dname_33_0);
                    diseaseName[33] = getString(R.string.dname_34_0);
                    diseaseName[34] = getString(R.string.dname_35_0);
                    diseaseName[35] = getString(R.string.dname_36_0);
                    diseaseName[36] = getString(R.string.dname_37_0);
                    diseaseName[37] = getString(R.string.dname_38_0);
                    empText.setText(getString(R.string.not_scan_yet_0));
                }
            }
            break;
            case 1: {
                {
                    diseaseName[0] = getString(R.string.dname_01_1);
                    diseaseName[1] = getString(R.string.dname_02_1);
                    diseaseName[2] = getString(R.string.dname_03_1);
                    diseaseName[3] = getString(R.string.dname_04_1);
                    diseaseName[4] = getString(R.string.dname_05_1);
                    diseaseName[5] = getString(R.string.dname_06_1);
                    diseaseName[6] = getString(R.string.dname_07_1);
                    diseaseName[7] = getString(R.string.dname_08_1);
                    diseaseName[8] = getString(R.string.dname_09_1);
                    diseaseName[9] = getString(R.string.dname_10_1);
                    diseaseName[10] = getString(R.string.dname_11_1);
                    diseaseName[11] = getString(R.string.dname_12_1);
                    diseaseName[12] = getString(R.string.dname_13_1);
                    diseaseName[13] = getString(R.string.dname_14_1);
                    diseaseName[14] = getString(R.string.dname_15_1);
                    diseaseName[15] = getString(R.string.dname_16_1);
                    diseaseName[16] = getString(R.string.dname_17_1);
                    diseaseName[17] = getString(R.string.dname_18_1);
                    diseaseName[18] = getString(R.string.dname_19_1);
                    diseaseName[19] = getString(R.string.dname_20_1);
                    diseaseName[20] = getString(R.string.dname_21_1);
                    diseaseName[21] = getString(R.string.dname_22_1);
                    diseaseName[22] = getString(R.string.dname_23_1);
                    diseaseName[23] = getString(R.string.dname_24_1);
                    diseaseName[24] = getString(R.string.dname_25_1);
                    diseaseName[25] = getString(R.string.dname_26_1);
                    diseaseName[26] = getString(R.string.dname_27_1);
                    diseaseName[27] = getString(R.string.dname_28_1);
                    diseaseName[28] = getString(R.string.dname_29_1);
                    diseaseName[29] = getString(R.string.dname_30_1);
                    diseaseName[30] = getString(R.string.dname_31_1);
                    diseaseName[31] = getString(R.string.dname_32_1);
                    diseaseName[32] = getString(R.string.dname_33_1);
                    diseaseName[33] = getString(R.string.dname_34_1);
                    diseaseName[34] = getString(R.string.dname_35_1);
                    diseaseName[35] = getString(R.string.dname_36_1);
                    diseaseName[36] = getString(R.string.dname_37_1);
                    diseaseName[37] = getString(R.string.dname_38_1);
                    empText.setText(getString(R.string.not_scan_yet_1));
                }
            }
            break;
            case 2: {
                {
                    diseaseName[0] = getString(R.string.dname_01_2);
                    diseaseName[1] = getString(R.string.dname_02_2);
                    diseaseName[2] = getString(R.string.dname_03_2);
                    diseaseName[3] = getString(R.string.dname_04_2);
                    diseaseName[4] = getString(R.string.dname_05_2);
                    diseaseName[5] = getString(R.string.dname_06_2);
                    diseaseName[6] = getString(R.string.dname_07_2);
                    diseaseName[7] = getString(R.string.dname_08_2);
                    diseaseName[8] = getString(R.string.dname_09_2);
                    diseaseName[9] = getString(R.string.dname_10_2);
                    diseaseName[10] = getString(R.string.dname_11_2);
                    diseaseName[11] = getString(R.string.dname_12_2);
                    diseaseName[12] = getString(R.string.dname_13_2);
                    diseaseName[13] = getString(R.string.dname_14_2);
                    diseaseName[14] = getString(R.string.dname_15_2);
                    diseaseName[15] = getString(R.string.dname_16_2);
                    diseaseName[16] = getString(R.string.dname_17_2);
                    diseaseName[17] = getString(R.string.dname_18_2);
                    diseaseName[18] = getString(R.string.dname_19_2);
                    diseaseName[19] = getString(R.string.dname_20_2);
                    diseaseName[20] = getString(R.string.dname_21_2);
                    diseaseName[21] = getString(R.string.dname_22_2);
                    diseaseName[22] = getString(R.string.dname_23_2);
                    diseaseName[23] = getString(R.string.dname_24_2);
                    diseaseName[24] = getString(R.string.dname_25_2);
                    diseaseName[25] = getString(R.string.dname_26_2);
                    diseaseName[26] = getString(R.string.dname_27_2);
                    diseaseName[27] = getString(R.string.dname_28_2);
                    diseaseName[28] = getString(R.string.dname_29_2);
                    diseaseName[29] = getString(R.string.dname_30_2);
                    diseaseName[30] = getString(R.string.dname_31_2);
                    diseaseName[31] = getString(R.string.dname_32_2);
                    diseaseName[32] = getString(R.string.dname_33_2);
                    diseaseName[33] = getString(R.string.dname_34_2);
                    diseaseName[34] = getString(R.string.dname_35_2);
                    diseaseName[35] = getString(R.string.dname_36_2);
                    diseaseName[36] = getString(R.string.dname_37_2);
                    diseaseName[37] = getString(R.string.dname_38_2);
                    empText.setText(getString(R.string.not_scan_yet_2));
                }
            }
            break;
        }
    }

    int load_setting() {
        SharedPreferences mySharedPreference = getSharedPreferences(SHARED, MODE_PRIVATE);
        int i = mySharedPreference.getInt(SHARED_APP_LANG, 0);
        return i;
    }


    /**
     * Menu Bar
     **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
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

    @Override
    public void OnReportCardClick(int position) {
        Intent intent = new Intent(Reports.this,ReportD.class);
        intent.putExtra("Col_id",id.get(position));
        startActivity(intent);
    }
}
