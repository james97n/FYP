package com.james.fyp;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

public class QuestionSet extends Fragment{

    protected View quizView;
    protected TextView textViewQuestion;
    protected TextView textViewScore;
    protected TextView textViewQuestionCount;
    protected TextView textViewCountDown;
    protected RadioGroup rbGroup;
    protected RadioButton rb1;
    protected RadioButton rb2;
    protected RadioButton rb3;
    protected Button buttonConfirmNext;

    protected ColorStateList textColorDefaultRb;

    protected List<Question> questionList;
    protected int questionCounter;
    protected int questionCountTotal;
    protected Question currentQuestion;

    protected int score;
    protected boolean answered;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        quizView = inflater.inflate(R.layout.fragment_quiz, container, false);
        //showFullScreen();
        ((MainActivity) getActivity()).setNavigationVisibility(false);

        textViewQuestion = quizView.findViewById(R.id.text_view_question);
        textViewScore = quizView.findViewById(R.id.text_view_score);
        textViewQuestionCount = quizView.findViewById(R.id.text_view_question_count);
        textViewCountDown = quizView.findViewById(R.id.text_view_countdown);
        rbGroup = quizView.findViewById(R.id.radio_group);
        rb1 = quizView.findViewById(R.id.radio_button1);
        rb2 = quizView.findViewById(R.id.radio_button2);
        rb3 = quizView.findViewById(R.id.radio_button3);
        buttonConfirmNext = quizView.findViewById(R.id.button_confirm_next);

        textColorDefaultRb = rb1.getTextColors();


        QuizDBHandler dbHelper = new QuizDBHandler(getContext());
        questionList = dbHelper.getAllQuestions();
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
        } else {
            finishQuiz();
        }
    }

    private void checkAnswer() {
        answered = true;

        RadioButton rbSelected = quizView.findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbSelected) + 1;

        if (answerNr == currentQuestion.getAnswerNr()) {
            score++;
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
                textViewQuestion.setText("Answer 1 is correct");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 2 is correct");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 3 is correct");
                break;
        }

        if (questionCounter < questionCountTotal) {
            buttonConfirmNext.setText("Next");
        } else {
            buttonConfirmNext.setText("Finish");
        }
    }

    private void finishQuiz() {
        getActivity().finish();
    }

}
