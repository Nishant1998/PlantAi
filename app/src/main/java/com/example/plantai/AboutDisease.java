package com.example.plantai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutDisease extends AppCompatActivity implements View.OnClickListener{

    private static final String SHARED = "Settings";
    private static final String SHARED_APP_LANG = "app_lang";
    int selected_lang;
    int did;

    int[] stringDnames_0 = {R.string.dname_01_0,R.string.dname_02_0,R.string.dname_03_0, R.string.dname_04_0,R.string.dname_05_0,R.string.dname_06_0,R.string.dname_07_0,R.string.dname_08_0,R.string.dname_09_0,R.string.dname_10_0,R.string.dname_11_0,R.string.dname_12_0,R.string.dname_13_0,R.string.dname_14_0,R.string.dname_15_0,R.string.dname_16_0,R.string.dname_17_0,R.string.dname_18_0,R.string.dname_19_0,R.string.dname_20_0,R.string.dname_21_0,R.string.dname_22_0,R.string.dname_23_0,R.string.dname_24_0,R.string.dname_25_0,R.string.dname_26_0,R.string.dname_27_0,R.string.dname_28_0,R.string.dname_29_0,R.string.dname_30_0,R.string.dname_31_0,R.string.dname_32_0,R.string.dname_33_0,R.string.dname_34_0,R.string.dname_35_0,R.string.dname_36_0,R.string.dname_37_0,R.string.dname_38_0};
    int[] stringDnames_1 = {R.string.dname_01_1,R.string.dname_02_1,R.string.dname_03_1, R.string.dname_04_1,R.string.dname_05_1,R.string.dname_06_1,R.string.dname_07_1,R.string.dname_08_1,R.string.dname_09_1,R.string.dname_10_1,R.string.dname_11_1,R.string.dname_12_1,R.string.dname_13_1,R.string.dname_14_1,R.string.dname_15_1,R.string.dname_16_1,R.string.dname_17_1,R.string.dname_18_1,R.string.dname_19_1,R.string.dname_20_1,R.string.dname_21_1,R.string.dname_22_1,R.string.dname_23_1,R.string.dname_24_1,R.string.dname_25_1,R.string.dname_26_1,R.string.dname_27_1,R.string.dname_28_1,R.string.dname_29_1,R.string.dname_30_1,R.string.dname_31_1,R.string.dname_32_1,R.string.dname_33_1,R.string.dname_34_1,R.string.dname_35_1,R.string.dname_36_1,R.string.dname_37_1,R.string.dname_38_1};
    int[] stringDnames_2 = {R.string.dname_01_2,R.string.dname_02_2,R.string.dname_03_2, R.string.dname_04_2,R.string.dname_05_2,R.string.dname_06_2,R.string.dname_07_2,R.string.dname_08_2,R.string.dname_09_2,R.string.dname_10_2,R.string.dname_11_2,R.string.dname_12_2,R.string.dname_13_2,R.string.dname_14_2,R.string.dname_15_2,R.string.dname_16_2,R.string.dname_17_2,R.string.dname_18_2,R.string.dname_19_2,R.string.dname_20_2,R.string.dname_21_2,R.string.dname_22_2,R.string.dname_23_2,R.string.dname_24_2,R.string.dname_25_2,R.string.dname_26_2,R.string.dname_27_2,R.string.dname_28_2,R.string.dname_29_2,R.string.dname_30_2,R.string.dname_31_2,R.string.dname_32_2,R.string.dname_33_2,R.string.dname_34_2,R.string.dname_35_2,R.string.dname_36_2,R.string.dname_37_2,R.string.dname_38_2};

    int[] title = {R.string.symptoms_0,R.string.symptoms_1,R.string.symptoms_2,R.string.prevention_0,R.string.prevention_1,R.string.prevention_2,R.string.cause_0,R.string.cause_1,R.string.cause_2,R.string.bcontrol_0,R.string.bcontrol_1,R.string.bcontrol_2};

    int[][] details_0 = {{R.string.dd_1_01_0, R.string.dd_2_01_0, R.string.dd_3_01_0, R.string.dd_4_01_0}, {R.string.dd_1_02_0, R.string.dd_2_02_0, R.string.dd_3_02_0, R.string.dd_4_02_0}, {R.string.dd_1_03_0, R.string.dd_2_03_0, R.string.dd_3_03_0, R.string.dd_4_03_0}, {R.string.dd_1_04_0, R.string.dd_2_04_0, R.string.dd_3_04_0, R.string.dd_4_04_0}, {R.string.dd_1_05_0, R.string.dd_2_05_0, R.string.dd_3_05_0, R.string.dd_4_05_0}, {R.string.dd_1_06_0, R.string.dd_2_06_0, R.string.dd_3_06_0, R.string.dd_4_06_0}, {R.string.dd_1_07_0, R.string.dd_2_07_0, R.string.dd_3_07_0, R.string.dd_4_07_0}, {R.string.dd_1_08_0, R.string.dd_2_08_0, R.string.dd_3_08_0, R.string.dd_4_08_0}, {R.string.dd_1_09_0, R.string.dd_2_09_0, R.string.dd_3_09_0, R.string.dd_4_09_0}, {R.string.dd_1_10_0, R.string.dd_2_10_0, R.string.dd_3_10_0, R.string.dd_4_10_0}, {R.string.dd_1_11_0, R.string.dd_2_11_0, R.string.dd_3_11_0, R.string.dd_4_11_0}, {R.string.dd_1_12_0, R.string.dd_2_12_0, R.string.dd_3_12_0, R.string.dd_4_12_0}, {R.string.dd_1_13_0, R.string.dd_2_13_0, R.string.dd_3_13_0, R.string.dd_4_13_0}, {R.string.dd_1_14_0, R.string.dd_2_14_0, R.string.dd_3_14_0, R.string.dd_4_14_0}, {R.string.dd_1_15_0, R.string.dd_2_15_0, R.string.dd_3_15_0, R.string.dd_4_15_0}, {R.string.dd_1_16_0, R.string.dd_2_16_0, R.string.dd_3_16_0, R.string.dd_4_16_0}, {R.string.dd_1_17_0, R.string.dd_2_17_0, R.string.dd_3_17_0, R.string.dd_4_17_0}, {R.string.dd_1_18_0, R.string.dd_2_18_0, R.string.dd_3_18_0, R.string.dd_4_18_0}, {R.string.dd_1_19_0, R.string.dd_2_19_0, R.string.dd_3_19_0, R.string.dd_4_19_0}, {R.string.dd_1_20_0, R.string.dd_2_20_0, R.string.dd_3_20_0, R.string.dd_4_20_0}, {R.string.dd_1_21_0, R.string.dd_2_21_0, R.string.dd_3_21_0, R.string.dd_4_21_0}, {R.string.dd_1_22_0, R.string.dd_2_22_0, R.string.dd_3_22_0, R.string.dd_4_22_0}, {R.string.dd_1_23_0, R.string.dd_2_23_0, R.string.dd_3_23_0, R.string.dd_4_23_0}, {R.string.dd_1_24_0, R.string.dd_2_24_0, R.string.dd_3_24_0, R.string.dd_4_24_0}, {R.string.dd_1_25_0, R.string.dd_2_25_0, R.string.dd_3_25_0, R.string.dd_4_25_0}, {R.string.dd_1_26_0, R.string.dd_2_26_0, R.string.dd_3_26_0, R.string.dd_4_26_0}, {R.string.dd_1_27_0, R.string.dd_2_27_0, R.string.dd_3_27_0, R.string.dd_4_27_0}, {R.string.dd_1_28_0, R.string.dd_2_28_0, R.string.dd_3_28_0, R.string.dd_4_28_0}, {R.string.dd_1_29_0, R.string.dd_2_29_0, R.string.dd_3_29_0, R.string.dd_4_29_0}, {R.string.dd_1_30_0, R.string.dd_2_30_0, R.string.dd_3_30_0, R.string.dd_4_30_0}, {R.string.dd_1_31_0, R.string.dd_2_31_0, R.string.dd_3_31_0, R.string.dd_4_31_0}, {R.string.dd_1_32_0, R.string.dd_2_32_0, R.string.dd_3_32_0, R.string.dd_4_32_0}, {R.string.dd_1_33_0, R.string.dd_2_33_0, R.string.dd_3_33_0, R.string.dd_4_33_0}, {R.string.dd_1_34_0, R.string.dd_2_34_0, R.string.dd_3_34_0, R.string.dd_4_34_0}, {R.string.dd_1_35_0, R.string.dd_2_35_0, R.string.dd_3_35_0, R.string.dd_4_35_0}, {R.string.dd_1_36_0, R.string.dd_2_36_0, R.string.dd_3_36_0, R.string.dd_4_36_0}, {R.string.dd_1_37_0, R.string.dd_2_37_0, R.string.dd_3_37_0, R.string.dd_4_37_0}, {R.string.dd_1_38_0, R.string.dd_2_38_0, R.string.dd_3_38_0, R.string.dd_4_38_0}};
    int[][] details_1 = {{R.string.dd_1_01_1, R.string.dd_2_01_1, R.string.dd_3_01_1, R.string.dd_4_01_1}, {R.string.dd_1_02_1, R.string.dd_2_02_1, R.string.dd_3_02_1, R.string.dd_4_02_1}, {R.string.dd_1_03_1, R.string.dd_2_03_1, R.string.dd_3_03_1, R.string.dd_4_03_1}, {R.string.dd_1_04_1, R.string.dd_2_04_1, R.string.dd_3_04_1, R.string.dd_4_04_1}, {R.string.dd_1_05_1, R.string.dd_2_05_1, R.string.dd_3_05_1, R.string.dd_4_05_1}, {R.string.dd_1_06_1, R.string.dd_2_06_1, R.string.dd_3_06_1, R.string.dd_4_06_1}, {R.string.dd_1_07_1, R.string.dd_2_07_1, R.string.dd_3_07_1, R.string.dd_4_07_1}, {R.string.dd_1_08_1, R.string.dd_2_08_1, R.string.dd_3_08_1, R.string.dd_4_08_1}, {R.string.dd_1_09_1, R.string.dd_2_09_1, R.string.dd_3_09_1, R.string.dd_4_09_1}, {R.string.dd_1_10_1, R.string.dd_2_10_1, R.string.dd_3_10_1, R.string.dd_4_10_1}, {R.string.dd_1_11_1, R.string.dd_2_11_1, R.string.dd_3_11_1, R.string.dd_4_11_1}, {R.string.dd_1_12_1, R.string.dd_2_12_1, R.string.dd_3_12_1, R.string.dd_4_12_1}, {R.string.dd_1_13_1, R.string.dd_2_13_1, R.string.dd_3_13_1, R.string.dd_4_13_1}, {R.string.dd_1_14_1, R.string.dd_2_14_1, R.string.dd_3_14_1, R.string.dd_4_14_1}, {R.string.dd_1_15_1, R.string.dd_2_15_1, R.string.dd_3_15_1, R.string.dd_4_15_1}, {R.string.dd_1_16_1, R.string.dd_2_16_1, R.string.dd_3_16_1, R.string.dd_4_16_1}, {R.string.dd_1_17_1, R.string.dd_2_17_1, R.string.dd_3_17_1, R.string.dd_4_17_1}, {R.string.dd_1_18_1, R.string.dd_2_18_1, R.string.dd_3_18_1, R.string.dd_4_18_1}, {R.string.dd_1_19_1, R.string.dd_2_19_1, R.string.dd_3_19_1, R.string.dd_4_19_1}, {R.string.dd_1_20_1, R.string.dd_2_20_1, R.string.dd_3_20_1, R.string.dd_4_20_1}, {R.string.dd_1_21_1, R.string.dd_2_21_1, R.string.dd_3_21_1, R.string.dd_4_21_1}, {R.string.dd_1_22_1, R.string.dd_2_22_1, R.string.dd_3_22_1, R.string.dd_4_22_1}, {R.string.dd_1_23_1, R.string.dd_2_23_1, R.string.dd_3_23_1, R.string.dd_4_23_1}, {R.string.dd_1_24_1, R.string.dd_2_24_1, R.string.dd_3_24_1, R.string.dd_4_24_1}, {R.string.dd_1_25_1, R.string.dd_2_25_1, R.string.dd_3_25_1, R.string.dd_4_25_1}, {R.string.dd_1_26_1, R.string.dd_2_26_1, R.string.dd_3_26_1, R.string.dd_4_26_1}, {R.string.dd_1_27_1, R.string.dd_2_27_1, R.string.dd_3_27_1, R.string.dd_4_27_1}, {R.string.dd_1_28_1, R.string.dd_2_28_1, R.string.dd_3_28_1, R.string.dd_4_28_1}, {R.string.dd_1_29_1, R.string.dd_2_29_1, R.string.dd_3_29_1, R.string.dd_4_29_1}, {R.string.dd_1_30_1, R.string.dd_2_30_1, R.string.dd_3_30_1, R.string.dd_4_30_1}, {R.string.dd_1_31_1, R.string.dd_2_31_1, R.string.dd_3_31_1, R.string.dd_4_31_1}, {R.string.dd_1_32_1, R.string.dd_2_32_1, R.string.dd_3_32_1, R.string.dd_4_32_1}, {R.string.dd_1_33_1, R.string.dd_2_33_1, R.string.dd_3_33_1, R.string.dd_4_33_1}, {R.string.dd_1_34_1, R.string.dd_2_34_1, R.string.dd_3_34_1, R.string.dd_4_34_1}, {R.string.dd_1_35_1, R.string.dd_2_35_1, R.string.dd_3_35_1, R.string.dd_4_35_1}, {R.string.dd_1_36_1, R.string.dd_2_36_1, R.string.dd_3_36_1, R.string.dd_4_36_1}, {R.string.dd_1_37_1, R.string.dd_2_37_1, R.string.dd_3_37_1, R.string.dd_4_37_1}, {R.string.dd_1_38_1, R.string.dd_2_38_1, R.string.dd_3_38_1, R.string.dd_4_38_1}};
    int[][] details_2 = {{R.string.dd_1_01_2, R.string.dd_2_01_2, R.string.dd_3_01_2, R.string.dd_4_01_2}, {R.string.dd_1_02_2, R.string.dd_2_02_2, R.string.dd_3_02_2, R.string.dd_4_02_2}, {R.string.dd_1_03_2, R.string.dd_2_03_2, R.string.dd_3_03_2, R.string.dd_4_03_2}, {R.string.dd_1_04_2, R.string.dd_2_04_2, R.string.dd_3_04_2, R.string.dd_4_04_2}, {R.string.dd_1_05_2, R.string.dd_2_05_2, R.string.dd_3_05_2, R.string.dd_4_05_2}, {R.string.dd_1_06_2, R.string.dd_2_06_2, R.string.dd_3_06_2, R.string.dd_4_06_2}, {R.string.dd_1_07_2, R.string.dd_2_07_2, R.string.dd_3_07_2, R.string.dd_4_07_2}, {R.string.dd_1_08_2, R.string.dd_2_08_2, R.string.dd_3_08_2, R.string.dd_4_08_2}, {R.string.dd_1_09_2, R.string.dd_2_09_2, R.string.dd_3_09_2, R.string.dd_4_09_2}, {R.string.dd_1_10_2, R.string.dd_2_10_2, R.string.dd_3_10_2, R.string.dd_4_10_2}, {R.string.dd_1_11_2, R.string.dd_2_11_2, R.string.dd_3_11_2, R.string.dd_4_11_2}, {R.string.dd_1_12_2, R.string.dd_2_12_2, R.string.dd_3_12_2, R.string.dd_4_12_2}, {R.string.dd_1_13_2, R.string.dd_2_13_2, R.string.dd_3_13_2, R.string.dd_4_13_2}, {R.string.dd_1_14_2, R.string.dd_2_14_2, R.string.dd_3_14_2, R.string.dd_4_14_2}, {R.string.dd_1_15_2, R.string.dd_2_15_2, R.string.dd_3_15_2, R.string.dd_4_15_2}, {R.string.dd_1_16_2, R.string.dd_2_16_2, R.string.dd_3_16_2, R.string.dd_4_16_2}, {R.string.dd_1_17_2, R.string.dd_2_17_2, R.string.dd_3_17_2, R.string.dd_4_17_2}, {R.string.dd_1_18_2, R.string.dd_2_18_2, R.string.dd_3_18_2, R.string.dd_4_18_2}, {R.string.dd_1_19_2, R.string.dd_2_19_2, R.string.dd_3_19_2, R.string.dd_4_19_2}, {R.string.dd_1_20_2, R.string.dd_2_20_2, R.string.dd_3_20_2, R.string.dd_4_20_2}, {R.string.dd_1_21_2, R.string.dd_2_21_2, R.string.dd_3_21_2, R.string.dd_4_21_2}, {R.string.dd_1_22_2, R.string.dd_2_22_2, R.string.dd_3_22_2, R.string.dd_4_22_2}, {R.string.dd_1_23_2, R.string.dd_2_23_2, R.string.dd_3_23_2, R.string.dd_4_23_2}, {R.string.dd_1_24_2, R.string.dd_2_24_2, R.string.dd_3_24_2, R.string.dd_4_24_2}, {R.string.dd_1_25_2, R.string.dd_2_25_2, R.string.dd_3_25_2, R.string.dd_4_25_2}, {R.string.dd_1_26_2, R.string.dd_2_26_2, R.string.dd_3_26_2, R.string.dd_4_26_2}, {R.string.dd_1_27_2, R.string.dd_2_27_2, R.string.dd_3_27_2, R.string.dd_4_27_2}, {R.string.dd_1_28_2, R.string.dd_2_28_2, R.string.dd_3_28_2, R.string.dd_4_28_2}, {R.string.dd_1_29_2, R.string.dd_2_29_2, R.string.dd_3_29_2, R.string.dd_4_29_2}, {R.string.dd_1_30_2, R.string.dd_2_30_2, R.string.dd_3_30_2, R.string.dd_4_30_2}, {R.string.dd_1_31_2, R.string.dd_2_31_2, R.string.dd_3_31_2, R.string.dd_4_31_2}, {R.string.dd_1_32_2, R.string.dd_2_32_2, R.string.dd_3_32_2, R.string.dd_4_32_2}, {R.string.dd_1_33_2, R.string.dd_2_33_2, R.string.dd_3_33_2, R.string.dd_4_33_2}, {R.string.dd_1_34_2, R.string.dd_2_34_2, R.string.dd_3_34_2, R.string.dd_4_34_2}, {R.string.dd_1_35_2, R.string.dd_2_35_2, R.string.dd_3_35_2, R.string.dd_4_35_2}, {R.string.dd_1_36_2, R.string.dd_2_36_2, R.string.dd_3_36_2, R.string.dd_4_36_2}, {R.string.dd_1_37_2, R.string.dd_2_37_2, R.string.dd_3_37_2, R.string.dd_4_37_2}, {R.string.dd_1_38_2, R.string.dd_2_38_2, R.string.dd_3_38_2, R.string.dd_4_38_2}};

    int[] dImage ={R.drawable.dp01,R.drawable.dp02,R.drawable.dp03,R.drawable.dp04,R.drawable.dp05,R.drawable.dp06,R.drawable.dp07,R.drawable.dp08,R.drawable.dp09,R.drawable.dp10,R.drawable.dp11,R.drawable.dp12,R.drawable.dp13,R.drawable.dp14,R.drawable.dp15,R.drawable.dp16,R.drawable.dp17,R.drawable.dp18,R.drawable.dp19,R.drawable.dp20,R.drawable.dp21,R.drawable.dp22,R.drawable.dp23,R.drawable.dp24,R.drawable.dp25,R.drawable.dp26,R.drawable.dp27,R.drawable.dp28,R.drawable.dp29,R.drawable.dp30,R.drawable.dp31,R.drawable.dp32,R.drawable.dp33,R.drawable.dp34,R.drawable.dp35,R.drawable.dp36,R.drawable.dp37,R.drawable.dp38};
    ImageView diseaseImageView;
    ImageButton imageButton;

    TextView diseaseName;
    TextView symptomsTitle,preventionTitle,causeTitle,biologicalControlTitle;
    TextView symptomsContents,preventionContents,causeContents,biologicalControlContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_disease);

        did = getIntent().getIntExtra("did",0);


        __init__();
        selected_lang = load_setting();
        setLang(selected_lang);

    }

    private void __init__() {
        diseaseImageView = findViewById(R.id.DiseaseImageView);
        imageButton = findViewById(R.id.imageButton);

        diseaseName = findViewById(R.id.diseaseName);

        symptomsTitle = findViewById(R.id.symptomsTitle);
        preventionTitle = findViewById(R.id.preventionTitle);
        causeTitle = findViewById(R.id.causeTitle);
        biologicalControlTitle = findViewById(R.id.biologicalControlTitle);
        symptomsContents = findViewById(R.id.symptomsContents);
        preventionContents = findViewById(R.id.preventionContents);
        causeContents = findViewById(R.id.causeContents);
        biologicalControlContents = findViewById(R.id.biologicalControlContents);
    }


    private void setLang(int selected_lang){

        diseaseImageView.setImageResource(dImage[did - 1]);
        switch (selected_lang)
        {
            case 0:
            {
                diseaseName.setText(getString(stringDnames_0[did - 1]));

                symptomsTitle.setText(getString(title[0]));
                preventionTitle.setText(getString(title[3]));
                causeTitle.setText(getString(title[6]));
                biologicalControlTitle.setText(getString(title[9]));

                symptomsContents.setText(getString(details_0[did-1][0]));
                preventionContents.setText(getString(details_0[did-1][1]));
                causeContents.setText(getString(details_0[did-1][2]));
                biologicalControlContents.setText(getString(details_0[did-1][3]));
            }
            break;
            case 1:
            {
                diseaseName.setText(getString(stringDnames_1[did - 1]));

                symptomsTitle.setText(getString(title[1]));
                preventionTitle.setText(getString(title[4]));
                causeTitle.setText(getString(title[7]));
                biologicalControlTitle.setText(getString(title[10]));

                symptomsContents.setText(getString(details_1[did-1][0]));
                preventionContents.setText(getString(details_1[did-1][1]));
                causeContents.setText(getString(details_1[did-1][2]));
                biologicalControlContents.setText(getString(details_1[did-1][3]));
            }
            break;
            case 2:
            {
                diseaseName.setText(getString(stringDnames_2[did - 1]));

                symptomsTitle.setText(getString(title[2]));
                preventionTitle.setText(getString(title[5]));
                causeTitle.setText(getString(title[8]));
                biologicalControlTitle.setText(getString(title[11]));

                symptomsContents.setText(getString(details_2[did-1][0]));
                preventionContents.setText(getString(details_2[did-1][1]));
                causeContents.setText(getString(details_2[did-1][2]));
                biologicalControlContents.setText(getString(details_2[did-1][3]));
            }
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

        switch (v.getId())
        {
            case R.id.BackImageButton:finish();break;

            case R.id.DiseaseImageView:
                Intent intent  = new Intent(this,ImageSlider.class);
                intent.putExtra("id",did);
                startActivity(intent);
                break;
        }
    }
}
