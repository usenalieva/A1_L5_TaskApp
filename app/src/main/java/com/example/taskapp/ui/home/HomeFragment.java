package com.example.taskapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements ItemViewClickListener {
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private String text;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // placing the adapter here so it won't be recreated each time
        adapter = new TaskAdapter();
        ArrayList<String> list = new ArrayList<>();
        list.add("Omurzak");
        list.add("Mahabat");
        list.add("Atai");
        list.add("Nazar");
        list.add("Ainazik");
        list.add("Ermek");
        list.add("Daniar");
        adapter.addList(list);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        initList();

        view.findViewById(R.id.fab).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.action_navigation_home_to_formFragment);

        });
        setFragmentListener();


    }

    private void setFragmentListener() {
        getParentFragmentManager().setFragmentResultListener("rk_task",
                getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        text = result.getString("text");
                        if (text != null)
                            adapter.addItem(text);
                    }
                });
    }

    private void initList() {
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

    }

    // setting ItemViewCLickListener's method displaying a position
    @Override
    public void displayPosition(int pos) {
        Toast.makeText(getContext(), "Item position: " + pos, Toast.LENGTH_SHORT).show();

    }
}

interface ItemViewClickListener {
    void displayPosition(int pos);
}