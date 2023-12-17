package com.example.app4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.app4.Adapter.BoxListAdapter;
import com.example.app4.DataBase.RoomDB;
import com.example.app4.Models.Box;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    RecyclerView recyclerView;
    FloatingActionButton fab_add, fab_info;
    BoxListAdapter boxListAdapter;
    RoomDB database;
    List<Box> boxes = new ArrayList<>();
    SearchView searchView_home;
    Box selectedBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_home);
        fab_add = findViewById(R.id.fab_add);
        database = RoomDB.getInstance(this);
        boxes = database.mainDAO().getAll();
        searchView_home = findViewById(R.id.searchView_home);
        fab_info = findViewById(R.id.fab_info);

        updateRecycler(boxes);


        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BoxTakerActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        searchView_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter (newText);
                return true;
            }
        });
    }

    private void filter(String newText) {
        List<Box> filteredList = new ArrayList<>();
        for(Box singleNote : boxes) {
            if(singleNote.getTitle().toLowerCase().contains(newText.toLowerCase())
                    || singleNote.getNotes().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(singleNote);
            }
        }
        boxListAdapter.filterList(filteredList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101) {
            if(resultCode == Activity.RESULT_OK) {
                Box new_box = (Box) data.getSerializableExtra("box");
                database.mainDAO().insert(new_box);
                boxes.clear();
                boxes.addAll(database.mainDAO().getAll());
                boxListAdapter.notifyDataSetChanged();
            }
        }

        if(requestCode == 102){
            if(resultCode == Activity.RESULT_OK) {
                Box new_box = (Box) data.getSerializableExtra("box");
                database.mainDAO().update(new_box.getID(), new_box.getTitle(), new_box.getNotes());
                boxes.clear();
                boxes.addAll(database.mainDAO().getAll());
                boxListAdapter.notifyDataSetChanged();
            }
        }
    }

    private void updateRecycler(List<Box> boxes) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        boxListAdapter = new BoxListAdapter(MainActivity.this, boxes, boxClickListener);
        recyclerView.setAdapter(boxListAdapter);

    }

    private final BoxClickListener boxClickListener = new BoxClickListener() {
        @Override
        public void onClick(Box box) {

            Intent intent = new Intent(MainActivity.this, BoxTakerActivity.class);
            intent.putExtra("old_box", box);
            startActivityForResult(intent, 102);

        }

        @Override
        public void onLongClick(Box box, CardView cardView) {
            selectedBox = box;
            showPopUp(cardView);
        }
    };

    private void showPopUp(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item.getItemId()==R.id.delete) {
            database.mainDAO().delete(selectedBox);
            boxes.remove(selectedBox);
            boxListAdapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public void showInfo(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Приложение создано в рамках курсового проекта студенкой физического факультета группы МС-32 Жихаревой Анастасией. ГГУ, 2023г");
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}