package com.nsbtas.nsbtas.ui.fragments;

import static com.nsbtas.nsbtas.LatestExpensesDataPump.getData;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nsbtas.nsbtas.R;
import com.nsbtas.nsbtas.adapters.ExpenseRecyclerViewAdapter;
import com.nsbtas.nsbtas.models.Expense;
import com.nsbtas.nsbtas.ui.activities.PaymentActivity;

import java.util.ArrayList;

public class HomepageFragment extends Fragment {
    public HomepageFragment() {
    }

    public static HomepageFragment newInstance() {
        return new HomepageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout btnMakePayment = view.findViewById(R.id.btnMakePayment);
        LinearLayout btnServices = view.findViewById(R.id.btnServices);
        LinearLayout btnHelp = view.findViewById(R.id.btnHelp);
        LinearLayout btnMore = view.findViewById(R.id.btnMore);

        btnMakePayment.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), PaymentActivity.class);
            startActivity(intent);
        });

        btnHelp.setOnClickListener(view12 -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:08504418818"));
            startActivity(callIntent);
        });

        RecyclerView rvLatestExpenses = view.findViewById(R.id.rvLatestExpenses);
        rvLatestExpenses.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvLatestExpenses.setAdapter(new ExpenseRecyclerViewAdapter(getData()));
    }
}