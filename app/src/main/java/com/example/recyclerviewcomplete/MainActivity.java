package com.example.recyclerviewcomplete;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity  implements RecyclerViewInterface{

    private RecyclerView recyclerView;
    RecyclerHelper helper;
    ArrayList<String> dataList;
    SwipeRefreshLayout refreshlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        refreshlayout = findViewById(R.id.swipe_refresh_layout);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(
                this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        addData();
        recyclerView.setHasFixedSize(true);
        helper = new RecyclerHelper(this, dataList, this);
        recyclerView.setAdapter(helper);

        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                dataList.add("Thor: Ragnarok (2017)");
                dataList.add("Ant-Man and the Wasp (2018)");
                dataList.add(" Avengers: Infinity War (2018)");
                dataList.add("Avengers: Endgame (2019)");
                dataList.add("Spider-Man: Far From Home (2019)");

                helper.notifyDataSetChanged();
                refreshlayout.setRefreshing(false);

            }
        });

    }

    private void addData() {

        dataList.add("Captain America: The First Avenger (2011)");
        dataList.add("Captain Marvel (2019)");
        dataList.add("Iron Man (2008)");
        dataList.add("Iron Man 2 (2010)");
        dataList.add("The Incredible Hulk (2008)");
        dataList.add("Thor (2011)");
        dataList.add("The Avengers (2012)");
        dataList.add("Thor: The Dark World (2013)");
        dataList.add("Guardians of the Galaxy (2014)");
        dataList.add("Guardians of the Galaxy Vol. 2 (2017)");
        dataList.add("Iron Man 3 (2013)");
        dataList.add("Captain America: The Winter Soldier (2014)");
        dataList.add("Avengers: Age of Ultron (2015)");
        dataList.add("Ant-Man (2015)");
        dataList.add("Captain America: Civil War (2016)");
        dataList.add("Black Panther (2018)");
        dataList.add("Spider-Man: Homecoming (2017)");
        dataList.add("Doctor Strange (2016)");

        ItemTouchHelper touchHelper = new ItemTouchHelper(simpleCallback);
        touchHelper.attachToRecyclerView(recyclerView);

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP |
            ItemTouchHelper.DOWN| ItemTouchHelper.START | ItemTouchHelper.END, ItemTouchHelper.LEFT |
            ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int endPosition = target.getAdapterPosition();

            Collections.swap(dataList, fromPosition, endPosition);

            if ((recyclerView.getAdapter()) != null)
                recyclerView.getAdapter().notifyItemMoved(fromPosition, endPosition);

            return false;
        }

        ArrayList<String> archivedList = new ArrayList<>();
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();
            switch (direction) {
                case ItemTouchHelper.LEFT :

                        String movieName = dataList.get(viewHolder.getAdapterPosition());
                        dataList.remove(position);
                        helper.notifyItemRemoved(position);

                    Snackbar.make(recyclerView, movieName + " deleted", BaseTransientBottomBar.LENGTH_LONG)
                            .setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dataList.add(position, movieName);
                                    helper.notifyItemInserted(position);
                                }
                            }).show();

                    break;
                case ItemTouchHelper.RIGHT :
                        archivedList.add(dataList.get(position));
                        String archiveMovieName = dataList.get(position);
                        Snackbar.make(recyclerView, archiveMovieName + ", Archived", BaseTransientBottomBar.LENGTH_LONG)
                            .setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    archivedList.remove(archivedList.lastIndexOf(archiveMovieName));
                                    dataList.add(position, archiveMovieName);
                                    helper.notifyItemInserted(position);

                                }
                            }).show();

                        dataList.remove(viewHolder.getAdapterPosition());
                        helper.notifyItemRemoved(viewHolder.getAdapterPosition());
                    break;
            }

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_archive_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    @Override
    public void onItemClick(int position) {

        Toast.makeText(this, dataList.get(position), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongItemClick(int position) {

//        dataList.remove(position);
//        helper.notifyItemRemoved(position);

    }

}