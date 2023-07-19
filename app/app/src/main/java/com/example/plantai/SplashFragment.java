package com.example.plantai;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SplashFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences prefs = requireActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                boolean firstRun = prefs.getBoolean("firstRun", true);

                if (firstRun) {
                    NavHostFragment.findNavController(SplashFragment.this)
                            .navigate(R.id.action_splashFragment_to_viewPagerFragment);
                } else {
                    NavHostFragment.findNavController(SplashFragment.this)
                            .navigate(R.id.action_splashFragment_to_home2);
                }
            }
        }, 3000);


        return inflater.inflate(R.layout.fragment_splash, container, false);
    }
}