package com.example.taskapp.ui.form;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.taskapp.MainActivity;
import com.example.taskapp.R;


public class FormFragment extends Fragment {
    private EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.editText);

        //short way to set OnCLickListener to Buttons, without initialization
       view.findViewById(R.id.btn_save).setOnClickListener(v-> {
           save();
        });
    }

    private void save() {
        // to get the text from EditText better to initialize it as below
        String text = editText.getText().toString();

        //Log.e highlighted in red color,and is shown in all the options (except Assert)
        Log.e("FormFragment", "text =  " + text);

        // transferring info to another fragment
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        getParentFragmentManager().setFragmentResult("rk_task", bundle);
        // calling the closing fragment from MainActivity
        ((MainActivity) requireActivity()).closeFragment();
    }
}