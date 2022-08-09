package com.nsbtas.nsbtas.adapters;

import static com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper.nextStage;
import static com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper.setCardId;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.nsbtas.nsbtas.R;
import com.nsbtas.nsbtas.models.Card;
import com.nsbtas.nsbtas.ui.views.ExpandableStackView;

import java.util.List;

public class ExpandableStackViewAdapter extends BaseAdapter {

    private List<Card> models;
    private Context context;
    private final Fragment fragment;
    private final ExpandableStackView expandableStackView;

    public ExpandableStackViewAdapter(List<Card> models, Context context, Fragment fragment, ExpandableStackView expandableStackView) {
        this.models = models;
        this.context = context;
        this.fragment = fragment;
        this.expandableStackView = expandableStackView;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Card getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
        LinearLayout cardContainer = view.findViewById(R.id.card);
        TextView tvCardOwnerInfo = view.findViewById(R.id.tvCardOwnerInfo);
        TextView tvCardNumber = view.findViewById(R.id.tvCardNumber);
        TextView tvExpirationDate = view.findViewById(R.id.tvExpirationDate);
        ImageView ivCardProvider = view.findViewById(R.id.ivLogo);
        Animation animationMove = AnimationUtils.loadAnimation(fragment.requireContext(), R.anim.move);

        /*
        animationMove.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setCardId(models.get(position).getId());
                nextStage(fragment.getParentFragmentManager());
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
         */

        tvCardOwnerInfo.setText(models.get(position).getCardOwner());
        tvCardNumber.setText(models.get(position).getCardNumber());
        tvExpirationDate.setText(models.get(position).getExpirationDate());

        if (models.get(position).getProvider().equals("Visa")) {
            cardContainer.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_card_visa));
            ivCardProvider.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.logo_visa));
        } else if (models.get(position).getProvider().equals("Mastercard")) {
            cardContainer.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_card_mastercard));
            ivCardProvider.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.logo_mastercard));
        } else {
            cardContainer.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_card_none));
        }

        cardContainer.setOnClickListener(card -> {
            if (expandableStackView.getCurrentState() == expandableStackView.getEndState()) {
                expandableStackView.transitionToStart();
                new Handler().postDelayed(() -> {
                    setCardId(models.get(position).getId());
                    nextStage(fragment.getParentFragmentManager());
                }, 600);
            } else {
                expandableStackView.transitionToStart();
            }
        });

        return view;
    }

    public List<Card> getModels() {
        return models;
    }

    public void setModels(List<Card> models) {
        this.models = models;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
