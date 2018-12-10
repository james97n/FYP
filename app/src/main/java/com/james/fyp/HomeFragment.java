package com.james.fyp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;




import java.io.IOException;


public class HomeFragment extends Fragment {

    String content,translatedtext;
    TextView tView, inputText;
    Translator translator = new Translator();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View homeView = inflater.inflate(R.layout.fragment_home, container, false);
        tView = homeView.findViewById(R.id.textView1);
        inputText = homeView.findViewById(R.id.search);

        inputText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {

                    String text = inputText.getText().toString();
                    try
                    {

                        translatedtext = translator.translate("en", "zh-CN", text);

                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }


                    //content = inputText.getText().toString();
                    tView.setText(translatedtext);


                    return true;
                }
                return false;
            }
        });





        return homeView;
    }





}


