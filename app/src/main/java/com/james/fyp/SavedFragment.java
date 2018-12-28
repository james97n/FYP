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
        builder.setTitle(getString(R.string.confirm_saved));
        builder.setMessage(getString(R.string.Cannot_undone));
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                savedDBHandler.deleteAllHandler();
                getFragmentManager().beginTransaction()
                        .replace(((ViewGroup) getView().getParent()).getId(), new SavedFragment())
                        .addToBackStack(null)
                        .commit();
                Toast.makeText(getContext(), getString(R.string.del_saved), Toast.LENGTH_LONG).show();

            }
        });
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
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
