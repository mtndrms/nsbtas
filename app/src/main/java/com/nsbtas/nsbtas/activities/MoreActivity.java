package com.nsbtas.nsbtas.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nsbtas.nsbtas.R;
import com.nsbtas.nsbtas.utils.Bookmarks;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MoreActivity extends AppCompatActivity {
    List<String> bookmarks = new ArrayList<>();
    List<String> filtered = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        SharedPreferences sharedPreferences = getSharedPreferences("NSBTAS", Context.MODE_PRIVATE);

        TextView tvDone = findViewById(R.id.tvDone);
        ListView lvBookmarks = findViewById(R.id.lvBookmarks);
        ListView lvUnselected = findViewById(R.id.lvCanBeSelected);

        for (String bookmarkId : sharedPreferences.getStringSet("bookmarks", null)) {
            bookmarks.add(Bookmarks.getBookmarkById(Integer.parseInt(bookmarkId)));
        }

        for (String bookmarkId : Bookmarks.all.stream().filter(e -> !sharedPreferences.getStringSet("bookmarks", null).contains(e)).collect(Collectors.toSet())) {
            filtered.add(Bookmarks.getBookmarkById(Integer.parseInt(bookmarkId)));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_bookmark, R.id.label, bookmarks);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.item_bookmark, R.id.label, filtered);
        lvBookmarks.setAdapter(adapter);
        lvUnselected.setAdapter(adapter2);

        lvUnselected.setOnItemClickListener((adapterView, view, i, l) -> System.out.println("TEST"));

        tvDone.setOnClickListener(view -> finish());
    }
}