package com.james.fyp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ScoreboardFragment extends Fragment{

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setNavigationVisibility(false);

        final View scoreView = inflater.inflate(R.layout.fragment_scoreboard, container, false);

        final ScoreDBHandler scoreDBHandler = new ScoreDBHandler(getActivity(),null,null,1); // db handler

        //loading view
        scoreDBHandler.loadHandler(scoreView,getActivity());

        return scoreView;

    }
}
