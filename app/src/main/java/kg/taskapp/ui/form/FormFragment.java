package kg.taskapp.ui.form;

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

import kg.taskapp.App;
import kg.taskapp.Note;
import kg.taskapp.ui.home.HomeFragment;
import kg.taskapp.MainActivity;

import com.example.taskapp.R;


public class FormFragment extends Fragment {
    public static final String KEY_NEW_CONTACT = "contact";
    public static final String KEY_ADD = "insert a new Contact";
    private EditText editText;
    private Note note;
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

                        note = (Note) result.getSerializable(HomeFragment.KEY_SAVED_CONTACT);
                        savedItem = note.getNote();
                        editText.setText(savedItem);
                    }
                });

    }

    private void save() {
        text = editText.getText().toString();

        //Log.e highlighted in red color,and is shown in all the options (except Assert)
        Log.e("FormFragment", "text =  " + text);
        Note note = new Note(text, System.currentTimeMillis());

        // saving to Database
        App.database.noteDao().insert(note);

        // transferring info to Home fragment
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_NEW_CONTACT, note);
        getParentFragmentManager().setFragmentResult(KEY_ADD, bundle);

        // calling the closing fragment method from MainActivity
        ((MainActivity) requireActivity()).closeFragment();

    }
}