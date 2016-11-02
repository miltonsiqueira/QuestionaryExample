package com.titomilton.questionaryexample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class FillNameFragment extends Fragment {
    private static String NAME_KEY = "name";
    private OnFragmentInteractionListener mListener;
    private EditText editTextName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fill_name, container, false);
        editTextName = (EditText) view.findViewById(R.id.name);
        Button fillNameButton = (Button) view.findViewById(R.id.button_fill_name);
        fillNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(getContext(), "Please inform the name", Toast.LENGTH_LONG).show();
                } else {
                    mListener.onNameFilled(name);
                }
            }
        });
        return view;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            editTextName.setText(savedInstanceState.getString(NAME_KEY));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(NAME_KEY, editTextName.getText().toString());
        super.onSaveInstanceState(outState);
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
        void onNameFilled(String name);
    }
}
