package com.nsbtas.nsbtas.fragments;

import static com.nsbtas.nsbtas.network.CDAClient.getClient;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAEntry;
import com.nsbtas.nsbtas.R;
import com.nsbtas.nsbtas.adapters.ServiceRecyclerViewAdapter;
import com.nsbtas.nsbtas.models.Service;
import com.nsbtas.nsbtas.utils.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChooseServiceFragment extends Fragment {
    private Callback callback;
    private final List<Service> services = new ArrayList<>();

    public ChooseServiceFragment() {
    }

    public static ChooseServiceFragment newInstance() {
        return new ChooseServiceFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        new Thread(() -> {
            try {
                CDAArray services = getClient().fetch(CDAEntry.class).where("content_type", "service").all();
                for (CDAEntry entry : services.entries().values()) {
                    this.services.add(
                            new Service(
                                    entry.getField("id"),
                                    entry.getField("name"),
                                    entry.getField("description"),
                                    entry.getField("months"),
                                    entry.getField("price")
                            ));
                }
                if (callback != null) {
                    callback.callback();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }).start();

        return inflater.inflate(R.layout.fragment_choose_service, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.callback = () -> requireActivity().runOnUiThread(() -> {
            RecyclerView rvServices = view.findViewById(R.id.rvServices);
            rvServices.setLayoutManager(new LinearLayoutManager(requireActivity()));
            rvServices.setAdapter(new ServiceRecyclerViewAdapter(services));
        });
    }
}