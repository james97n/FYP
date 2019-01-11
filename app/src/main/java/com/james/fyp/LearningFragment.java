package com.james.fyp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class LearningFragment extends Fragment {


    static SharedPreferences prefs;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";
    protected static String player_name = " ";

    static ScoreDBHandler scoreDBHandler;

    protected TextView textViewHighscore;

    protected static int highscore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.Enter_name));
        builder.setMessage(getString(R.string.Get_ranked));

        scoreDBHandler = new ScoreDBHandler(getContext(),null,null,1); // db handler


        final View learningView = inflater.inflate(R.layout.fragment_learning, container, false);
        final EditText input = new EditText(getContext());

        final TextView tview = learningView.findViewById(R.id.player_name);
        tview.setText("Player name: "+player_name);

        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                player_name = input.getText().toString();
                startQuiz();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        ((MainActivity) getActivity()).setNavigationVisibility(true);

        prefs = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        textViewHighscore = learningView.findViewById(R.id.text_view_highscore);
        loadHighscore();

        Button btnStartQuiz = learningView.findViewById(R.id.button_start_quiz);
        btnStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.show();

            }
        });


        Button btnScoreboard = learningView.findViewById(R.id.button_score_board);
        btnScoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction()
                        .replace(((ViewGroup) getView().getParent()).getId(), new ScoreboardFragment())
                        .addToBackStack(null)
                        .commit();
                //scoreDBHandler.deleteAllHandler(getContext());


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

        scoreDBHandler.addHandler(player_name,highscoreNew);

    }

    private void loadHighscore() {

        highscore = prefs.getInt(KEY_HIGHSCORE, 0);
        textViewHighscore.setText("Highscore: " + highscore);



    }



}
