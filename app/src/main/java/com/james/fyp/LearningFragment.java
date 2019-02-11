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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class LearningFragment extends Fragment {

    private LearningFragmentListener listener;

    static SharedPreferences prefs;
    //static SharedPreferences prefs1;

    //public static final String EXTRA_DIFFICULTY = "extraDifficulty";


    public static final String SHARED_PREFS = "sharedPrefs";
    //public static final String SHARED_PREFS1 = "sharedPref";
    public static final String KEY_HIGHSCORE = "keyHighscore";
    public static final String PLAYER_NAME = "playerName";
    protected static String player_name;
    boolean startbtn = false;

    static ScoreDBHandler scoreDBHandler;

    protected TextView textViewHighscore;
    protected Spinner spinnerDifficulty;

    View V; //variable used to hide keyboard

    protected static int highscore;

    public interface LearningFragmentListener {
        void setDifficulty(String difficulty);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        scoreDBHandler = new ScoreDBHandler(getContext(),null,null,1); // db handler


        final View learningView = inflater.inflate(R.layout.fragment_learning, container, false);

        prefs = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        //prefs1 = getActivity().getSharedPreferences(SHARED_PREFS1, Context.MODE_PRIVATE);

        final EditText input = new EditText(getActivity());
        input.setText(loadPlayerName(), TextView.BufferType.EDITABLE);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.Enter_name));
        builder.setMessage(getString(R.string.Get_ranked));
        builder.setCancelable(true);

        final TextView tview = learningView.findViewById(R.id.player_name);
        if (!loadPlayerName().equals("")) {
            tview.setText("Player name: " + loadPlayerName());
        }

        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                player_name = input.getText().toString();

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(PLAYER_NAME, player_name);
                editor.apply();

                //to check whether start button is clicked
                if(startbtn){
                    startbtn=false;
                    startQuiz();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog alert = builder.create();


        ((MainActivity) getActivity()).setNavigationVisibility(true);


        textViewHighscore = learningView.findViewById(R.id.text_view_highscore);
        spinnerDifficulty = learningView.findViewById(R.id.spinner_difficulty);

        String[] difficultyLevels = Question.getAllDifficultyLevels();

        //spinnerDifficulty = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, difficultyLevels);
        ArrayAdapter<String> adapterDifficulty = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, difficultyLevels);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(adapterDifficulty);

        loadHighscore();

        Button btnStartQuiz = learningView.findViewById(R.id.button_start_quiz);
        btnStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loadPlayerName().equals("")){
                    startbtn = true;
                    alert.show();
                    V = v;
                    }
                    else{startQuiz();}

            }
        });

        //test purpose

        /*GraphView graph = learningView.findViewById(R.id.graph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3)
        });
        graph.addSeries(series);*/


        Button btnScoreboard = learningView.findViewById(R.id.button_score_board);
        btnScoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String difficult = spinnerDifficulty.getSelectedItem().toString();
                //tview.setText("Difficulty: "+ difficult);

                getFragmentManager().beginTransaction()
                        .replace(((ViewGroup) getView().getParent()).getId(), new ScoreboardFragment())
                        .addToBackStack(null)
                        .commit();
                //scoreDBHandler.deleteAllHandler(getContext());


            }
        });


        Button btnChangeName = learningView.findViewById(R.id.button_change_player);
        btnChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.show();

            }
        });

        Button btnGraph = learningView.findViewById(R.id.button_statistic);
        btnGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String difficult = spinnerDifficulty.getSelectedItem().toString();
                //tview.setText("Difficulty: "+ difficult);

                getFragmentManager().beginTransaction()
                        .replace(((ViewGroup) getView().getParent()).getId(), new GraphFragment())
                        .addToBackStack(null)
                        .commit();
                //scoreDBHandler.deleteAllHandler(getContext());


            }
        });

        return learningView;

    }


    private void startQuiz() {

        String difficult = spinnerDifficulty.getSelectedItem().toString();

        listener.setDifficulty(difficult);

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

    public String loadPlayerName() {

        return player_name = prefs.getString(PLAYER_NAME, "");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LearningFragmentListener) {
            listener = (LearningFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentAListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }





}
