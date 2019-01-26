package com.james.fyp;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    Button historyBtn;
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


        inputText = homeView.findViewById(R.id.search);

        //Done input action
        inputText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    tView.setText(getString(R.string.translating));
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    //validTranslate = false;
                    text = inputText.getText().toString();
                    if(!text.isEmpty() && !text.trim().equals("")) {  // make sure user is not null
                        try {
                            translatedtext = translator.translate("en", "zh-CN", text,1);
                            historyDBHandler.addHandler(text,translatedtext);
                            tView.setText(translatedtext);
                            saveBtn.setEnabled(true);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        translatedtext = getString(R.string.no_input);
                    }

                    tView.setText(translatedtext);

                    return true;

                }
                return false;
            }
        });

        //save button action


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    savedDBHandler.addHandler(text, translatedtext);
                    Toast.makeText(getContext(), getString(R.string.translate_saved), Toast.LENGTH_LONG).show();

            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(((ViewGroup) getView().getParent()).getId(), new HistoryFragment())
                        .addToBackStack(null)
                        .commit();

            }
        });



        return homeView;
    }




}


