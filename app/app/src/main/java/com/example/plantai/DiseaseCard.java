package com.example.plantai;

public class DiseaseCard {
    private int mImageResource;
    private String mText,mPercent;

    public DiseaseCard(int mImageResource, String mText,String mPercent) {
        this.mImageResource = mImageResource;
        this.mText = mText;
        this.mPercent = mPercent;
    }

    public String getmPercent() {
        return mPercent;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public String getmText() {
        return mText;
    }
}
