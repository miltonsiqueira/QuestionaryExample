package com.titomilton.questionaryexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements FillNameFragment.OnFragmentInteractionListener, QuestionFragment.OnFragmentInteractionListener {
    private static final int TOTAL_QUESTIONS = 5;
    private static String KEY_NAME = "mName";
    private static String KEY_CURRENT_QUESTION = "mName";
    private static String KEY_RESULT_ANSWERS = "mResultAnswers";
    private String mName;
    private int mCurrentQuestion = 0;
    private boolean[] mResultAnswersQuestion = new boolean[TOTAL_QUESTIONS];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            fillName();
        }
    }

    private void fillName() {
        // TODO search what is the best practice or difference for show a fragment use add() or replace()
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new FillNameFragment())
                .commit();
    }


    @Override
    public void onQuestionAnswered(int questionNumber, int totalQuestions, boolean isCorrectAnswer) {
        mResultAnswersQuestion[questionNumber - 1] = isCorrectAnswer;
        mCurrentQuestion = questionNumber + 1;

        if (mCurrentQuestion > TOTAL_QUESTIONS) {
            mCurrentQuestion = 0;
            mResultAnswersQuestion = new boolean[TOTAL_QUESTIONS];
            fillName();
        } else {
            nextQuestion();
        }

    }

    @Override
    public void onNameFilled(String name) {
        this.mName = name;

        mCurrentQuestion = 1;

        nextQuestion();
    }

    private void nextQuestion() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, QuestionFragment.newInstance(mCurrentQuestion, TOTAL_QUESTIONS))
                .commit();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_NAME, mName);
        outState.putInt(KEY_CURRENT_QUESTION, mCurrentQuestion);
        outState.putBooleanArray(KEY_RESULT_ANSWERS, mResultAnswersQuestion);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mName = savedInstanceState.getString(KEY_NAME);
        mCurrentQuestion = savedInstanceState.getInt(KEY_CURRENT_QUESTION);
        mResultAnswersQuestion = savedInstanceState.getBooleanArray(KEY_RESULT_ANSWERS);
    }

}
