package com.nsbtas.nsbtas.adapters;

import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nsbtas.nsbtas.R;
import com.nsbtas.nsbtas.models.Service;
import com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper;

import java.util.List;
import java.util.Random;

public class ServiceRecyclerViewAdapter extends RecyclerView.Adapter<ServiceRecyclerViewAdapter.ViewHolder> {
    private final List<Service> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvDescription;
        private final ImageView ivIcon;

        public ViewHolder(View view) {
            super(view);

            tvName = view.findViewById(R.id.tvName);
            tvDescription = view.findViewById(R.id.tvDescription);
            ivIcon = view.findViewById(R.id.ivIcon);
        }

        public TextView getTvName() {
            return tvName;
        }

        public TextView getTvDescription() {
            return tvDescription;
        }

        public ImageView getIvIcon() {
            return ivIcon;
        }
    }

    public ServiceRecyclerViewAdapter(List<Service> dataSet) {
        localDataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_service, viewGroup, false);
        TransitionDrawable transitionDrawable = (TransitionDrawable) view.getBackground();

        view.setOnClickListener(view1 -> {
            if (!MultiStepPaymentFormHelper.isServiceChosen()) {
                MultiStepPaymentFormHelper.setIsServiceChosen(true);
                transitionDrawable.startTransition(100);
            } else {
                MultiStepPaymentFormHelper.setIsServiceChosen(false);
                transitionDrawable.reverseTransition(100);
            }
        });

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        int randomColor = Color.rgb(r, g, b);

        viewHolder.getTvName().setText(localDataSet.get(position).getName());
        viewHolder.getTvDescription().setText(localDataSet.get(position).getDescription());
        viewHolder.getIvIcon().setColorFilter(randomColor);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
