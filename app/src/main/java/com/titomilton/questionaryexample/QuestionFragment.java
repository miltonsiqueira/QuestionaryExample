package com.titomilton.questionaryexample;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String QUESTION_NUMBER = "question_number";
    private static final String TOTAL_QUESTIONS = "total_questions";

    private int mQuestionNumber;
    private int mTotalQuestions;
    private int mCorrectAnswerNumber = 1;
    private boolean isCorrect = false;

    private OnFragmentInteractionListener mListener;
    private Button buttonNext;


    public QuestionFragment() {

    }

    public static QuestionFragment newInstance(int questionNumber, int totalQuestions) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt(QUESTION_NUMBER, questionNumber);
        args.putInt(TOTAL_QUESTIONS, totalQuestions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTotalQuestions = getArguments().getInt(TOTAL_QUESTIONS);
            mQuestionNumber = getArguments().getInt(QUESTION_NUMBER);
        }
        if (savedInstanceState != null) {
            mTotalQuestions = savedInstanceState.getInt(TOTAL_QUESTIONS);
            mQuestionNumber = savedInstanceState.getInt(QUESTION_NUMBER);

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(QUESTION_NUMBER, mQuestionNumber);
        outState.putInt(TOTAL_QUESTIONS, mTotalQuestions);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        TextView textQuestionNumber = (TextView) view.findViewById(R.id.question_number);
        textQuestionNumber.setText("Question " + mQuestionNumber + "/" + mTotalQuestions);
        buttonNext = (Button) view.findViewById(R.id.button_next);
        buttonNext.setEnabled(false);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onQuestionAnswered(mQuestionNumber, mTotalQuestions, isCorrect);
            }
        });

        final int correctAnswerNumber = mCorrectAnswerNumber;
        RadioGroup radioGroupQuestions = (RadioGroup) view.findViewById(R.id.radio_group_questions);
        radioGroupQuestions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                buttonNext.setEnabled(true);
                isCorrect = (i == correctAnswerNumber);
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onQuestionAnswered(int questionNumber, int totalQuestions, boolean isCorrectAnswer);
    }
}
