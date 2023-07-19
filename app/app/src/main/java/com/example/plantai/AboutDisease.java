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
    String selected_lang;
    int did;

    int[] stringDnames = {R.string.dname_01,R.string.dname_02,R.string.dname_03, R.string.dname_04,R.string.dname_05,R.string.dname_06,R.string.dname_07,R.string.dname_08,R.string.dname_09,R.string.dname_10,R.string.dname_11,R.string.dname_12,R.string.dname_13,R.string.dname_14,R.string.dname_15,R.string.dname_16,R.string.dname_17,R.string.dname_18,R.string.dname_19,R.string.dname_20,R.string.dname_21,R.string.dname_22,R.string.dname_23,R.string.dname_24,R.string.dname_25,R.string.dname_26,R.string.dname_27,R.string.dname_28,R.string.dname_29,R.string.dname_30,R.string.dname_31,R.string.dname_32,R.string.dname_33,R.string.dname_34,R.string.dname_35,R.string.dname_36,R.string.dname_37,R.string.dname_38,R.string.dname_39};

    int[] symptoms = {R.string.symptoms_01, R.string.symptoms_02, R.string.symptoms_03, R.string.symptoms_04, R.string.symptoms_05, R.string.symptoms_06, R.string.symptoms_07, R.string.symptoms_08, R.string.symptoms_09, R.string.symptoms_10, R.string.symptoms_11, R.string.symptoms_12, R.string.symptoms_13, R.string.symptoms_14, R.string.symptoms_15, R.string.symptoms_16, R.string.symptoms_17, R.string.symptoms_18, R.string.symptoms_19, R.string.symptoms_20, R.string.symptoms_21, R.string.symptoms_22, R.string.symptoms_23, R.string.symptoms_24, R.string.symptoms_25, R.string.symptoms_26, R.string.symptoms_27, R.string.symptoms_28, R.string.symptoms_29, R.string.symptoms_30, R.string.symptoms_31, R.string.symptoms_32, R.string.symptoms_33, R.string.symptoms_34, R.string.symptoms_35, R.string.symptoms_36, R.string.symptoms_37, R.string.symptoms_38, R.string.symptoms_38};
    int[] prevention = {R.string.prevention_01, R.string.prevention_02, R.string.prevention_03, R.string.prevention_04, R.string.prevention_05, R.string.prevention_06, R.string.prevention_07, R.string.prevention_08, R.string.prevention_09, R.string.prevention_10, R.string.prevention_11, R.string.prevention_12, R.string.prevention_13, R.string.prevention_14, R.string.prevention_15, R.string.prevention_16, R.string.prevention_17, R.string.prevention_18, R.string.prevention_19, R.string.prevention_20, R.string.prevention_21, R.string.prevention_22, R.string.prevention_23, R.string.prevention_24, R.string.prevention_25, R.string.prevention_26, R.string.prevention_27, R.string.prevention_28, R.string.prevention_29, R.string.prevention_30, R.string.prevention_31, R.string.prevention_32, R.string.prevention_33, R.string.prevention_34, R.string.prevention_35, R.string.prevention_36, R.string.prevention_37, R.string.prevention_38, R.string.prevention_39};
    int[] cause = {R.string.cause_01, R.string.cause_02, R.string.cause_03, R.string.cause_04, R.string.cause_05, R.string.cause_06, R.string.cause_07, R.string.cause_08, R.string.cause_09, R.string.cause_10, R.string.cause_11, R.string.cause_12, R.string.cause_13, R.string.cause_14, R.string.cause_15, R.string.cause_16, R.string.cause_17, R.string.cause_18, R.string.cause_19, R.string.cause_20, R.string.cause_21, R.string.cause_22, R.string.cause_23, R.string.cause_24, R.string.cause_25, R.string.cause_26, R.string.cause_27, R.string.cause_28, R.string.cause_29, R.string.cause_30, R.string.cause_31, R.string.cause_32, R.string.cause_33, R.string.cause_34, R.string.cause_35, R.string.cause_36, R.string.cause_37, R.string.cause_38, R.string.cause_39};
    int[] biological = {R.string.biological_01, R.string.biological_02, R.string.biological_03, R.string.biological_04, R.string.biological_05, R.string.biological_06, R.string.biological_07, R.string.biological_08, R.string.biological_09, R.string.biological_10, R.string.biological_11, R.string.biological_12, R.string.biological_13, R.string.biological_14, R.string.biological_15, R.string.biological_16, R.string.biological_17, R.string.biological_18, R.string.biological_19, R.string.biological_20, R.string.biological_21, R.string.biological_22, R.string.biological_23, R.string.biological_24, R.string.biological_25, R.string.biological_26, R.string.biological_27, R.string.biological_28, R.string.biological_29, R.string.biological_30, R.string.biological_31, R.string.biological_32, R.string.biological_33, R.string.biological_34, R.string.biological_35, R.string.biological_36, R.string.biological_37, R.string.biological_38, R.string.biological_39};

    int[] dImage = {
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
        setLang();

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


    private void setLang(){

        diseaseImageView.setImageResource(dImage[did - 1]);

        diseaseName.setText(getString(stringDnames[did - 1]));
        symptomsTitle.setText(getString(R.string.symptoms));
        preventionTitle.setText(getString(R.string.prevention));
        causeTitle.setText(getString(R.string.cause));
        biologicalControlTitle.setText(getString(R.string.bcontrol));

        symptomsContents.setText(getString(symptoms[did-1]));
        preventionContents.setText(getString(prevention[did-1]));
        causeContents.setText(getString(cause[did-1]));
        biologicalControlContents.setText(getString(biological[did-1]));
    }

    //TODO Load Setting
    String load_setting()
    {
        SharedPreferences mySharedPreference = getSharedPreferences(SHARED,MODE_PRIVATE);
        return mySharedPreference.getString(SHARED_APP_LANG,"en");
    }



    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.BackImageButton) {
            finish();
        } else if (v.getId() == R.id.DiseaseImageView) {
            Intent intent = new Intent(this, ImageSlider.class);
            intent.putExtra("id", did);
            startActivity(intent);
        }

    }
}
