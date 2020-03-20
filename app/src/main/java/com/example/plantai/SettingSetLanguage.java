package com.example.plantai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SettingSetLanguage extends AppCompatActivity {

    int selected_lang;
    Button save,cancel;
    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView textView;
    private static final String SHARED = "Settings";
    private static final String SHARED_APP_LANG = "app_lang";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_set_language);

        save = findViewById(R.id.SaveBtn);
        cancel = findViewById(R.id.CancelBtn);

        radioGroup = findViewById(R.id.lang_grp);
        textView = findViewById(R.id.sYL);

        //get setting and change language
        selected_lang = load_setting();
        setLang(selected_lang);
        RadioButton r;
        if(selected_lang == 0)
            r = findViewById(R.id.rodio_0);
        else if(selected_lang == 1)
            r = findViewById(R.id.rodio_1);
        else
            r = findViewById(R.id.rodio_2);
        r.toggle();

    }

    public void radioClick(View view) {
        int radioBtn =  radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioBtn);

        if(radioButton.getText().equals("English"))
            selected_lang = 0;
        else if(radioButton.getText().equals("हिंदी"))
            selected_lang = 1;
        else if(radioButton.getText().equals("मराठी"))
            selected_lang = 2;
    }

    //save settings
    void save_setting(int language_number)
    {
        SharedPreferences mySharedPreference = getSharedPreferences(SHARED,MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreference.edit();
        editor.putInt(SHARED_APP_LANG,language_number);
        editor.apply();
    }

    public void saveClick(View view) {
        save_setting(selected_lang);
//        Intent intent = new Intent(this,Settings.class);
//        startActivity(intent);
        finish();
    }

    public void cancelClick(View view) {
//        Intent intent = new Intent(this,Settings.class);
//        startActivity(intent);
        finish();
    }

    //TODO Change lang
    void setLang(int i)
    {
        switch (i)
        {
            case 0:
                System.out.println("CASE 0 ***");
                cancel.setText(getString(R.string.cancel_0));
                save.setText(getString(R.string.save_0));
                textView.setText(getString(R.string.sele_lan_0));
                break;
            case 1:
                System.out.println("CASE 1 ***");
                cancel.setText(getString(R.string.cancel_1));
                save.setText(getString(R.string.save_1));
                textView.setText(getString(R.string.sele_lan_1));
                break;
            case 2:
                System.out.println("CASE 2 ***");
                cancel.setText(getString(R.string.cancel_2));
                save.setText(getString(R.string.save_2));
                textView.setText(getString(R.string.sele_lan_2));
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
}
