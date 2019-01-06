package com.james.fyp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.os.Handler;


import java.util.List;

public  class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener, QuestionSet.QuestionSetListener{


    private LearningFragment learningFragment = new LearningFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);


        loadFragment(new HomeFragment());
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

            return true;
        }
        return false;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()) {

            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;

            case R.id.navigation_saved:
                fragment = new SavedFragment();
                break;

            case R.id.navigation_history:
                fragment = new HistoryFragment();
                break;

            case R.id.navigation_learning:
                fragment = new LearningFragment();
                break;


        }
        return loadFragment(fragment);


    }

    public void setNavigationVisibility(boolean visible) {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        if (navigation.isShown() && !visible) {
            navigation.setVisibility(View.GONE);
        }
        else if (!navigation.isShown() && visible){
            navigation.setVisibility(View.VISIBLE);
        }
    }



    @Override
    public void UpdateHighscore(int highscoreNew){
        learningFragment.updateHighscore(highscoreNew);
    }








}