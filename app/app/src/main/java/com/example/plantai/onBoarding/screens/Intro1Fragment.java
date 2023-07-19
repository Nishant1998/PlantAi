package com.example.plantai.onBoarding.screens;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.plantai.R;

import java.util.Locale;

public class Intro1Fragment extends Fragment {

    private static final String SHARED_PREFS = "Settings";
    private static final String APP_LANGUAGE_KEY = "app_lang";

    private String selectedLang;
    private Button nextButton;
    private TextView textH, textD;
    private ViewPager2 viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro1, container, false);

        // Initialize UI components
        nextButton = view.findViewById(R.id.nextBtn);
        textH = view.findViewById(R.id.intro1_head);
        textD = view.findViewById(R.id.intro1_dis);
        viewPager = requireActivity().findViewById(R.id.viewPager);

        // Load selected language setting
        selectedLang = loadSetting();
        setLang();


        // Handle "Next" button click
        nextButton.setOnClickListener(v -> {
            if (viewPager != null) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });

        return view;
    }

    /** ✅ Load saved language preference */
    private String loadSetting() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(APP_LANGUAGE_KEY, "en");
    }



    /** ✅ Set language for UI elements */
    private void setLang() {
        nextButton.setText(getString(R.string.next));
        textH.setText(getString(R.string.intro_heading));
        textD.setText(getString(R.string.intro_caption));

    }
}
