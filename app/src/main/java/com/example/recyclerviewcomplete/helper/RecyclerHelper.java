package com.example.recyclerviewcomplete.helper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewcomplete.R;
import com.example.recyclerviewcomplete.interfacerecycler.RecyclerViewInterface;

import java.util.ArrayList;
import java.util.Collection;

public class RecyclerHelper extends RecyclerView.Adapter<RecyclerHelper.ViewHolder>
implements Filterable {

    Context context;
    ArrayList<String> dataList;
    RecyclerViewInterface recyclerViewInterface;
    private static final String TAG = "RecyclerHelper";
    ArrayList<String> dataListAll;

    public RecyclerHelper(Context context, ArrayList<String> dataList,
                          RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.dataList = dataList;
        this.dataListAll = new ArrayList<>(dataList);
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_layout,
                parent, false);
        return new ViewHolder(view);
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

    @Override
    public Filter getFilter() {
        return filter;
    }


    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            ArrayList<String> filterList = new ArrayList<>();

            if(charSequence.toString().isEmpty()) {

                filterList.addAll(dataListAll);
            } else{

                for(String item: dataListAll)
                    if (item.toLowerCase().contains(charSequence.toString().toLowerCase()))
                        filterList.add(item);
            }
            FilterResults results = new FilterResults();
            results.values = filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            dataList.clear();
            dataList.addAll((Collection<? extends String>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Log.d(TAG, "ViewHolder: called");
            textView = itemView.findViewById(R.id.recyclerText);

            itemView.setOnClickListener(view -> recyclerViewInterface.onItemClick(getAdapterPosition()));

            itemView.setOnLongClickListener(view -> {

                recyclerViewInterface.onLongItemClick(getAdapterPosition());
                return false;
            });
        }

    }
}
