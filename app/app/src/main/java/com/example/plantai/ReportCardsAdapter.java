package com.example.plantai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;

public class ReportCardsAdapter extends RecyclerView.Adapter<ReportCardsAdapter.ReportCardViewHolder>{

    private ArrayList<ReportCard> mReportCardList;
    private ReportCardOnClickListener reportCardOnClickListener;

    public ReportCardsAdapter(ArrayList<ReportCard> mCardList, ReportCardOnClickListener reportCardOnClickListener) {
        this.mReportCardList = mCardList;
        this.reportCardOnClickListener = reportCardOnClickListener;
    }

    @NonNull
    @Override
    public ReportCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_reports,parent,false);
        return new ReportCardsAdapter.ReportCardViewHolder(view,reportCardOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportCardViewHolder holder, int position) {
        ReportCard currentCard = mReportCardList.get(position);
        holder.textView.setText(currentCard.getmText());
        holder.dateView.setText(currentCard.getmDate());
    }

    @Override
    public int getItemCount() {
        return mReportCardList.size();
    }

    public static class ReportCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView textView;
        public TextView dateView;
        public ReportCardOnClickListener onClickListener;
        public ReportCardViewHolder(@NonNull View itemView, ReportCardOnClickListener reportCardOnClickListener) {
            super(itemView);

            textView = itemView.findViewById(R.id.diagnosedOnText);
            dateView = itemView.findViewById(R.id.dateAndTimeText);
            onClickListener = reportCardOnClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.OnReportCardClick(getAdapterPosition());
        }
    }

    public interface ReportCardOnClickListener
    {
        void OnReportCardClick(int position);
    }
}
