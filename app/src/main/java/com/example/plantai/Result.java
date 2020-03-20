package com.example.plantai;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


public class Result extends AppCompatActivity implements View.OnClickListener{

    private static final String SHARED = "Settings";
    private static final String SHARED_APP_LANG = "app_lang";
    int selected_lang;


    String jsonData;
    int[] max = new int[3];
    Double[] probability= new Double[38];

    //ProgressBar progressBar;
    RelativeLayout relativeLayout01,relativeLayout02,relativeLayout03,relativeLayout04,relativeLayout05;
    RelativeLayout relativeLayout06,relativeLayout07,relativeLayout08,relativeLayout09,relativeLayout10;
    RelativeLayout relativeLayout11,relativeLayout12,relativeLayout13,relativeLayout14,relativeLayout15;
    RelativeLayout relativeLayout16,relativeLayout17,relativeLayout18,relativeLayout19,relativeLayout20;
    RelativeLayout relativeLayout21,relativeLayout22,relativeLayout23,relativeLayout24,relativeLayout25;
    RelativeLayout relativeLayout26,relativeLayout27,relativeLayout28,relativeLayout29,relativeLayout30;
    RelativeLayout relativeLayout31,relativeLayout32,relativeLayout33,relativeLayout34,relativeLayout35;
    RelativeLayout relativeLayout36,relativeLayout37,relativeLayout38;
    ImageView iv01,iv02,iv03,iv04,iv05,iv06,iv07,iv08,iv09,iv10,iv11,iv12,iv13,iv14,iv15;
    ImageView iv16,iv17,iv18,iv19,iv20,iv21,iv22,iv23,iv24,iv25,iv26,iv27,iv28,iv29,iv30;
    ImageView iv31,iv32,iv33,iv34,iv35,iv36,iv37,iv38,uiv;
    TextView tv01,tv02,tv03,tv04,tv05,tv06,tv07,tv08,tv09,tv10,tv11,tv12,tv13,tv14,tv15;
    TextView tv16,tv17,tv18,tv19,tv20,tv21,tv22,tv23,tv24,tv25,tv26,tv27,tv28,tv29,tv30;
    TextView tv31,tv32,tv33,tv34,tv35,tv36,tv37,tv38;
    Button b01,b02,b03,b04,b05,b06,b07,b08,b09,b10,b11,b12,b13,b14,b15;
    Button b16,b17,b18,b19,b20,b21,b22,b23,b24,b25,b26,b27,b28,b29,b30;
    Button b31,b32,b33,b34,b35,b36,b37,b38;
    ImageButton ib;
    TextView textView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        __init__();
        selected_lang = load_setting();
        setLang(selected_lang);

