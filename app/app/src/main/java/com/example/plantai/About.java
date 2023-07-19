package com.example.plantai;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView aboutText = findViewById(R.id.aboutText);
        String htmlText = getString(R.string.about_page);
        aboutText.setText(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY));

        // Register a callback for back button presses
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Your custom back action: navigate to a specific activity
                Intent intent = new Intent(About.this, Home.class);
                startActivity(intent);
                finish();  // Optionally finish the current activity
            }
        });
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
        } else if (item.getItemId() == R.id.op_reports) {
            intent = new Intent(this, Reports.class);
        } else if (item.getItemId() == R.id.op_setting) {
            intent = new Intent(this, Settings.class);
        } else if (item.getItemId() == R.id.op_about) {
            intent = new Intent(this, About.class);
        }

        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
