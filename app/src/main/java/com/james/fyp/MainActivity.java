package com.james.fyp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

public  class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener, QuestionSet.QuestionSetListener,
        LearningFragment.LearningFragmentListener,
        GraphFragment.GraphFragmentListener {


    private LearningFragment learningFragment = new LearningFragment();
    private QuestionSet questionSet = new QuestionSet();


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

            case R.id.navigation_study:
                fragment = new StudyFragment();
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
    public void UpdateScore(int highscoreNew) {
        learningFragment.updateScore(highscoreNew);
    }

    @Override
    public void setDifficulty(String difficulty) {
        questionSet.setDifficulty(difficulty);
    }

    @Override
    public String loadPlayerName(){return learningFragment.loadPlayerName();}








}