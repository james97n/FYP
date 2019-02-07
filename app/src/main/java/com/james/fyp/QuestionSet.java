package com.james.fyp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuestionSet extends Fragment {

    //protected static final String EXTRA_SCORE = "extraScore";

    private QuestionSetListener listener;

    private static final long COUNTDOWN_IN_MILLIS = 10000;


    protected View quizView;
    protected TextView textViewQuestion;
    protected TextView textViewScore;
    protected TextView textViewQuestionCount;
    private TextView textViewDifficulty;
    protected TextView textViewCountDown;
    protected RadioGroup rbGroup;
    protected RadioButton rb1;
    protected RadioButton rb2;
    protected RadioButton rb3;
    protected Button buttonConfirmNext;
    protected static String difficulty;

    protected ColorStateList textColorDefaultRb;
    private ColorStateList textColorDefaultCd;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    protected List<Question> questionList;
    protected int questionCounter;
    protected int questionCountTotal;
    protected Question currentQuestion;

    protected int score;
    protected boolean answered;

    //private long backPressedTime;


    public interface QuestionSetListener{
        void UpdateHighscore(int highScore);
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        quizView = inflater.inflate(R.layout.fragment_quiz, container, false);
        ((MainActivity) getActivity()).setNavigationVisibility(false);

        textViewQuestion = quizView.findViewById(R.id.text_view_question);
        textViewScore = quizView.findViewById(R.id.text_view_score);
        textViewQuestionCount = quizView.findViewById(R.id.text_view_question_count);
        textViewDifficulty = quizView.findViewById(R.id.text_view_difficulty);
        textViewCountDown = quizView.findViewById(R.id.text_view_countdown);
        rbGroup = quizView.findViewById(R.id.radio_group);
        rb1 = quizView.findViewById(R.id.radio_button1);
        rb2 = quizView.findViewById(R.id.radio_button2);
        rb3 = quizView.findViewById(R.id.radio_button3);
        buttonConfirmNext = quizView.findViewById(R.id.button_confirm_next);

        textColorDefaultRb = rb1.getTextColors();
        textColorDefaultCd = textViewCountDown.getTextColors();


        textViewDifficulty.setText("Difficulty: " + difficulty);

        QuizDBHandler dbHelper = new QuizDBHandler(getContext());
        questionList = dbHelper.getQuestions(difficulty);
        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);

        showNextQuestion();

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(getContext(), "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });


        return quizView;

    }

    private void showNextQuestion() {
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();

        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());

            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answered = false;
            buttonConfirmNext.setText("Confirm");

            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();
        } else {
            finishQuiz();
        }
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewCountDown.setText(timeFormatted);

        if (timeLeftInMillis < 5000) {
            textViewCountDown.setTextColor(Color.RED);
        } else {
            textViewCountDown.setTextColor(textColorDefaultCd);
        }
    }

    private void checkAnswer() {
        answered = true;

        countDownTimer.cancel();

        RadioButton rbSelected = quizView.findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbSelected) + 1;

        if (answerNr == currentQuestion.getAnswerNr()) {
            if (difficulty.equals("Beginner")) {
                score++;
            } else if (difficulty.equals("Competent")) {
                score = score + 2;
            }
            textViewScore.setText("Score: " + score);
        }

        showSolution();
    }

    private void showSolution() {
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNr()) {
            case 1:
                rb1.setTextColor(Color.GREEN);
                //textViewQuestion.setText("Answer 1 is correct");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                //textViewQuestion.setText("Answer 2 is correct");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                //textViewQuestion.setText("Answer 3 is correct");
                break;
        }

        if (questionCounter < questionCountTotal) {
            buttonConfirmNext.setText("Next");
        } else {
            buttonConfirmNext.setText("Finish");
        }
    }

    public void finishQuiz() {

        int Score = score;
        listener.UpdateHighscore(Score);
        getFragmentManager().beginTransaction()
                .replace(((ViewGroup) getView().getParent()).getId(), new LearningFragment())
                .addToBackStack(null)
                .commit();
    }

    public void setDifficulty(String difficult) {

        difficulty = difficult;

    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof QuestionSetListener) {
            listener = (QuestionSetListener) context;
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }


}



