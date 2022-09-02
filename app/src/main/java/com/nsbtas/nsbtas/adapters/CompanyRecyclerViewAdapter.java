package com.nsbtas.nsbtas.adapters;

import static com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper.setChosenCompany;
import static com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper.setIsCompanyChosen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nsbtas.nsbtas.R;
import com.nsbtas.nsbtas.models.Company;
import com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper;

import java.util.List;

public class CompanyRecyclerViewAdapter extends RecyclerView.Adapter<CompanyRecyclerViewAdapter.ViewHolder> {
    private final List<Company> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCompanyName;
        private final TextView tvPhoneNumber;

        public ViewHolder(View view) {
            super(view);

            tvCompanyName = view.findViewById(R.id.tvCompanyName);
            tvPhoneNumber = view.findViewById(R.id.tvPhoneNumber);
        }

        public TextView getTvCompanyName() {
            return tvCompanyName;
        }

        public TextView getTvPhoneNumber() {
            return tvPhoneNumber;
        }
    }

    public CompanyRecyclerViewAdapter(List<Company> dataSet) {
        localDataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_company, viewGroup, false);
        ImageView ivCheck = view.findViewById(R.id.ivIconChecked);

        view.setOnClickListener(view1 -> {
            if (!MultiStepPaymentFormHelper.isCompanyChosen()) {
                setIsCompanyChosen(true);
                setChosenCompany(localDataSet.get(localDataSet.size() - 1)); // FIXME: 1.09.2022 Temporary!
                ivCheck.setVisibility(View.VISIBLE);
            } else {
                if (ivCheck.getVisibility() == View.VISIBLE) {
                    setIsCompanyChosen(false);
                    setChosenCompany(null);
                    ivCheck.setVisibility(View.INVISIBLE);
                }
            }
        });

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTvCompanyName().setText(localDataSet.get(position).getCompanyName());
        viewHolder.getTvPhoneNumber().setText(localDataSet.get(position).getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
