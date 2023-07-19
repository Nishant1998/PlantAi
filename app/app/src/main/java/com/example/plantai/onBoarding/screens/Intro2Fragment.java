package com.example.plantai.onBoarding.screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.plantai.Home;
import com.example.plantai.R;

public class Intro2Fragment extends Fragment {

    private static final String SHARED_PREFS = "MyPrefs";  // 🔹 Updated to "MyPrefs" to match your code
    private static final String APP_LANGUAGE_KEY = "app_lang";
    private static final String FIRST_RUN_KEY = "firstRun";

    private String selectedLang;
    private Button nextButton;
    private TextView textH, textD;
    private ViewPager2 viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro2, container, false);

        // Initialize UI elements
        nextButton = view.findViewById(R.id.nextBtn);
        textH = view.findViewById(R.id.intro1_head);
        textD = view.findViewById(R.id.intro1_dis);
        viewPager = requireActivity().findViewById(R.id.viewPager);

        // Load and set language
        selectedLang = loadSetting();
        setLang();

        // Handle "Next" button click
        nextButton.setOnClickListener(v -> {
            markOnboardingComplete(); // ✅ Save firstRun as false
            startActivity(new Intent(requireActivity(), Home.class));
            requireActivity().finish(); // ✅ Finish onboarding
        });

        return view;
    }

    /** ✅ Save firstRun as FALSE to prevent showing onboarding again */
    private void markOnboardingComplete() {
        SharedPreferences prefs = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(FIRST_RUN_KEY, false);  // 🔹 Correctly setting "firstRun" to false
        editor.apply();
    }

    /** ✅ Load saved language preference */
    private String loadSetting() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(APP_LANGUAGE_KEY, "en");
    }

    /** ✅ Set language for UI elements */
    private void setLang() {
        nextButton.setText(getString(R.string.next));  // 🔹 Change "Next" to "Finish" on last screen
        textH.setText(getString(R.string.intro_heading));
        textD.setText(getString(R.string.intro_caption));
    }
}
