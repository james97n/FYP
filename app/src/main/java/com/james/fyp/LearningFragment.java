package com.james.fyp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;
import java.util.Set;



public class LearningFragment extends Fragment {


    static SharedPreferences prefs;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";


    protected TextView textViewHighscore;

    protected static int highscore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View learningView = inflater.inflate(R.layout.fragment_learning, container, false);

        ((MainActivity) getActivity()).setNavigationVisibility(true);

        prefs = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        textViewHighscore = learningView.findViewById(R.id.text_view_highscore);
        loadHighscore();

        Button btnStartQuiz = learningView.findViewById(R.id.button_start_quiz);
        btnStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });

        return learningView;

    }


    private void startQuiz() {
        getFragmentManager().beginTransaction()
                .replace(((ViewGroup) getView().getParent()).getId(), new QuestionSet())
                .addToBackStack(null)
                .commit();
    }



    public void updateHighscore(int highscoreNew) {
        if(highscoreNew>highscore){
            highscore = highscoreNew;}

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.apply();
    }

    private void loadHighscore() {

        highscore = prefs.getInt(KEY_HIGHSCORE, 0);
        textViewHighscore.setText("Highscore: " + highscore);



    }







}
