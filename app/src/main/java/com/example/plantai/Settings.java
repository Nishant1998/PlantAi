package com.example.plantai;

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
    int selected_lang;

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
            case R.id.op_all_diseases:
                intent = new Intent(this, AllDiseases.class);
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
                delReport0.setText(getString(R.string.menu_history_0));
                delReport1.setText(getString(R.string.del_history_0));
                builder.setTitle(getString(R.string.are_you_sure_0));
                builder.setMessage(getString(R.string.all_his_willbe_del_0));
                ok = getString(R.string.ok_0);
                cancle = getString(R.string.cancel_0);
                deleted = getString(R.string.all_history_del_0);
                break;
            case 1:
                System.out.println("CASE 1 ***");
                textView0.setText(getString(R.string.sele_lan_1));
                textView1.setText(getString(R.string.hindi));
                delReport0.setText(getString(R.string.menu_history_1));
                delReport1.setText(getString(R.string.del_history_1));
                builder.setTitle(getString(R.string.are_you_sure_1));
                builder.setMessage(getString(R.string.all_his_willbe_del_1));
                ok = getString(R.string.ok_1);
                cancle = getString(R.string.cancel_1);
                deleted = getString(R.string.all_history_del_1);
                break;
            case 2:
                System.out.println("CASE 2 ***");
                textView0.setText(getString(R.string.sele_lan_2));
                textView1.setText(getString(R.string.marathi));
                delReport0.setText(getString(R.string.menu_history_2));
                delReport1.setText(getString(R.string.del_history_2));
                builder.setTitle(getString(R.string.are_you_sure_2));
                builder.setMessage(getString(R.string.all_his_willbe_del_2));
                ok = getString(R.string.ok_2);
                cancle = getString(R.string.cancel_2);
                deleted = getString(R.string.all_history_del_2);
                break;
        }
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
