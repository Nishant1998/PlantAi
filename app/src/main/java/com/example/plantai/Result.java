package com.example.plantai;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


public class Result extends AppCompatActivity implements View.OnClickListener, DiseaseCardsAdapter.DiseaseCardOnClickListener {
    private static final String TAG = "Result";
    private static final String SHARED = "Settings";
    private static final String SHARED_APP_LANG = "app_lang";
    private int selected_lang;
    private String jsonData;
    private int[] max = new int[3];
    private Double[] probability = new Double[38];
    private ImageButton ib;
    private JSONArray jprob, jmax;
    private ImageView uiv;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DiseaseCard> diseaseCardList;
    private String[] diseaseName = new String[38];

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        __init__();
        selected_lang = load_setting();
        setLang(selected_lang);

        jsonData = getIntent().getStringExtra("jsonresult");
        Log.e("RESULT FROM RESULT ", jsonData);
        try {
            JSONArray array = new JSONArray(jsonData);
            jmax = (JSONArray) array.get(1);
            jprob = (JSONArray) array.get(2);
            for (int i = 0; i <= 2; i++) {
                max[i] = (int) jmax.get(i);
//                probability[i] = (Double) jprob.get(max[i]);
//                probability[i] *= 100;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        Log.d("max", probability[0] + " " + probability[1] + " " + probability[2]);

        //SET USER Image View
        {
            File imgFile = new File(Environment.getExternalStorageDirectory()
                    + "/Android/PlantAi/test.jpg");

            if (imgFile.exists()) {
                Uri imageUri = Uri.fromFile(imgFile);
                if (imageUri != null) {
                    uiv.setImageURI(imageUri);
                }
            }
        }


        //genrate data
        try {
            genrateDiseaseData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        diseaseRecyclerViewConfig();

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


    private void genrateDiseaseData() throws JSONException {
        diseaseCardList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            switch (max[i]) {
                case 0:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp01, diseaseName[0], Math.round((double) jprob.get(0) * 100) + "%"));
                    break;
                case 1:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp02, diseaseName[1], Math.round((double) jprob.get(1) * 100) + "%"));
                    break;
                case 2:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp03, diseaseName[2], Math.round((double) jprob.get(2) * 100) + "%"));
                    break;
                case 3:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp04, diseaseName[3], Math.round((double) jprob.get(3) * 100) + "%"));
                    break;
                case 4:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp05, diseaseName[4], Math.round((double) jprob.get(4) * 100) + "%"));
                    break;
                case 5:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp06, diseaseName[5], Math.round((double) jprob.get(5) * 100) + "%"));
                    break;
                case 6:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp07, diseaseName[6], Math.round((double) jprob.get(6) * 100) + "%"));
                    break;
                case 7:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp08, diseaseName[7], Math.round((double) jprob.get(7) * 100) + "%"));
                    break;
                case 8:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp09, diseaseName[8], Math.round((double) jprob.get(8) * 100) + "%"));
                    break;
                case 9:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp10, diseaseName[9], Math.round((double) jprob.get(9) * 100) + "%"));
                    break;
                case 10:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp11, diseaseName[10], Math.round((double) jprob.get(10) * 100) + "%"));
                    break;
                case 11:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp12, diseaseName[11], Math.round((double) jprob.get(11) * 100) + "%"));
                    break;
                case 12:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp13, diseaseName[12], Math.round((double) jprob.get(12) * 100) + "%"));
                    break;
                case 13:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp14, diseaseName[13], Math.round((double) jprob.get(13) * 100) + "%"));
                    break;
                case 14:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp15, diseaseName[14], Math.round((double) jprob.get(14) * 100) + "%"));
                    break;
                case 15:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp16, diseaseName[15], Math.round((double) jprob.get(15) * 100) + "%"));
                    break;
                case 16:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp17, diseaseName[16], Math.round((double) jprob.get(16) * 100) + "%"));
                    break;
                case 17:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp18, diseaseName[17], Math.round((double) jprob.get(17) * 100) + "%"));
                    break;
                case 18:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp19, diseaseName[18], Math.round((double) jprob.get(18) * 100) + "%"));
                    break;
                case 19:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp20, diseaseName[19], Math.round((double) jprob.get(19) * 100) + "%"));
                    break;
                case 20:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp21, diseaseName[20], Math.round((double) jprob.get(20) * 100) + "%"));
                    break;
                case 21:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp22, diseaseName[21], Math.round((double) jprob.get(21) * 100) + "%"));
                    break;
                case 22:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp23, diseaseName[22], Math.round((double) jprob.get(22) * 100) + "%"));
                    break;
                case 23:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp24, diseaseName[23], Math.round((double) jprob.get(23) * 100) + "%"));
                    break;
                case 24:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp25, diseaseName[24], Math.round((double) jprob.get(24) * 100) + "%"));
                    break;
                case 25:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp26, diseaseName[25], Math.round((double) jprob.get(25) * 100) + "%"));
                    break;
                case 26:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp27, diseaseName[26], Math.round((double) jprob.get(26) * 100) + "%"));
                    break;
                case 27:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp28, diseaseName[27], Math.round((double) jprob.get(27) * 100) + "%"));
                    break;
                case 28:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp29, diseaseName[28], Math.round((double) jprob.get(28) * 100) + "%"));
                    break;
                case 29:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp30, diseaseName[29], Math.round((double) jprob.get(29) * 100) + "%"));
                    break;
                case 30:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp31, diseaseName[30], Math.round((double) jprob.get(30) * 100) + "%"));
                    break;
                case 31:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp32, diseaseName[31], Math.round((double) jprob.get(31) * 100) + "%"));
                    break;
                case 32:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp33, diseaseName[32], Math.round((double) jprob.get(32) * 100) + "%"));
                    break;
                case 33:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp34, diseaseName[33], Math.round((double) jprob.get(33) * 100) + "%"));
                    break;
                case 34:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp35, diseaseName[34], Math.round((double) jprob.get(34) * 100) + "%"));
                    break;
                case 35:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp36, diseaseName[35], Math.round((double) jprob.get(35) * 100) + "%"));
                    break;
                case 36:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp37, diseaseName[36], Math.round((double) jprob.get(36) * 100) + "%"));
                    break;
                case 37:
                    diseaseCardList.add(new DiseaseCard(R.drawable.dp38, diseaseName[37], Math.round((double) jprob.get(37) * 100) + "%"));
                    break;

            }
        }
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
                }
            }
            break;
        }
    }

    private void __init__() {
        uiv = findViewById(R.id.userImageView);
        ib = findViewById(R.id.imageButton);
    }

    //TODO Load Setting
    int load_setting() {
        SharedPreferences mySharedPreference = getSharedPreferences(SHARED, MODE_PRIVATE);
        int i = mySharedPreference.getInt(SHARED_APP_LANG, 0);
        return i;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Result.this, Home.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.imageButton: {
                intent = new Intent(Result.this, Home.class);
                Log.d("test", "image  Button press");
            }
            break;
            case R.id.userImageView: {
                intent = new Intent(Result.this, ImageSlider.class);
                intent.putExtra("id", 999);
            }
            break;
        }
        startActivity(intent);
    }

    @Override
    public void OnDiseaseCardClick(int position) {
        Intent intent = new Intent(Result.this, AboutDisease.class);
        intent.putExtra("did", max[position]+1);
        startActivity(intent);
    }
}
