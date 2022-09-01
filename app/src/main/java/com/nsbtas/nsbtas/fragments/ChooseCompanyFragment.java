package com.nsbtas.nsbtas.fragments;

import static com.nsbtas.nsbtas.network.CDAClient.getClient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.contentful.java.cda.CDAEntry;
import com.google.android.material.textfield.TextInputLayout;
import com.nsbtas.nsbtas.R;
import com.nsbtas.nsbtas.activities.SaveNewCompanyActivity;
import com.nsbtas.nsbtas.adapters.CompanyRecyclerViewAdapter;
import com.nsbtas.nsbtas.models.Company;
import com.nsbtas.nsbtas.utils.Callback;

import java.util.ArrayList;
import java.util.List;

public class ChooseCompanyFragment extends Fragment {
    private int serviceId;
    private Callback callback;
    private final List<Company> companies = new ArrayList<>();

    public ChooseCompanyFragment() {
    }

    public static ChooseCompanyFragment newInstance() {
        return new ChooseCompanyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("requestKey", this, (requestKey, bundle) -> serviceId = bundle.getInt("serviceId"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("NSBTAS", Context.MODE_PRIVATE);

        new Thread(() -> {
            try {
                List<CDAEntry> savedCompanies = getClient().fetch(CDAEntry.class).one(sharedPreferences.getString("userEntryId", "null")).getField("companies");
                for (CDAEntry entry : savedCompanies) {
                    companies.add(
                            new Company(
                                    entry.getField("companyName"),
                                    entry.getField("customerName"),
                                    entry.getField("phoneNumber"),
                                    entry.getField("emailAddress"),
                                    entry.getField("note"),
                                    entry.getField("address")
                            ));
                }
                if (callback != null) {
                    callback.callback();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }).start();

        return inflater.inflate(R.layout.fragment_choose_company, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.callback = () -> requireActivity().runOnUiThread(() -> {
            RecyclerView rvSavedCompanies = view.findViewById(R.id.rvSavedCompanies);
            rvSavedCompanies.setLayoutManager(new LinearLayoutManager(requireActivity()));
            rvSavedCompanies.setAdapter(new CompanyRecyclerViewAdapter(companies));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Bundle result = new Bundle();

        result.putInt("serviceId", serviceId);
        getParentFragmentManager().setFragmentResult("requestKey", result);
    }
}