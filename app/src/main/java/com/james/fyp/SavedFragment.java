package com.james.fyp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

public class SavedFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        // declaration
        //private static Context context = null;
        final View savedView = inflater.inflate(R.layout.fragment_saved, container, false); // declare history view
        //final TextView tView = historyView.findViewById(R.id.historyTextView1); // text view
        TableLayout tablelayout = savedView.findViewById(R.id.savedTable); //  table layout
        Button btnDelete = savedView.findViewById(R.id.btnDelete2); //delete button
        final SavedDBHandler savedDBHandler = new SavedDBHandler(getActivity(),null,null,1); // db handler

        //loading view
        savedDBHandler.loadHandler(savedView,getActivity());

        //dialog pop up
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Permanently delete all saved vocabularies?");
        builder.setMessage("This can't be undone");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                savedDBHandler.deleteAllHandler();
                savedDBHandler.loadHandler(savedView,getActivity());
                Toast.makeText(getContext(), "Saved vocabularies deleted.", Toast.LENGTH_LONG).show();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        // button action
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        return savedView;
    }
}
