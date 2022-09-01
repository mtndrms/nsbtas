package com.nsbtas.nsbtas.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nsbtas.nsbtas.R;
import com.nsbtas.nsbtas.models.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseRecyclerViewAdapter extends RecyclerView.Adapter<ExpenseRecyclerViewAdapter.ViewHolder> {
    private final List<Expense> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvFirm;
        private final TextView tvDate;
        private final TextView tvAmount;

        public ViewHolder(View view) {
            super(view);

            tvFirm = view.findViewById(R.id.tvFirm);
            tvDate = view.findViewById(R.id.tvDate);
            tvAmount = view.findViewById(R.id.tvAmount);
        }

        public TextView getTvFirm() {
            return tvFirm;
        }

        public TextView getTvDate() {
            return tvDate;
        }

        public TextView getTvAmount() {
            return tvAmount;
        }
    }

    public ExpenseRecyclerViewAdapter(List<Expense> dataSet) {
        localDataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_latest_activity, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.getTvFirm().setText(localDataSet.get(position).getService());
        viewHolder.getTvDate().setText(localDataSet.get(position).getDate());
        viewHolder.getTvAmount().setText(String.format("₺%s", localDataSet.get(position).getAmount()));
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