        jsonData = getIntent().getStringExtra("jsonresult");
        Log.e("RESULT FROM RESULT " , jsonData);
        try {
            JSONArray array = new JSONArray(jsonData);
            JSONArray jmax = (JSONArray) array.get(1);
            JSONArray jprob = (JSONArray) array.get(2);
            for(int i=0;i<=2;i++)
            {
                max[i] = (int) jmax.get(i);
                probability[i] = (Double) jprob.get(max[i]);
                probability[i] *= 100;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("max",probability[0] + " " + probability[1]+ " " + probability[2]);

        //SET TEXT VIEW
        {
            textView = new TextView(Result.this);
            textView.setLayoutParams(new RelativeLayout.LayoutParams(WRAP_CONTENT,WRAP_CONTENT));
            textView.setTextColor(getColor(R.color.colorWhite));
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setTextSize(25);
            textView.setBackground(getDrawable(R.drawable.button_fill2));
            textView.setPadding(13,30,13,13);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) textView.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            textView.setLayoutParams(params);
        }
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


            //display disease name
        for(int i=0;i<3;i++)
        {
            if((probability[i]) < 50 && i!=0)
                continue;
            textView.setText(probability[i].intValue()+"%");

            switch(max[i])
            {

                case 0:relativeLayout01.setVisibility(View.VISIBLE);relativeLayout01.addView(textView);break;
                case 1:relativeLayout02.setVisibility(View.VISIBLE);relativeLayout02.addView(textView);break;
                case 2:relativeLayout03.setVisibility(View.VISIBLE);relativeLayout03.addView(textView);break;
                case 3:relativeLayout04.setVisibility(View.VISIBLE);relativeLayout04.addView(textView);break;
                case 4:relativeLayout05.setVisibility(View.VISIBLE);relativeLayout05.addView(textView);break;
                case 5:relativeLayout06.setVisibility(View.VISIBLE);relativeLayout06.addView(textView);break;
                case 6:relativeLayout07.setVisibility(View.VISIBLE);relativeLayout07.addView(textView);break;
                case 7:relativeLayout08.setVisibility(View.VISIBLE);relativeLayout08.addView(textView);break;
                case 8:relativeLayout09.setVisibility(View.VISIBLE);relativeLayout09.addView(textView);break;
                case 9:relativeLayout10.setVisibility(View.VISIBLE);relativeLayout10.addView(textView);break;
                case 10:relativeLayout11.setVisibility(View.VISIBLE);relativeLayout11.addView(textView);break;
                case 11:relativeLayout12.setVisibility(View.VISIBLE);relativeLayout12.addView(textView);break;
                case 12:relativeLayout13.setVisibility(View.VISIBLE);relativeLayout13.addView(textView);break;
                case 13:relativeLayout14.setVisibility(View.VISIBLE);relativeLayout14.addView(textView);break;
                case 14:relativeLayout15.setVisibility(View.VISIBLE);relativeLayout15.addView(textView);break;
                case 15:relativeLayout16.setVisibility(View.VISIBLE);relativeLayout16.addView(textView);break;
                case 16:relativeLayout17.setVisibility(View.VISIBLE);relativeLayout17.addView(textView);break;
                case 17:relativeLayout18.setVisibility(View.VISIBLE);relativeLayout18.addView(textView);break;
                case 18:relativeLayout19.setVisibility(View.VISIBLE);relativeLayout19.addView(textView);break;
                case 19:relativeLayout20.setVisibility(View.VISIBLE);relativeLayout20.addView(textView);break;
                case 20:relativeLayout21.setVisibility(View.VISIBLE);relativeLayout21.addView(textView);break;
                case 21:relativeLayout22.setVisibility(View.VISIBLE);relativeLayout22.addView(textView);break;
                case 22:relativeLayout23.setVisibility(View.VISIBLE);relativeLayout23.addView(textView);break;
                case 23:relativeLayout24.setVisibility(View.VISIBLE);relativeLayout24.addView(textView);break;
                case 24:relativeLayout25.setVisibility(View.VISIBLE);relativeLayout25.addView(textView);break;
                case 25:relativeLayout26.setVisibility(View.VISIBLE);relativeLayout26.addView(textView);break;
                case 26:relativeLayout27.setVisibility(View.VISIBLE);relativeLayout27.addView(textView);break;
                case 27:relativeLayout28.setVisibility(View.VISIBLE);relativeLayout28.addView(textView);break;
                case 28:relativeLayout29.setVisibility(View.VISIBLE);relativeLayout29.addView(textView);break;
                case 29:relativeLayout30.setVisibility(View.VISIBLE);relativeLayout30.addView(textView);break;
                case 30:relativeLayout31.setVisibility(View.VISIBLE);relativeLayout31.addView(textView);break;
                case 31:relativeLayout32.setVisibility(View.VISIBLE);relativeLayout32.addView(textView);break;
                case 32:relativeLayout33.setVisibility(View.VISIBLE);relativeLayout33.addView(textView);break;
                case 33:relativeLayout34.setVisibility(View.VISIBLE);relativeLayout34.addView(textView);break;
                case 34:relativeLayout35.setVisibility(View.VISIBLE);relativeLayout35.addView(textView);break;
                case 35:relativeLayout36.setVisibility(View.VISIBLE);relativeLayout36.addView(textView);break;
                case 36:relativeLayout37.setVisibility(View.VISIBLE);relativeLayout37.addView(textView);break;
                case 37:relativeLayout38.setVisibility(View.VISIBLE);relativeLayout38.addView(textView);break;
            }
        }


    }

