package com.nsbtas.nsbtas;

import com.nsbtas.nsbtas.models.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListViewDataPump {
    public static HashMap<String, List<Card>> getData() {
        HashMap<String, List<Card>> expandableListDetail = new HashMap<>();

        List<Card> mCard = new ArrayList<>();
        List<Card> vCard = new ArrayList<>();

        mCard.add(new Card(1, "Mastercard", "4242 4242 4242 4242", "Test Test"));
        mCard.add(new Card(2, "Mastercard", "2424 2424 2424 2424", "Test User"));
        mCard.add(new Card(3, "Mastercard", "5555 5555 5555 5555", "User Test"));
        vCard.add(new Card(4, "Visa", "1234 5678 9012 3456", "User User"));

        expandableListDetail.put("Mastercard", mCard);
        expandableListDetail.put("Visa", vCard);
        return expandableListDetail;
    }
}