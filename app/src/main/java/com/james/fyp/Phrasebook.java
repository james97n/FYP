package com.james.fyp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class Phrasebook extends Fragment {

    private ArrayList<CardItem> mCardList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private CardAdapter2 mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_phrasebook, container, false);


        createCardList();
        buildRecyclerView(view);

        return view;

    }

    public void createCardList() {
        mCardList = new ArrayList<>();
        mCardList.add(new CardItem("Line 1", "Line 2"));
        mCardList.add(new CardItem("Line 3", "Line 4"));
        mCardList.add(new CardItem("Line 5", "Line 6"));
    }

    public void buildRecyclerView(View v) {
        mRecyclerView = v.findViewById(R.id.recyclerView2);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new CardAdapter2(mCardList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener2(new CardAdapter2.OnItemClickListener2() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onSaveClick(int position) {
                Toast.makeText(getContext(), "Vocabulary saved", Toast.LENGTH_LONG).show();
            }
        });
    }


}