    private void setLang(int selected_lang){
        switch (selected_lang)
        {
            case 0:{
                {b01.setText(getString(R.string.readmore_0));
                    b02.setText(getString(R.string.readmore_0));
                    b03.setText(getString(R.string.readmore_0));
                    b04.setText(getString(R.string.readmore_0));
                    b05.setText(getString(R.string.readmore_0));
                    b06.setText(getString(R.string.readmore_0));
                    b07.setText(getString(R.string.readmore_0));
                    b08.setText(getString(R.string.readmore_0));
                    b09.setText(getString(R.string.readmore_0));
                    b10.setText(getString(R.string.readmore_0));
                    b11.setText(getString(R.string.readmore_0));
                    b12.setText(getString(R.string.readmore_0));
                    b13.setText(getString(R.string.readmore_0));
                    b14.setText(getString(R.string.readmore_0));
                    b15.setText(getString(R.string.readmore_0));
                    b16.setText(getString(R.string.readmore_0));
                    b17.setText(getString(R.string.readmore_0));
                    b18.setText(getString(R.string.readmore_0));
                    b19.setText(getString(R.string.readmore_0));
                    b20.setText(getString(R.string.readmore_0));
                    b21.setText(getString(R.string.readmore_0));
                    b22.setText(getString(R.string.readmore_0));
                    b23.setText(getString(R.string.readmore_0));
                    b24.setText(getString(R.string.readmore_0));
                    b25.setText(getString(R.string.readmore_0));
                    b26.setText(getString(R.string.readmore_0));
                    b27.setText(getString(R.string.readmore_0));
                    b28.setText(getString(R.string.readmore_0));
                    b29.setText(getString(R.string.readmore_0));
                    b30.setText(getString(R.string.readmore_0));
                    b31.setText(getString(R.string.readmore_0));
                    b32.setText(getString(R.string.readmore_0));
                    b33.setText(getString(R.string.readmore_0));
                    b34.setText(getString(R.string.readmore_0));
                    b35.setText(getString(R.string.readmore_0));
                    b36.setText(getString(R.string.readmore_0));
                    b37.setText(getString(R.string.readmore_0));
                    b38.setText(getString(R.string.readmore_0));}
                {
                    tv01.setText(getString(R.string.dname_01_0));
                    tv02.setText(getString(R.string.dname_02_0));
                    tv03.setText(getString(R.string.dname_03_0));
                    tv04.setText(getString(R.string.dname_04_0));
                    tv05.setText(getString(R.string.dname_05_0));
                    tv06.setText(getString(R.string.dname_06_0));
                    tv07.setText(getString(R.string.dname_07_0));
                    tv08.setText(getString(R.string.dname_08_0));
                    tv09.setText(getString(R.string.dname_09_0));
                    tv10.setText(getString(R.string.dname_10_0));
                    tv11.setText(getString(R.string.dname_11_0));
                    tv12.setText(getString(R.string.dname_12_0));
                    tv13.setText(getString(R.string.dname_13_0));
                    tv14.setText(getString(R.string.dname_14_0));
                    tv15.setText(getString(R.string.dname_15_0));
                    tv16.setText(getString(R.string.dname_16_0));
                    tv17.setText(getString(R.string.dname_17_0));
                    tv18.setText(getString(R.string.dname_18_0));
                    tv19.setText(getString(R.string.dname_19_0));
                    tv20.setText(getString(R.string.dname_20_0));
                    tv21.setText(getString(R.string.dname_21_0));
                    tv22.setText(getString(R.string.dname_22_0));
                    tv23.setText(getString(R.string.dname_23_0));
                    tv24.setText(getString(R.string.dname_24_0));
                    tv25.setText(getString(R.string.dname_25_0));
                    tv26.setText(getString(R.string.dname_26_0));
                    tv27.setText(getString(R.string.dname_27_0));
                    tv28.setText(getString(R.string.dname_28_0));
                    tv29.setText(getString(R.string.dname_29_0));
                    tv30.setText(getString(R.string.dname_30_0));
                    tv31.setText(getString(R.string.dname_31_0));
                    tv32.setText(getString(R.string.dname_32_0));
                    tv33.setText(getString(R.string.dname_33_0));
                    tv34.setText(getString(R.string.dname_34_0));
                    tv35.setText(getString(R.string.dname_35_0));
                    tv36.setText(getString(R.string.dname_36_0));
                    tv37.setText(getString(R.string.dname_37_0));
                    tv38.setText(getString(R.string.dname_38_0));
                }
            }
            break;
            case 1:{
                {b01.setText(getString(R.string.readmore_1));
                b02.setText(getString(R.string.readmore_1));
                b03.setText(getString(R.string.readmore_1));
                b04.setText(getString(R.string.readmore_1));
                b05.setText(getString(R.string.readmore_1));
                b06.setText(getString(R.string.readmore_1));
                b07.setText(getString(R.string.readmore_1));
                b08.setText(getString(R.string.readmore_1));
                b09.setText(getString(R.string.readmore_1));
                b10.setText(getString(R.string.readmore_1));
                b11.setText(getString(R.string.readmore_1));
                b12.setText(getString(R.string.readmore_1));
                b13.setText(getString(R.string.readmore_1));
                b14.setText(getString(R.string.readmore_1));
                b15.setText(getString(R.string.readmore_1));
                b16.setText(getString(R.string.readmore_1));
                b17.setText(getString(R.string.readmore_1));
                b18.setText(getString(R.string.readmore_1));
                b19.setText(getString(R.string.readmore_1));
                b20.setText(getString(R.string.readmore_1));
                b21.setText(getString(R.string.readmore_1));
                b22.setText(getString(R.string.readmore_1));
                b23.setText(getString(R.string.readmore_1));
                b24.setText(getString(R.string.readmore_1));
                b25.setText(getString(R.string.readmore_1));
                b26.setText(getString(R.string.readmore_1));
                b27.setText(getString(R.string.readmore_1));
                b28.setText(getString(R.string.readmore_1));
                b29.setText(getString(R.string.readmore_1));
                b30.setText(getString(R.string.readmore_1));
                b31.setText(getString(R.string.readmore_1));
                b32.setText(getString(R.string.readmore_1));
                b33.setText(getString(R.string.readmore_1));
                b34.setText(getString(R.string.readmore_1));
                b35.setText(getString(R.string.readmore_1));
                b36.setText(getString(R.string.readmore_1));
                b37.setText(getString(R.string.readmore_1));
                b38.setText(getString(R.string.readmore_1));}
                {
                    tv01.setText(getString(R.string.dname_01_1));
                    tv02.setText(getString(R.string.dname_02_1));
                    tv03.setText(getString(R.string.dname_03_1));
                    tv04.setText(getString(R.string.dname_04_1));
                    tv05.setText(getString(R.string.dname_05_1));
                    tv06.setText(getString(R.string.dname_06_1));
                    tv07.setText(getString(R.string.dname_07_1));
                    tv08.setText(getString(R.string.dname_08_1));
                    tv09.setText(getString(R.string.dname_09_1));
                    tv10.setText(getString(R.string.dname_10_1));
                    tv11.setText(getString(R.string.dname_11_1));
                    tv12.setText(getString(R.string.dname_12_1));
                    tv13.setText(getString(R.string.dname_13_1));
                    tv14.setText(getString(R.string.dname_14_1));
                    tv15.setText(getString(R.string.dname_15_1));
                    tv16.setText(getString(R.string.dname_16_1));
                    tv17.setText(getString(R.string.dname_17_1));
                    tv18.setText(getString(R.string.dname_18_1));
                    tv19.setText(getString(R.string.dname_19_1));
                    tv20.setText(getString(R.string.dname_20_1));
                    tv21.setText(getString(R.string.dname_21_1));
                    tv22.setText(getString(R.string.dname_22_1));
                    tv23.setText(getString(R.string.dname_23_1));
                    tv24.setText(getString(R.string.dname_24_1));
                    tv25.setText(getString(R.string.dname_25_1));
                    tv26.setText(getString(R.string.dname_26_1));
                    tv27.setText(getString(R.string.dname_27_1));
                    tv28.setText(getString(R.string.dname_28_1));
                    tv29.setText(getString(R.string.dname_29_1));
                    tv30.setText(getString(R.string.dname_30_1));
                    tv31.setText(getString(R.string.dname_31_1));
                    tv32.setText(getString(R.string.dname_32_1));
                    tv33.setText(getString(R.string.dname_33_1));
                    tv34.setText(getString(R.string.dname_34_1));
                    tv35.setText(getString(R.string.dname_35_1));
                    tv36.setText(getString(R.string.dname_36_1));
                    tv37.setText(getString(R.string.dname_37_1));
                    tv38.setText(getString(R.string.dname_38_1));}
            }
            break;
            case 2:{
                {b01.setText(getString(R.string.readmore_2));
                b02.setText(getString(R.string.readmore_2));
                b03.setText(getString(R.string.readmore_2));
                b04.setText(getString(R.string.readmore_2));
                b05.setText(getString(R.string.readmore_2));
                b06.setText(getString(R.string.readmore_2));
                b07.setText(getString(R.string.readmore_2));
                b08.setText(getString(R.string.readmore_2));
                b09.setText(getString(R.string.readmore_2));
                b10.setText(getString(R.string.readmore_2));
                b11.setText(getString(R.string.readmore_2));
                b12.setText(getString(R.string.readmore_2));
                b13.setText(getString(R.string.readmore_2));
                b14.setText(getString(R.string.readmore_2));
                b15.setText(getString(R.string.readmore_2));
                b16.setText(getString(R.string.readmore_2));
                b17.setText(getString(R.string.readmore_2));
                b18.setText(getString(R.string.readmore_2));
                b19.setText(getString(R.string.readmore_2));
                b20.setText(getString(R.string.readmore_2));
                b21.setText(getString(R.string.readmore_2));
                b22.setText(getString(R.string.readmore_2));
                b23.setText(getString(R.string.readmore_2));
                b24.setText(getString(R.string.readmore_2));
                b25.setText(getString(R.string.readmore_2));
                b26.setText(getString(R.string.readmore_2));
                b27.setText(getString(R.string.readmore_2));
                b28.setText(getString(R.string.readmore_2));
                b29.setText(getString(R.string.readmore_2));
                b30.setText(getString(R.string.readmore_2));
                b31.setText(getString(R.string.readmore_2));
                b32.setText(getString(R.string.readmore_2));
                b33.setText(getString(R.string.readmore_2));
                b34.setText(getString(R.string.readmore_2));
                b35.setText(getString(R.string.readmore_2));
                b36.setText(getString(R.string.readmore_2));
                b37.setText(getString(R.string.readmore_2));
                b38.setText(getString(R.string.readmore_2));}
                {
                    tv01.setText(getString(R.string.dname_01_2));
                    tv02.setText(getString(R.string.dname_02_2));
                    tv03.setText(getString(R.string.dname_03_2));
                    tv04.setText(getString(R.string.dname_04_2));
                    tv05.setText(getString(R.string.dname_05_2));
                    tv06.setText(getString(R.string.dname_06_2));
                    tv07.setText(getString(R.string.dname_07_2));
                    tv08.setText(getString(R.string.dname_08_2));
                    tv09.setText(getString(R.string.dname_09_2));
                    tv10.setText(getString(R.string.dname_10_2));
                    tv11.setText(getString(R.string.dname_11_2));
                    tv12.setText(getString(R.string.dname_12_2));
                    tv13.setText(getString(R.string.dname_13_2));
                    tv14.setText(getString(R.string.dname_14_2));
                    tv15.setText(getString(R.string.dname_15_2));
                    tv16.setText(getString(R.string.dname_16_2));
                    tv17.setText(getString(R.string.dname_17_2));
                    tv18.setText(getString(R.string.dname_18_2));
                    tv19.setText(getString(R.string.dname_19_2));
                    tv20.setText(getString(R.string.dname_20_2));
                    tv21.setText(getString(R.string.dname_21_2));
                    tv22.setText(getString(R.string.dname_22_2));
                    tv23.setText(getString(R.string.dname_23_2));
                    tv24.setText(getString(R.string.dname_24_2));
                    tv25.setText(getString(R.string.dname_25_2));
                    tv26.setText(getString(R.string.dname_26_2));
                    tv27.setText(getString(R.string.dname_27_2));
                    tv28.setText(getString(R.string.dname_28_2));
                    tv29.setText(getString(R.string.dname_29_2));
                    tv30.setText(getString(R.string.dname_30_2));
                    tv31.setText(getString(R.string.dname_31_2));
                    tv32.setText(getString(R.string.dname_32_2));
                    tv33.setText(getString(R.string.dname_33_2));
                    tv34.setText(getString(R.string.dname_34_2));
                    tv35.setText(getString(R.string.dname_35_2));
                    tv36.setText(getString(R.string.dname_36_2));
                    tv37.setText(getString(R.string.dname_37_2));
                    tv38.setText(getString(R.string.dname_38_2));
                }
            }
            break;
        }
    }

