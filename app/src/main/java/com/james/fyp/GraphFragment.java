package com.james.fyp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GraphFragment extends Fragment {

    private GraphFragmentListener listener;

    public interface GraphFragmentListener{
        String loadPlayerName();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //((MainActivity) getActivity()).setNavigationVisibility(false);

        final View graphView = inflater.inflate(R.layout.fragment_graph, container, false);

        final ScoreDBHandler scoreDBHandler = new ScoreDBHandler(getActivity()); // db handler

        final TextView tview = graphView.findViewById(R.id.playerGraph);
        tview.setText("Player: " + listener.loadPlayerName());

        scoreDBHandler.loadPlayerHandler(graphView, listener.loadPlayerName());



        return graphView;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GraphFragmentListener) {
            listener = (GraphFragmentListener) context;
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
