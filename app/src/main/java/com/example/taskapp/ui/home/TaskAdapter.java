package com.example.taskapp.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;
import com.example.taskapp.ui.ItemViewClickListener;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private ArrayList<String> list = new ArrayList<>();
    ItemViewClickListener listener;

    public TaskAdapter() {
    }
    public void setListener(ItemViewClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_task,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));

        //setting Background Color for Items (orange if position is even, blue if odd)
        if (position %2 == 0){
            ((LinearLayout)holder.itemView).setBackgroundResource(R.color.orange_light);
        } else {
            ((LinearLayout)holder.itemView).setBackgroundResource(R.color.blue_light);
        }

        // setting display of position by clicking the item
        holder.itemView.setOnClickListener(v->{
            listener.displayPosition(position);
        });

    }

    @Override
    public int getItemCount() {
        // if we put 2, but it's only 1 item in the list, it will through an error (Array out of bound exception)
        return list.size();
    }

    public void addList(ArrayList<String> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addItem(String text) {
        /*
        Adding new item to bottom of the list
        list.add(text);
        notifyItemInserted(list.size() - 1);
         */

        // adding item to top of the list
        list.add(0, text);
        notifyItemInserted(0);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }

        public void bind(String s) {
            textView.setText(s);
        }
    }
}
