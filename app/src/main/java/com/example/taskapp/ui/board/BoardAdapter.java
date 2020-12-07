package com.example.taskapp.ui.board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;

import java.util.ArrayList;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private BoardsButtonsListener boardListener;
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> desc = new ArrayList<>();
    private ArrayList<Integer> photos = new ArrayList<>();


    public BoardAdapter() {

        titles.add("Fast");
        titles.add("Free");
        titles.add("Powerful");

        desc.add("Fast Fast Fast Fast Fast Fast Fast ");
        desc.add("Free");
        desc.add("Powerful");

        photos.add(R.drawable.ic_home_black_24dp);
        photos.add(R.drawable.ic_launcher_background);
        photos.add(R.drawable.alima_profile);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pager_board, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitle;
        private TextView textDesc;
        private ImageView imageView;
        private Button btnLogin;
        private Button btnSkip;
        private ImageView dot1;
        private ImageView dot2;
        private ImageView dot3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            init(itemView);

        }

        private void init(View itemView) {

            textTitle = itemView.findViewById(R.id.textTitle);
            textDesc = itemView.findViewById(R.id.textDesc);
            imageView = itemView.findViewById(R.id.imageView);
            btnLogin = itemView.findViewById(R.id.btn_login);
            btnSkip = itemView.findViewById(R.id.btn_skip);
//            dot1 = itemView.findViewById(R.id.dot1);
//            dot2 = itemView.findViewById(R.id.dot2);
//            dot3 = itemView.findViewById(R.id.dot3);
            btnLogin.setOnClickListener(v->{
                boardListener.openMainPage();
            });
            btnSkip.setOnClickListener(v->{
                boardListener.openMainPage();
            });
        }

        public void bind(int position) {
            textTitle.setText(titles.get(position));
            imageView.setImageResource(photos.get(position));
            textDesc.setText(desc.get(position));
            btnLoginSetUP(position);

        }

        private void btnLoginSetUP(int position) {
            if (position == 2)
                btnLogin.setVisibility(View.VISIBLE);
            else
                btnLogin.setVisibility(View.GONE);
        }

    }
    public  interface BoardsButtonsListener {
        void openMainPage();
    }

    public void setOnButtonListener( BoardsButtonsListener listener) {
        boardListener = listener;
    }


}

