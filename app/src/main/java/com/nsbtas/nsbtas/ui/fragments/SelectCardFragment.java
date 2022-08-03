package com.nsbtas.nsbtas.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.nsbtas.nsbtas.ExpandableListViewDataPump;
import com.nsbtas.nsbtas.R;
import com.nsbtas.nsbtas.adapters.ExpandableListViewCustomAdapter;
import com.nsbtas.nsbtas.models.Card;
import com.nsbtas.nsbtas.ui.activities.AddNewCardActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SelectCardFragment extends Fragment {

    ExpandableListView expandableListView;
    ExpandableListViewCustomAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<Card>> expandableListDetail;
    int cardChosen = 0;

    public SelectCardFragment() {
    }

    public static SelectCardFragment newInstance() {
        return new SelectCardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvAddNewCard = view.findViewById(R.id.btnAddNewCard);

        tvAddNewCard.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), AddNewCardActivity.class);
            startActivity(intent);
        });

        expandableListDetail = ExpandableListViewDataPump.getData();
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListView = view.findViewById(R.id.elvSavedCards);
        expandableListAdapter = new ExpandableListViewCustomAdapter(getContext(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {

            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {

            }
        });

        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            return false;
        });
    }
}