package com.example.plantai.onBoarding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plantai.R;
import com.example.plantai.onBoarding.screens.Intro1Fragment;
import com.example.plantai.onBoarding.screens.Intro2Fragment;
import com.example.plantai.onBoarding.screens.SelectLanguageFragment;

import java.util.ArrayList;

public class ViewPagerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);

        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new SelectLanguageFragment());
        fragmentList.add(new Intro1Fragment());
        fragmentList.add(new Intro2Fragment());

        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentList, requireActivity().getSupportFragmentManager(), getLifecycle());

        ViewPager2 pager = view.findViewById(R.id.viewPager);
        pager.setAdapter(adapter);
        return view;
    }
}