package com.example.plantai;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ImageAdapter extends PagerAdapter {
    private Context mContext;
    private int[] imageId  = {};
    private int[][] allimageIds =  {{R.drawable.dp01,R.drawable.dp01_1,R.drawable.dp01_2},
            {R.drawable.dp02,R.drawable.dp02_1,R.drawable.dp02_2},
            {R.drawable.dp03,R.drawable.dp03_1,R.drawable.dp03_2},
            {R.drawable.dp04,/*R.drawable.dp04_1,R.drawable.dp04_2*/},
            {R.drawable.dp05,/*R.drawable.dp05_1,R.drawable.dp05_2*/},
            {R.drawable.dp06,/*R.drawable.dp06_1,R.drawable.dp06_2*/},
            {R.drawable.dp07,/*R.drawable.dp07_1,R.drawable.dp07_2*/},
            {R.drawable.dp08,/*R.drawable.dp08_1,R.drawable.dp08_2*/},
            {R.drawable.dp09,/*R.drawable.dp09_1,R.drawable.dp09_2*/},
            {R.drawable.dp10,/*R.drawable.dp10_1,R.drawable.dp10_2*/},
            {R.drawable.dp11,/*R.drawable.dp11_1,R.drawable.dp11_2*/},
            {R.drawable.dp12,/*R.drawable.dp12_1,R.drawable.dp12_2*/},
            {R.drawable.dp13,/*R.drawable.dp13_1,R.drawable.dp13_2*/},
            {R.drawable.dp14,/*R.drawable.dp14_1,R.drawable.dp14_2*/},
            {R.drawable.dp15,/*R.drawable.dp15_1,R.drawable.dp15_2*/},
            {R.drawable.dp16,/*R.drawable.dp16_1,R.drawable.dp16_2*/},
            {R.drawable.dp17,/*R.drawable.dp17_1,R.drawable.dp17_2*/},
            {R.drawable.dp18,/*R.drawable.dp18_1,R.drawable.dp18_2*/},
            {R.drawable.dp19,/*R.drawable.dp19_1,R.drawable.dp19_2*/},
            {R.drawable.dp20,/*R.drawable.dp20_1,R.drawable.dp20_2*/},
            {R.drawable.dp21,/*R.drawable.dp21_1,R.drawable.dp21_2*/},
            {R.drawable.dp22,/*R.drawable.dp22_1,R.drawable.dp22_2*/},
            {R.drawable.dp23,/*R.drawable.dp23_1,R.drawable.dp23_2*/},
            {R.drawable.dp24,/*R.drawable.dp24_1,R.drawable.dp24_2*/},
            {R.drawable.dp25,/*R.drawable.dp25_1,R.drawable.dp25_2*/},
            {R.drawable.dp26,/*R.drawable.dp26_1,R.drawable.dp26_2*/},
            {R.drawable.dp27,/*R.drawable.dp27_1,R.drawable.dp27_2*/},
            {R.drawable.dp28,/*R.drawable.dp28_1,R.drawable.dp28_2*/},
            {R.drawable.dp29,/*R.drawable.dp29_1,R.drawable.dp29_2*/},
            {R.drawable.dp30,/*R.drawable.dp30_1,R.drawable.dp30_2*/},
            {R.drawable.dp31,/*R.drawable.dp31_1,R.drawable.dp31_2*/},
            {R.drawable.dp32,/*R.drawable.dp32_1,R.drawable.dp32_2*/},
            {R.drawable.dp33,/*R.drawable.dp33_1,R.drawable.dp33_2*/},
            {R.drawable.dp34,/*R.drawable.dp34_1,R.drawable.dp34_2*/},
            {R.drawable.dp35,/*R.drawable.dp35_1,R.drawable.dp35_2*/},
            {R.drawable.dp36,/*R.drawable.dp36_1,R.drawable.dp36_2*/},
            {R.drawable.dp37,/*R.drawable.dp37_1,R.drawable.dp37_2*/},
            {R.drawable.dp38,/*R.drawable.dp38_1,R.drawable.dp38_2*/},
    };
    ImageAdapter(Context context,int id)
    {
        mContext = context;
        imageId = allimageIds[id - 1];
        //TODO init imageId array according to id
    }



    @Override
    public int getCount() {
        return imageId.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(imageId[position]);
        container.addView(imageView,0);
        return  imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }
}
