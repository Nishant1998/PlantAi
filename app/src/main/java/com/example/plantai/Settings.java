package com.example.plantai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    private static final String SHARED = "Settings";
    private static final String SHARED_APP_LANG = "app_lang";
    int selected_lang;

    TextView textView0,textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        textView0 = findViewById(R.id.setting_head);
        textView1 = findViewById(R.id.setting_info);

        //get setting and change language
        selected_lang = load_setting();
        setLang(selected_lang);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

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
        switch (item.getItemId())
        {
            case R.id.op_profile:
                intent = new Intent(this,profile.class);
                startActivity(intent);
                break;
            case R.id.op_reports:
                intent = new Intent(this,Reports.class);
                startActivity(intent);
                break;
            case R.id.op_setting:
//                intent = new Intent(this,this.class);
                break;
            case R.id.op_about:
                intent = new Intent(this,About.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void clickLangSetting(View view) {
        Intent intent = new Intent(this,SettingSetLanguage.class);
        startActivity(intent);
    }

    //TODO Load Setting
    int load_setting()
    {
        SharedPreferences mySharedPreference = getSharedPreferences(SHARED,MODE_PRIVATE);
        int i = mySharedPreference.getInt(SHARED_APP_LANG,0);
        return i;
    }

    //TODO Change lang
    void setLang(int i)
    {
        switch (i)
        {
            case 0:
                System.out.println("CASE 0 ***");
                textView0.setText(getString(R.string.sele_lan_0));
                textView1.setText(getString(R.string.english));
                break;
            case 1:
                System.out.println("CASE 1 ***");
                textView0.setText(getString(R.string.sele_lan_1));
                textView1.setText(getString(R.string.hindi));
                break;
            case 2:
                System.out.println("CASE 2 ***");
                textView0.setText(getString(R.string.sele_lan_2));
                textView1.setText(getString(R.string.marathi));
                break;
        }
    }

}
