package com.nsbtas.nsbtas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.nsbtas.nsbtas.R;
import com.nsbtas.nsbtas.models.Card;

import java.util.HashMap;
import java.util.List;

public class ExpandableListViewCustomAdapter extends BaseExpandableListAdapter {

    private final List<String> expandableListTitle;
    private final Context context;
    HashMap<String, List<Card>> expandableListDetail;
    private Boolean cardChosen = false;

    public ExpandableListViewCustomAdapter(Context context, List<String> expandableListTitle, HashMap<String, List<Card>> expandableListDetail) {
        this.context = context;
        this.expandableListDetail = expandableListDetail;
        this.expandableListTitle = expandableListTitle;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Card expandedListText = (Card) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_card, null);
        }
        TextView tvCardOwner = convertView.findViewById(R.id.tvCardOwnerInfo);
        TextView tvCardNumber = convertView.findViewById(R.id.tvCardNumber);
        ImageView ivCardProvider = convertView.findViewById(R.id.ivLogo);
        ImageView selected = convertView.findViewById(R.id.ivSelected);
        if (expandedListText.getProvider().equals("Visa")) {
            ivCardProvider.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.logo_visa));
        } else {
            ivCardProvider.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.logo_mastercard));
        }
        tvCardOwner.setText(expandedListText.getCardOwner());
        tvCardNumber.setText(String.format("**** %s", expandedListText.getCardNumber().substring(expandedListText.getCardNumber().length() - 4)));
        convertView.setOnClickListener(view -> {
            if (!cardChosen) {
                if (selected.getVisibility() == View.VISIBLE) {
                    cardChosen = false;
                    selected.setVisibility(View.INVISIBLE);
                } else {
                    cardChosen = true;
                    selected.setVisibility(View.VISIBLE);
                }
            } else {
                if (selected.getVisibility() == View.VISIBLE) {
                    cardChosen = false;
                    selected.setVisibility(View.INVISIBLE);
                }
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return expandableListDetail.get(this.expandableListTitle.get(listPosition)).size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_elv_group, null);
        }
        TextView listTitleTextView = convertView.findViewById(R.id.tvCardProvider);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
