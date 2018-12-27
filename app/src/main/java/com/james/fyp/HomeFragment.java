package com.james.fyp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;


public class HomeFragment extends Fragment {



    String content,translatedtext,text;
    TextView tView, inputText;
    Translator translator = new Translator();
    Button saveBtn;
    Boolean save = false;
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


        inputText = homeView.findViewById(R.id.search);

        //Done input action
        inputText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    tView.setText("Translating...");
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    //validTranslate = false;
                    text = inputText.getText().toString();
                    if(!text.isEmpty() && !text.trim().equals("")) {  // make sure user is not null
                        try {
                            translatedtext = translator.translate("en", "zh-CN", text,1);
                            historyDBHandler.addHandler(text,translatedtext);
                            tView.setText(translatedtext);
                            save = true;

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        translatedtext = "It seems that you didn't input anything..";
                    }

                    tView.setText(translatedtext);

                    return true;

                }
                return false;
            }
        });

        //save button action
        saveBtn =homeView.findViewById(R.id.btnSave);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(save) {
                    savedDBHandler.addHandler(text, translatedtext);
                    Toast.makeText(getContext(), "Translation saved.", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(), "No words is entered", Toast.LENGTH_LONG).show();
                }
            }
        });

        return homeView;
    }




}


