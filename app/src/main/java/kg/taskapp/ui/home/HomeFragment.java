package kg.taskapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;

import java.util.List;

import kg.taskapp.App;
import kg.taskapp.Note;
import kg.taskapp.ui.form.FormFragment;
import kg.taskapp.interfaces.OnItemClickListener;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private Note note;
    private int pos;
    private boolean toAdd;
    public static final String KEY_SAVED_CONTACT = "saved contact";
    public static final String KEY_SEND = "send data to FormFragment";
    public static final String KEY_EDIT_CONTACT = "edit saved contact";
    public static final String KEY_EDIT = "edit data key";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // placing the adapter here so it won't be recreated each time
        adapter = new TaskAdapter();
        List<Note>  list = App.database.noteDao().getAll();
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
            toAdd = true;
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.action_navigation_home_to_formFragment);

        });
        setFragmentListener();


    }

    private void setFragmentListener() {
        getParentFragmentManager().setFragmentResultListener(FormFragment.KEY_ADD,
                getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        note = (Note) result.getSerializable(FormFragment.KEY_NEW_CONTACT);
                        if (toAdd)
                            adapter.addItem(note);
                        else  adapter.editItem(note,pos);
                    }
                });
    }

    private void initList() {
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        // setting OnItemViewCLickListener's methods
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                toAdd = false;
                saveTheData(position);
                openFormFragment();
            }

            private void saveTheData(int position) {
                pos = position;
                Bundle bundle = new Bundle();
                Note note = adapter.getData(position);
                bundle.putSerializable(KEY_SAVED_CONTACT, note);
                getParentFragmentManager().setFragmentResult(KEY_SEND, bundle);
            }

            private void openFormFragment() {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_navigation_home_to_formFragment);
            }

            @Override
            public void onLongClick(int position) {
                deleteItem(position);
            }

            private void deleteItem(int position) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Delete Element");
                alert.setMessage("Are you sure you want to delete the contact?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapter.deleteItem(position);
                    }
                });
                alert.setNegativeButton("NO", (dialogInterface, i) -> {
                });
                alert.create().show();
            }
        });
    }

}
