package com.example.taskapp.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;
import com.interfaces.OnItemClickListener;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private ArrayList<Contact> list = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public TaskAdapter() {
    }


    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));

    }

    @Override
    public int getItemCount() {
        // if we put 2, but it's only 1 item in the list, it will through an error (Array out of bound exception)
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void addList(ArrayList<Contact> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addItem(Contact contact) {
        /*
        Adding new item to bottom of the list
        list.add(text);
        notifyItemInserted(list.size() - 1);
         */

        // adding item to top of the list
        list.add(0, contact);
        notifyItemInserted(0);
    }


    public void editItem(Contact contact, int pos) {
        list.set(pos, contact);
        notifyItemChanged(pos);
    }

    public void deleteItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }


// return Contact
    public Contact getData(int position) {
       return new Contact(list.get(position).getName());

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // new Interface OnItemClickListener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(getAdapterPosition());
                }
            });
            // setting LongClickListener
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onLongClick(getAdapterPosition());
                    return true;
                }
            });
            textView = itemView.findViewById(R.id.textView);


        }

        public void bind(Contact contact) {
            textView.setText(contact.getName());

            //setting Background Color for Items (orange if position is even, blue if odd)
            if (getAdapterPosition() % 2 == 0) {
                itemView.setBackgroundResource(R.color.orange_light);
            } else {
                itemView.setBackgroundResource(R.color.blue_light);

            }
        }
    }
}
