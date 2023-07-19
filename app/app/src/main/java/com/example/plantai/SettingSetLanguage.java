package com.example.plantai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Objects;

public class SettingSetLanguage extends AppCompatActivity {

    String selected_lang;
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
        setLang();
        RadioButton r;
        if(Objects.equals(selected_lang, "en"))
            r = findViewById(R.id.rodio_0);
        else if(Objects.equals(selected_lang, "hi"))
            r = findViewById(R.id.rodio_1);
        else // mr
            r = findViewById(R.id.rodio_2);
        r.toggle();

    }

    public void radioClick(View view) {
        int radioBtn =  radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioBtn);

        if(radioButton.getText().equals("English"))
            selected_lang = "en";
        else if(radioButton.getText().equals("हिंदी"))
            selected_lang = "hi";
        else if(radioButton.getText().equals("मराठी"))
            selected_lang = "mr";
    }

    //save settings
    void save_setting(String lang_code) {
        SharedPreferences mySharedPreference = getSharedPreferences(SHARED, MODE_PRIVATE);
        String currentLang = mySharedPreference.getString(SHARED_APP_LANG, "en");

        // Only update language if it is different from the current one
        if (!currentLang.equals(lang_code)) {
            SharedPreferences.Editor editor = mySharedPreference.edit();
            editor.putString(SHARED_APP_LANG, lang_code);
            editor.apply();

            // Update app language based on the selected language
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(lang_code));

            // Instead of recreating the activity immediately, use a smoother transition
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(this, getClass());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }, 100);  // Slight delay for smoother transition
        }
    }


    public void saveClick(View view) {
        save_setting(selected_lang);
        finish();
    }

    public void cancelClick(View view) {
        finish();
    }

    //TODO Change lang
    void setLang()
    {
        cancel.setText(getString(R.string.cancel));
        save.setText(getString(R.string.save));
        textView.setText(getString(R.string.select_language));
    }

    //TODO Load Setting
    String load_setting()
    {
        SharedPreferences mySharedPreference = getSharedPreferences(SHARED,MODE_PRIVATE);
        return mySharedPreference.getString(SHARED_APP_LANG,"en");
    }


}
