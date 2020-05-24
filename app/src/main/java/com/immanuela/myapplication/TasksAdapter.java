package com.immanuela.myapplication;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.media.CamcorderProfile.get;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    public interface OnLongClickListener{
        void onItemLongClicked(int position);

    }

    private List<String> tasks;
    OnLongClickListener longClickListener;

    TasksAdapter(List<String> tasks, OnLongClickListener longClickListener) {
        this.tasks =tasks;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Use layout inflator to inflate a view.
        View todoview = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false );

        //Wrap it insided a viewholder and return it

        return new ViewHolder(todoview);
    }

    @Override
    //Binding data to a particular view holder
    public void onBindViewHolder(@NonNull TasksAdapter.ViewHolder holder, int position) {
        //Grab the item at a position
        String task = tasks.get(position);
        //Bind the item into the specified view holder
        holder.bind(task);
    }
    //Tells the recyclerview how many items are in the list
    @Override
    public int getItemCount() {
        return tasks.size();
    }

    //Container to provide easy access to views that represent each row in a list
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvItem;

        ViewHolder(@NonNull View taskView) {
            super(taskView);
            tvItem = taskView.findViewById(android.R.id.text1);
        }

        //update the view inside the view holder with this data
        void bind(String task) {
            tvItem.setText(task);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //Notify the adapter which position was long clicked
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
