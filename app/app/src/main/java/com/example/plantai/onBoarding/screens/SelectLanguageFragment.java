package com.example.plantai.onBoarding.screens;

import static androidx.core.app.ActivityCompat.finishAffinity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.plantai.R;

import java.util.Locale;

public class SelectLanguageFragment extends Fragment {

    private static final String TAG = "SelectLanguageFragment";
    private static final String SHARED_PREFS = "Settings";
    private static final String APP_LANGUAGE = "app_lang";

    private String selectedLang;
    private Button nextButton;
    private RadioGroup radioGroup;
    private ViewPager2 viewPager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_language, container, false);

        // Initialize UI elements
        nextButton = view.findViewById(R.id.nextBtn);
        radioGroup = view.findViewById(R.id.lang_grp);
        viewPager = requireActivity().findViewById(R.id.viewPager);

        // Load stored language setting
        loadSetting();

        // ✅ Correct way to handle Radio Button selection
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedRadioButton = view.findViewById(checkedId);
            if (selectedRadioButton != null) {
                selectedLang = getLanguageCode(selectedRadioButton.getText().toString());
                System.out.println("radioGroup Clicked");
                System.out.println(selectedLang);
                saveSetting(selectedLang);
                Log.d(TAG, "Selected Language: " + selectedLang);
            }
        });

        // ✅ Handle "Next" button click
        nextButton.setOnClickListener(v -> {
            if (selectedLang == null) {
                selectedLang = "en"; // Default language
            }
            System.out.println("radioGroup Clicked");
            System.out.println(selectedLang);
            saveSetting(selectedLang);

            // Move to the next fragment in ViewPager
            if (viewPager != null) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            } else {
                Toast.makeText(getContext(), "Error: ViewPager not found!", Toast.LENGTH_SHORT).show();
            }
        });

        // ✅ Handle Back Press (Only exit if first screen)
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (viewPager.getCurrentItem() == 0) { // Only exit if first screen
//                    new AlertDialog.Builder(requireContext())
//                            .setTitle("Exit App")
//                            .setMessage("Are you sure you want to exit?")
//                            .setPositiveButton("Yes", (dialog, which) -> requireActivity().finishAffinity())
//                            .setNegativeButton("No", null)
//                            .show();
                    requireActivity().finishAffinity();
                } else {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true); // Go to previous page
                }
            }
        });

        return view;
    }

    /** ✅ Get language code from selected text */
    private String getLanguageCode(String selectedText) {
        switch (selectedText) {
            case "English":
                return "en";
            case "हिंदी":
                return "hi";
            case "मराठी":
                return "mr";
            default:
                return "en";
        }
    }

    /** ✅ Save language preference */
    private void saveSetting(String language) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(APP_LANGUAGE, language);
        editor.apply();
        Log.d(TAG, "Language saved: " + language);
    }

    /** ✅ Load saved language setting */
    private void loadSetting() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        selectedLang = sharedPreferences.getString(APP_LANGUAGE, "en");

        // Set the selected language in UI
        if (selectedLang.equals("en")) {
            radioGroup.check(R.id.rodio_e);
        } else if (selectedLang.equals("hi")) {
            radioGroup.check(R.id.rodio_h);
        } else if (selectedLang.equals("mr")) {
            radioGroup.check(R.id.rodio_m);
        }

        updateLocale(selectedLang);
    }

    private void updateLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = new Configuration(resources.getConfiguration());
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }


}
