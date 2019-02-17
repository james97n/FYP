package com.james.fyp;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class WordFragment extends Fragment {

    protected String word;
    protected String meaning;
    protected String example;

    public WordFragment(String Word, String Meaning, String Example) {

        word = Word;
        meaning = Meaning;
        example = Example;

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.fragment_word, container, false);
        final TextView tWord = view.findViewById(R.id.wordOfTheSaved);
        tWord.setMovementMethod(new ScrollingMovementMethod());
        TextView tMeaning = view.findViewById(R.id.meaningOfTheWord);
        TextView tExampleTitle = view.findViewById(R.id.exampleTitle);
        TextView tExample = view.findViewById(R.id.exampleOfTheWord);

        tWord.setText(word);
        tMeaning.setText(meaning);
        if (!example.equals("")) {
            tExampleTitle.setText("Example: ");
            tExample.setText(example);
        }

        tWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(tWord.getText());
                Toast.makeText(getContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}
