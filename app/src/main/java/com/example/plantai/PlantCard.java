package com.example.plantai;

public class PlantCard {

    private int mImageResource;
    private String mText;

    public PlantCard(int mImageResource, String mText) {
        this.mImageResource = mImageResource;
        this.mText = mText;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public String getmText() {
        return mText;
    }
}
