package com.james.fyp;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class StudyFragment extends Fragment {

    static SharedPreferences prefs;

    //share preferences attribute
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String PHRASE_WORD = "phraseWord";
    public static final String PHRASE_MEANING = "phraseMeaning";
    public static final String PHRASE_EXAMPLE = "phraseExample";
    public static final String CURRENT_TIME = "currentTime";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        final View studyView = inflater.inflate(R.layout.fragment_study, container, false);
        final TextView word = studyView.findViewById(R.id.wordOfTheDay);
        final TextView meaning = studyView.findViewById(R.id.meaningOfTheDay);
        final TextView example = studyView.findViewById(R.id.exampleOfTheDay);

        final PhraseDBHandler phraseDBHandler = new PhraseDBHandler(getActivity(), null, null, 1);
        final SavedDBHandler savedDBHandler = new SavedDBHandler(getActivity(), null, null, 1);

        prefs = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        Date currentTime = new Date(prefs.getLong(CURRENT_TIME, 0));
        Date lastUpdateTime = Calendar.getInstance().getTime();


        long different = lastUpdateTime.getTime() - currentTime.getTime();

        if (different >= 1000 * 60 * 60 * 24) { //check whether the duration is more than one day
            //word.setText("Hello");
            phraseDBHandler.loadHandler(word, meaning, example);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(PHRASE_WORD, word.getText().toString());
            editor.putString(PHRASE_MEANING, meaning.getText().toString());
            editor.putString(PHRASE_EXAMPLE, example.getText().toString());
            editor.putLong(CURRENT_TIME, lastUpdateTime.getTime());
            editor.apply();
        } else {

            word.setText(prefs.getString(PHRASE_WORD, ""));
            meaning.setText(prefs.getString(PHRASE_MEANING, ""));
            example.setText(prefs.getString(PHRASE_EXAMPLE, ""));

        }

        Button btnStudy = studyView.findViewById(R.id.btnPhrasebook);
        btnStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(((ViewGroup) getView().getParent()).getId(), new Phrasebook())
                        .addToBackStack(null)
                        .commit();


            }
        });


        final Button btnSave = studyView.findViewById(R.id.btnSaveStudy);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                savedDBHandler.addHandler(word.getText().toString(), meaning.getText().toString(), example.getText().toString());
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();


            }
        });

        word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(word.getText());
                Toast.makeText(getContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });


        return studyView;

    }


}



