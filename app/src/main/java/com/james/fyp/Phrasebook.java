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

    public SavedDBHandler savedDBHandler;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_phrasebook, container, false);

        savedDBHandler = new SavedDBHandler(getActivity(), null, null, 1);

        createCardList();
        buildRecyclerView(view);

        return view;

    }

    public void createCardList() {
        mCardList = new ArrayList<>();
        mCardList.add(new CardItem("你好吗？", "How are you?"));
        mCardList.add(new CardItem("谢谢你", "Thank you."));
        mCardList.add(new CardItem("今天天气很好", "The weather is very nice today."));
        mCardList.add(new CardItem("现在是下午两点钟", "It is 2 pm."));
        mCardList.add(new CardItem("早安", "Good Morning."));
        mCardList.add(new CardItem("你叫什么名字？", "What is your name."));
        mCardList.add(new CardItem("我今年21岁。", "I am 21 years old."));
        mCardList.add(new CardItem("我喜欢吃苹果", "I like to eat apple."));
        mCardList.add(new CardItem("我去北京。", "I go to Beijing."));
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
                String word = mCardList.get(position).getText1();
                String meaning = mCardList.get(position).getText2();
                savedDBHandler.addHandler(word, meaning);
                Toast.makeText(getContext(), "Vocabulary saved", Toast.LENGTH_LONG).show();
            }
        });
    }


}
