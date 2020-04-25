package com.example.plantai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlantCardsAdapter extends RecyclerView.Adapter<PlantCardsAdapter.PlantCardViewHolder> {

    private ArrayList<PlantCard> mPlantCardList;
    private PlantCardOnClickListener plantCardOnClickListener;

    public PlantCardsAdapter(ArrayList<PlantCard> mPlantCardList,PlantCardOnClickListener plantCardOnClickListener) {
        this.mPlantCardList = mPlantCardList;
        this.plantCardOnClickListener = plantCardOnClickListener;
    }


    @NonNull
    @Override
    public PlantCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_plants,parent,false);
        return new PlantCardViewHolder(view,plantCardOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantCardViewHolder holder, int position) {
        PlantCard currentExampleCard = mPlantCardList.get(position);
        holder.imageView.setImageResource(currentExampleCard.getmImageResource());
        holder.textView.setText(currentExampleCard.getmText());
    }

    @Override
    public int getItemCount() {
        return mPlantCardList.size();
    }

    public static class PlantCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView textView;
        public PlantCardOnClickListener plantCardOnClickListener;
        public PlantCardViewHolder(@NonNull View itemView, PlantCardOnClickListener plantCardOnClickListener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.CardPlantPic);
            textView = itemView.findViewById(R.id.CardPlantName);
            this.plantCardOnClickListener = plantCardOnClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            plantCardOnClickListener.OnPlantCardClick(getAdapterPosition());
        }
    }

    public interface PlantCardOnClickListener
    {
        void OnPlantCardClick(int position);
    }

}
