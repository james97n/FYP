package com.james.fyp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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
        tView.setMovementMethod(new ScrollingMovementMethod());
        //tView.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD)
        inputText = homeView.findViewById(R.id.search);

        inputText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    String text = inputText.getText().toString();
                    if(text != null && !text.isEmpty()) {
                        try {

                            translatedtext = translator.translate("en", "zh-CN", text);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        translatedtext = "It seems that you didn't input anything..";
                    }

                    //content = inputText.getText().toString();
                    tView.setText(translatedtext);
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    return true;
                }
                return false;
            }
        });





        return homeView;
    }





}


