package com.james.fyp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class SavedFragment extends Fragment {

    private ArrayList<SavedCardItem> mCardList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private CardAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public SavedDBHandler savedDBHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        // declaration
        final View savedView = inflater.inflate(R.layout.fragment_saved, container, false); // declare history view

        savedDBHandler = new SavedDBHandler(getActivity(), null, null, 1); // db handler

        //loading view
        savedDBHandler.loadHandler(mCardList);

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


        buildRecyclerView(savedView);

        return savedView;
    }

    public void removeItem(int position) {

        long id = mCardList.get(position).getmID();
        savedDBHandler.deleteItem(id);
        mCardList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void RemoveAllItem() {

        int size = mCardList.size();

        for (int i = 0; i < size; i++) {

            mCardList.remove(0);

        }

    }


    public void buildRecyclerView(View v) {
        mRecyclerView = v.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new CardAdapter(mCardList);


        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);



        mAdapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {


                getFragmentManager().beginTransaction()
                        .replace(((ViewGroup) getView().getParent()).getId(), new WordFragment(mCardList.get(position).getText1(), mCardList.get(position).getText2(), mCardList.get(position).getText3()))
                        .addToBackStack(null)
                        .commit();

                RemoveAllItem(); // to prevent item duplicate once back button pressed


            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }

}