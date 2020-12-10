package com.example.taskapp.ui.form;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.taskapp.MainActivity;
import com.example.taskapp.R;
import com.example.taskapp.ui.home.Contact;
import com.example.taskapp.ui.home.HomeFragment;


public class FormFragment extends Fragment {
    public static final String KEY_NEW_CONTACT = "contact";
    public static final String KEY_ADD = "insert a new Contact";
    private EditText editText;
    private Contact contact;
    private String savedItem;
    private String text;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.edit_text);

        //short way to set OnCLickListener to Buttons, without initialization
        view.findViewById(R.id.btn_save).setOnClickListener(v -> {
            save();
        });
        setFragmentListener();


    }

    private void setFragmentListener() {
        getParentFragmentManager().setFragmentResultListener(HomeFragment.KEY_SEND,
                getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                        contact = (Contact) result.getSerializable(HomeFragment.KEY_SAVED_CONTACT);
                        savedItem = contact.getName();
                        editText.setText(savedItem);
                    }
                });

    }

    private void save() {
        text = editText.getText().toString();

        if (savedItem != null) editSavedItem();
        else addNewItem();

        // calling the closing fragment method from MainActivity
        ((MainActivity) requireActivity()).closeFragment();

    }

    private void editSavedItem() {
        Bundle bundle = new Bundle();
        Contact contact = new Contact(text);
        bundle.putSerializable(HomeFragment.KEY_EDIT_CONTACT, contact);
        getParentFragmentManager().setFragmentResult(HomeFragment.KEY_EDIT, bundle);
    }

    private void addNewItem() {

        //Log.e highlighted in red color,and is shown in all the options (except Assert)
        Log.e("FormFragment", "text =  " + text);

        // transferring info to another fragment
        Bundle bundle = new Bundle();
        Contact contact = new Contact(text);
        bundle.putSerializable(KEY_NEW_CONTACT, contact);
        getParentFragmentManager().setFragmentResult(KEY_ADD, bundle);

    }
}