    private void __init__() {
        //progressBar = findViewById(R.id.progressBar);
        relativeLayout01 = findViewById(R.id.ResultCard01);
        relativeLayout02 = findViewById(R.id.ResultCard02);
        relativeLayout03 = findViewById(R.id.ResultCard03);
        relativeLayout04 = findViewById(R.id.ResultCard04);
        relativeLayout05 = findViewById(R.id.ResultCard05);
        relativeLayout06 = findViewById(R.id.ResultCard06);
        relativeLayout07 = findViewById(R.id.ResultCard07);
        relativeLayout08 = findViewById(R.id.ResultCard08);
        relativeLayout09 = findViewById(R.id.ResultCard09);
        relativeLayout10 = findViewById(R.id.ResultCard10);
        relativeLayout11 = findViewById(R.id.ResultCard11);
        relativeLayout12 = findViewById(R.id.ResultCard12);
        relativeLayout13 = findViewById(R.id.ResultCard13);
        relativeLayout14 = findViewById(R.id.ResultCard14);
        relativeLayout15 = findViewById(R.id.ResultCard15);
        relativeLayout16 = findViewById(R.id.ResultCard16);
        relativeLayout17 = findViewById(R.id.ResultCard17);
        relativeLayout18 = findViewById(R.id.ResultCard18);
        relativeLayout19 = findViewById(R.id.ResultCard19);
        relativeLayout20 = findViewById(R.id.ResultCard20);
        relativeLayout21 = findViewById(R.id.ResultCard21);
        relativeLayout22 = findViewById(R.id.ResultCard22);
        relativeLayout23 = findViewById(R.id.ResultCard23);
        relativeLayout24 = findViewById(R.id.ResultCard24);
        relativeLayout25 = findViewById(R.id.ResultCard25);
        relativeLayout26 = findViewById(R.id.ResultCard26);
        relativeLayout27 = findViewById(R.id.ResultCard27);
        relativeLayout28 = findViewById(R.id.ResultCard28);
        relativeLayout29 = findViewById(R.id.ResultCard29);
        relativeLayout30 = findViewById(R.id.ResultCard30);
        relativeLayout31 = findViewById(R.id.ResultCard31);
        relativeLayout32 = findViewById(R.id.ResultCard32);
        relativeLayout33 = findViewById(R.id.ResultCard33);
        relativeLayout34 = findViewById(R.id.ResultCard34);
        relativeLayout35 = findViewById(R.id.ResultCard35);
        relativeLayout36 = findViewById(R.id.ResultCard36);
        relativeLayout37 = findViewById(R.id.ResultCard37);
        relativeLayout38 = findViewById(R.id.ResultCard38);

        iv01 = findViewById(R.id.ResultPic01);
        iv02 = findViewById(R.id.ResultPic02);
        iv03 = findViewById(R.id.ResultPic03);
        iv04 = findViewById(R.id.ResultPic04);
        iv05 = findViewById(R.id.ResultPic05);
        iv06 = findViewById(R.id.ResultPic06);
        iv07 = findViewById(R.id.ResultPic07);
        iv08 = findViewById(R.id.ResultPic08);
        iv09 = findViewById(R.id.ResultPic09);
        iv10 = findViewById(R.id.ResultPic10);
        iv11 = findViewById(R.id.ResultPic11);
        iv12 = findViewById(R.id.ResultPic12);
        iv13 = findViewById(R.id.ResultPic13);
        iv14 = findViewById(R.id.ResultPic14);
        iv15 = findViewById(R.id.ResultPic15);
        iv16 = findViewById(R.id.ResultPic16);
        iv17 = findViewById(R.id.ResultPic17);
        iv18 = findViewById(R.id.ResultPic18);
        iv19 = findViewById(R.id.ResultPic19);
        iv20 = findViewById(R.id.ResultPic20);
        iv21 = findViewById(R.id.ResultPic21);
        iv22 = findViewById(R.id.ResultPic22);
        iv23 = findViewById(R.id.ResultPic23);
        iv24 = findViewById(R.id.ResultPic24);
        iv25 = findViewById(R.id.ResultPic25);
        iv26 = findViewById(R.id.ResultPic26);
        iv27 = findViewById(R.id.ResultPic27);
        iv28 = findViewById(R.id.ResultPic28);
        iv29 = findViewById(R.id.ResultPic29);
        iv30 = findViewById(R.id.ResultPic30);
        iv31 = findViewById(R.id.ResultPic31);
        iv32 = findViewById(R.id.ResultPic32);
        iv33 = findViewById(R.id.ResultPic33);
        iv34 = findViewById(R.id.ResultPic34);
        iv35 = findViewById(R.id.ResultPic35);
        iv36 = findViewById(R.id.ResultPic36);
        iv37 = findViewById(R.id.ResultPic37);
        iv38 = findViewById(R.id.ResultPic38);
        uiv  = findViewById(R.id.userImageView);

        tv01 = findViewById(R.id.ResultName01);
        tv02 = findViewById(R.id.ResultName02);
        tv03 = findViewById(R.id.ResultName03);
        tv04 = findViewById(R.id.ResultName04);
        tv05 = findViewById(R.id.ResultName05);
        tv06 = findViewById(R.id.ResultName06);
        tv07 = findViewById(R.id.ResultName07);
        tv08 = findViewById(R.id.ResultName08);
        tv09 = findViewById(R.id.ResultName09);
        tv10 = findViewById(R.id.ResultName10);
        tv11 = findViewById(R.id.ResultName11);
        tv12 = findViewById(R.id.ResultName12);
        tv13 = findViewById(R.id.ResultName13);
        tv14 = findViewById(R.id.ResultName14);
        tv15 = findViewById(R.id.ResultName15);
        tv16 = findViewById(R.id.ResultName16);
        tv17 = findViewById(R.id.ResultName17);
        tv18 = findViewById(R.id.ResultName18);
        tv19 = findViewById(R.id.ResultName19);
        tv20 = findViewById(R.id.ResultName20);
        tv21 = findViewById(R.id.ResultName21);
        tv22 = findViewById(R.id.ResultName22);
        tv23 = findViewById(R.id.ResultName23);
        tv24 = findViewById(R.id.ResultName24);
        tv25 = findViewById(R.id.ResultName25);
        tv26 = findViewById(R.id.ResultName26);
        tv27 = findViewById(R.id.ResultName27);
        tv28 = findViewById(R.id.ResultName28);
        tv29 = findViewById(R.id.ResultName29);
        tv30 = findViewById(R.id.ResultName30);
        tv31 = findViewById(R.id.ResultName31);
        tv32 = findViewById(R.id.ResultName32);
        tv33 = findViewById(R.id.ResultName33);
        tv34 = findViewById(R.id.ResultName34);
        tv35 = findViewById(R.id.ResultName35);
        tv36 = findViewById(R.id.ResultName36);
        tv37 = findViewById(R.id.ResultName37);
        tv38 = findViewById(R.id.ResultName38);

        b01 = findViewById(R.id.btn01);
        b02 = findViewById(R.id.btn02);
        b03 = findViewById(R.id.btn03);
        b04 = findViewById(R.id.btn04);
        b05 = findViewById(R.id.btn05);
        b06 = findViewById(R.id.btn06);
        b07 = findViewById(R.id.btn07);
        b08 = findViewById(R.id.btn08);
        b09 = findViewById(R.id.btn09);
        b10 = findViewById(R.id.btn10);
        b11 = findViewById(R.id.btn11);
        b12 = findViewById(R.id.btn12);
        b13 = findViewById(R.id.btn13);
        b14 = findViewById(R.id.btn14);
        b15 = findViewById(R.id.btn15);
        b16 = findViewById(R.id.btn16);
        b17 = findViewById(R.id.btn17);
        b18 = findViewById(R.id.btn18);
        b19 = findViewById(R.id.btn19);
        b20 = findViewById(R.id.btn20);
        b21 = findViewById(R.id.btn21);
        b22 = findViewById(R.id.btn22);
        b23 = findViewById(R.id.btn23);
        b24 = findViewById(R.id.btn24);
        b25 = findViewById(R.id.btn25);
        b26 = findViewById(R.id.btn26);
        b27 = findViewById(R.id.btn27);
        b28 = findViewById(R.id.btn28);
        b29 = findViewById(R.id.btn29);
        b30 = findViewById(R.id.btn30);
        b31 = findViewById(R.id.btn31);
        b32 = findViewById(R.id.btn32);
        b33 = findViewById(R.id.btn33);
        b34 = findViewById(R.id.btn34);
        b35 = findViewById(R.id.btn35);
        b36 = findViewById(R.id.btn36);
        b37 = findViewById(R.id.btn37);
        b38 = findViewById(R.id.btn38);
        ib  = findViewById(R.id.imageButton);

    }


