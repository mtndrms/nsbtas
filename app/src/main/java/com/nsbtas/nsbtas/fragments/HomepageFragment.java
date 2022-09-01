package com.nsbtas.nsbtas.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAEntry;
import com.nsbtas.nsbtas.R;
import com.nsbtas.nsbtas.adapters.ExpenseRecyclerViewAdapter;
import com.nsbtas.nsbtas.models.Expense;
import com.nsbtas.nsbtas.activities.MoreActivity;
import com.nsbtas.nsbtas.activities.PaymentActivity;
import com.nsbtas.nsbtas.network.CDAClient;
import com.nsbtas.nsbtas.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class HomepageFragment extends Fragment {
    CDAArray usersExpenses = null;

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

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("NSBTAS", Context.MODE_PRIVATE);
        LinearLayout btnBookmarkFirst = view.findViewById(R.id.btnBookmarkFirst);
        LinearLayout btnBookmarkSecond = view.findViewById(R.id.btnBookmarkSecond);
        LinearLayout btnBookmarkThird = view.findViewById(R.id.btnBookmarkThird);
        LinearLayout btnMore = view.findViewById(R.id.btnMore);
        ImageView btnProfilePicture = view.findViewById(R.id.ivCustomerProfilePicture);
        TextView tvCustomerUsername = view.findViewById(R.id.tvCustomerUsername);

        List<Expense> expenses = new ArrayList<>();
        new Thread(() -> {
            try {
                usersExpenses = CDAClient.getClient().fetch(CDAEntry.class)
                        .where("content_type", "expense")
                        .where("fields.user", sharedPreferences.getString("userEntryId", "null"))
                        .all();

                for (CDAEntry entry : usersExpenses.entries().values()) {
                    expenses.add(
                            new Expense(
                                    Utils.getServiceById(Integer.parseInt(entry.getField("service"))),
                                    entry.getField("date"),
                                    entry.getField("amount").toString()
                            )
                    );
                }

                requireActivity().runOnUiThread(() -> {
                    RecyclerView rvLatestExpenses = view.findViewById(R.id.rvLatestExpenses);
                    rvLatestExpenses.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rvLatestExpenses.setAdapter(new ExpenseRecyclerViewAdapter(expenses));
                });

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }).start();

        tvCustomerUsername.setText(sharedPreferences.getString("username", "null"));

        btnBookmarkFirst.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), PaymentActivity.class);
            startActivity(intent);
        });

        btnBookmarkThird.setOnClickListener(view12 -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:08504418818"));
            startActivity(callIntent);
        });

        btnProfilePicture.setOnClickListener(view13 -> {
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, new ProfileFragment(), "PROFILE_FRAGMENT");
            fragmentTransaction.addToBackStack("PROFILE_FRAGMENT");
            fragmentTransaction.commit();
        });

        btnMore.setOnClickListener(view14 -> {
            Intent intent = new Intent(requireContext(), MoreActivity.class);
            startActivity(intent);
        });
    }
}