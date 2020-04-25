package com.example.plantai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Reports extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
    }


    /** Menu Bar **/
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
                break;
            case R.id.op_reports:
                intent = new Intent(this,Reports.class);
                break;
            case R.id.op_setting:
                intent = new Intent(this,Settings.class);
                break;
            case R.id.op_about:
                intent = new Intent(this,About.class);
                break;

        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
