package com.nsbtas.nsbtas.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nsbtas.nsbtas.R;

public class ChooseServiceFragment extends Fragment {
    private String title;
    private int page;

    private int serviceChosen = 0;

    public ChooseServiceFragment() {
    }

    public static ChooseServiceFragment newInstance(String title, int page) {
        ChooseServiceFragment chooseServiceFragment = new ChooseServiceFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("page", page);
        chooseServiceFragment.setArguments(args);
        return chooseServiceFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            title = getArguments().getString("title", "no title");
            page = getArguments().getInt("page", 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_service, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout service1 = view.findViewById(R.id.service1);
        LinearLayout service2 = view.findViewById(R.id.service2);
        LinearLayout service3 = view.findViewById(R.id.service3);

        service1.setOnClickListener(view1 -> {
            this.serviceChosen = 1;
        });

        service2.setOnClickListener(view12 -> {
            this.serviceChosen = 2;
        });

        service3.setOnClickListener(view13 -> {
            this.serviceChosen = 3;
        });
    }

    public int getServiceChosen() {
        return serviceChosen;
    }
}