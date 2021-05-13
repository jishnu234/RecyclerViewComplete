package com.example.recyclerviewcomplete;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerHelper extends RecyclerView.Adapter<RecyclerHelper.ViewHolder> {

    Context context;
    ArrayList<String> dataList;
    RecyclerViewInterface recyclerViewInterface;
    private static final String TAG = "RecyclerHelper";

    public RecyclerHelper(Context context, ArrayList<String> dataList,
                          RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.dataList = dataList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_layout,
                parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder: called ");
        holder.textView.setText(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Log.d(TAG, "ViewHolder: called");
            textView = itemView.findViewById(R.id.recyclerText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewInterface.onItemClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    recyclerViewInterface.onLongItemClick(getAdapterPosition());
                    return false;
                }
            });
        }

    }
}