    //TODO Load Setting
    int load_setting()
    {
        SharedPreferences mySharedPreference = getSharedPreferences(SHARED,MODE_PRIVATE);
        int i = mySharedPreference.getInt(SHARED_APP_LANG,0);
        return i;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Result.this,Home.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId())
        {
            case R.id.imageButton:{intent = new Intent(Result.this,Home.class);
                Log.d("test","image  Button press");
            }break;
            case R.id.userImageView:
            {
                intent = new Intent(Result.this,ImageSlider.class);
                intent.putExtra("id",999);
            }
                break;
            case R.id.btn01:
            {
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",1);
            }break;
            case R.id.btn02:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",2);
            }break;
            case R.id.btn03:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",3);
            }break;
            case R.id.btn04:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",4);
            }break;
            case R.id.btn05:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",5);
            }break;
            case R.id.btn06:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",6);
            }break;
            case R.id.btn07:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",7);
            }break;
            case R.id.btn08:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",8);
            }break;
            case R.id.btn09:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",9);
            }break;
            case R.id.btn10:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",10);
            }break;
            case R.id.btn11:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",11);
            }break;
            case R.id.btn12:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",12);
            }break;
            case R.id.btn13:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",13);
            }break;
            case R.id.btn14:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",14);
            }break;
            case R.id.btn15:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",15);
            }break;
            case R.id.btn16:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",16);
            }break;
            case R.id.btn17:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",17);
            }break;
            case R.id.btn18:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",18);
            }break;
            case R.id.btn19:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",19);
            }break;
            case R.id.btn20:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",20);
            }break;
            case R.id.btn21:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",21);
            }break;
            case R.id.btn22:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",22);
            }break;
            case R.id.btn23:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",23);
            }break;
            case R.id.btn24:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",24);
            }break;
            case R.id.btn25:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",25);
            }break;
            case R.id.btn26:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",26);
            }break;
            case R.id.btn27:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",27);
            }break;
            case R.id.btn28:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",28);
            }break;
            case R.id.btn29:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",29);
            }break;
            case R.id.btn30:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",30);
            }break;
            case R.id.btn31:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",31);
            }break;
            case R.id.btn32:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",32);
            }break;
            case R.id.btn33:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",33);
            }break;
            case R.id.btn34:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",34);
            }break;
            case R.id.btn35:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",35);
            }break;
            case R.id.btn36:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",36);
            }break;
            case R.id.btn37:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",37);
            }break;
            case R.id.btn38:{
                intent = new Intent(Result.this,AboutDisease.class);
                intent.putExtra("did",38);
            }break;
        }
        startActivity(intent);
    }
}
