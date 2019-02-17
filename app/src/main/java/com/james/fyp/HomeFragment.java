package com.james.fyp;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


public class HomeFragment extends Fragment {



    String content,translatedtext,text;
    TextView tView, inputText;
    Translator translator = new Translator();
    Button saveBtn;
    Button historyBtn;
    Button translateBtn;
    //Boolean validTranslate = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //declaration
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();  // prevent app stop working
        StrictMode.setThreadPolicy(policy);
        final View homeView = inflater.inflate(R.layout.fragment_home, container, false);
        final HistoryDBHandler historyDBHandler = new HistoryDBHandler(getActivity(),null,null,1);
        final SavedDBHandler savedDBHandler = new SavedDBHandler(getActivity(),null,null,1);
        tView = homeView.findViewById(R.id.textView1);
        tView.setMovementMethod(new ScrollingMovementMethod());
        saveBtn =homeView.findViewById(R.id.btnSave);
        saveBtn.setEnabled(false);
        historyBtn = homeView.findViewById(R.id.btnHistory);
        translateBtn = homeView.findViewById(R.id.btnTranslate);


        inputText = homeView.findViewById(R.id.search);


        tView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(tView.getText());
                Toast.makeText(getContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });


        //save button action

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedDBHandler.addHandler(text, translatedtext, "");
                    Toast.makeText(getContext(), getString(R.string.translate_saved), Toast.LENGTH_LONG).show();

            }
        });


        //history button
        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(((ViewGroup) getView().getParent()).getId(), new HistoryFragment())
                        .addToBackStack(null)
                        .commit();

            }
        });

        translateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //validTranslate = false;
                text = inputText.getText().toString();
                if (!text.isEmpty() && !text.trim().equals("")) {  // make sure user is not null
                    try {
                        translatedtext = translator.translate("en", "zh-CN", text, 1);
                        historyDBHandler.addHandler(text, translatedtext);
                        tView.setText(translatedtext);
                        saveBtn.setEnabled(true);
                        Toast.makeText(getContext(), "Translated", Toast.LENGTH_LONG).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    translatedtext = "Please Input Text.";
                }

                tView.setText(translatedtext);


            }
        });


        return homeView;
    }




}


