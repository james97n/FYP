package com.james.fyp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CardAdapter2 extends RecyclerView.Adapter<CardAdapter2.CardViewHolder> {

    private ArrayList<CardItem> mCardList;
    private OnItemClickListener2 mListener;

    public interface OnItemClickListener2 {
        void onItemClick(int position);

        void onSaveClick(int position);

    }

    public void setOnItemClickListener2(OnItemClickListener2 listener) {
        mListener = listener;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView mSaveImage;

        public CardViewHolder(View itemView, final OnItemClickListener2 listener) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.textViewStudy);
            mTextView2 = itemView.findViewById(R.id.textViewStudy2);
            mSaveImage = itemView.findViewById(R.id.image_star);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            mSaveImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onSaveClick(position);


                        }
                    }
                }
            });

        }

    }

    public CardAdapter2(ArrayList<CardItem> cardList) {
        mCardList = cardList;

    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item2, parent, false);
        CardViewHolder evh = new CardViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        CardItem currentItem = mCardList.get(position);

        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());
    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }


}
