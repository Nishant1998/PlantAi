package com.example.plantai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DiseaseCardsAdapter extends RecyclerView.Adapter<DiseaseCardsAdapter.DiseaseCardViewHolder>{

    private ArrayList<DiseaseCard> mCardList;
    private DiseaseCardOnClickListener mOnClickListener;

    public DiseaseCardsAdapter(ArrayList<DiseaseCard> mExampleCardList , DiseaseCardOnClickListener onClickListener) {
        this.mCardList = mExampleCardList;
        this.mOnClickListener = onClickListener;
    }

    public static class DiseaseCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imageView;
        public TextView textView;
        public TextView percentView;
        DiseaseCardOnClickListener onClickListener;

        public DiseaseCardViewHolder(@NonNull View itemView, DiseaseCardOnClickListener onClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.CardDiseasePic);
            textView = itemView.findViewById(R.id.CardDiseaseName);
            percentView = itemView.findViewById(R.id.percentage);
            this.onClickListener = onClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.OnDiseaseCardClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public DiseaseCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)  {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_disease,parent,false);
        DiseaseCardViewHolder ViewHolder = new DiseaseCardViewHolder(view,mOnClickListener);
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DiseaseCardViewHolder holder, int position) {
        DiseaseCard currentExampleCard = mCardList.get(position);
        holder.imageView.setImageResource(currentExampleCard.getmImageResource());
        holder.textView.setText(currentExampleCard.getmText());
        holder.percentView.setText(currentExampleCard.getmPercent());
    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }

    public interface DiseaseCardOnClickListener
    {
        void OnDiseaseCardClick(int position);
    }

    }


