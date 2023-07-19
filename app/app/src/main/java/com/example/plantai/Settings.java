package com.example.plantai;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class Settings extends AppCompatActivity {

    private static final String SHARED = "Settings";
    private static final String SHARED_APP_LANG = "app_lang";
    String selected_lang;

    TextView textView0,textView1;
    TextView delReport0,delReport1;
    AlertDialog.Builder builder;
    private String cancle;
    private String ok,deleted;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        textView0 = findViewById(R.id.setting_head);
        textView1 = findViewById(R.id.setting_info);
        delReport0 = findViewById(R.id.delreport_head);
        delReport1 = findViewById(R.id.delreport_info);
        builder = new AlertDialog.Builder(Settings.this);
        myDB = new DatabaseHelper(this);

        //get setting and change language
        selected_lang = load_setting();
        setLang();

        // Register a callback for back button presses
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Your custom back action: navigate to a specific activity
                Intent intent = new Intent(Settings.this, Home.class);
                startActivity(intent);
                finish();  // Optionally finish the current activity
            }
        });

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
        if (item.getItemId() == R.id.op_all_diseases) {
            intent = new Intent(this, AllDiseases.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.op_reports) {
            intent = new Intent(this, Reports.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.op_setting) {
            // Intent commented out, keeping empty action for now
            // intent = new Intent(this, this.class);
        } else if (item.getItemId() == R.id.op_about) {
            intent = new Intent(this, About.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void clickLangSetting(View view) {
        Intent intent = new Intent(this,SettingSetLanguage.class);
        startActivity(intent);
    }

    //TODO Load Setting
    String load_setting()
    {
        SharedPreferences mySharedPreference = getSharedPreferences(SHARED,MODE_PRIVATE);
        return mySharedPreference.getString(SHARED_APP_LANG,"en");
    }

    //TODO Change lang
    void setLang()
    {
        textView0.setText(getString(R.string.select_language));
        textView1.setText(getString(R.string.language));
        delReport0.setText(getString(R.string.menu_history));
        delReport1.setText(getString(R.string.del_history));
        builder.setTitle(getString(R.string.are_you_sure));
        builder.setMessage(getString(R.string.all_his_willbe_del));
        ok = getString(R.string.ok);
        cancle = getString(R.string.cancel);
        deleted = getString(R.string.all_history_del);
    }

    public void clickDelReportSetting(View view) {
        builder.setPositiveButton(ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDB.deleteAllData();
                //delete images
                File dir = new File(Environment.getExternalStorageDirectory()
                        + "/Android/PlantAi/AllImages");
                if (dir.isDirectory())
                {
                    String[] children = dir.list();
                    for (int i = 0; i < children.length; i++)
                    {
                        new File(dir, children[i]).delete();
                    }
                }
                Toast.makeText(Settings.this,deleted,Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton(cancle, null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}
