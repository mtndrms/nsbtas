package com.nsbtas.nsbtas.utils;

import com.nsbtas.nsbtas.models.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableCardListMockData {
    public static HashMap<String, List<Card>> getData() {
        HashMap<String, List<Card>> expandableListDetail = new HashMap<>();

        List<Card> cards = new ArrayList<>();

        cards.add(new Card("Mastercard", "4242 4242 4242 4242", "Test Test", "01/01", "123"));
        cards.add(new Card("Visa", "2424 2424 2424 2424", "Test User", "02/02", "456"));
        cards.add(new Card("Mastercard", "5555 5555 5555 5555", "User Test", "03/03", "789"));
        cards.add(new Card("Visa", "1234 5678 9012 3456", "User User", "04/04", "123"));
        cards.add(new Card("Visa", "1234 5678 9012 3456", "User User", "05/05", "123"));

        expandableListDetail.put("Kredi Kartları", cards);
        return expandableListDetail;
    }

    public static List<Card> getDataList() {
        List<Card> cards = new ArrayList<>();

        cards.add(new Card("Mastercard", "4242 4242 4242 4242", "Test Test", "01/01", "123"));
        cards.add(new Card("Visa", "2424 2424 2424 2424", "Test User", "02/02", "456"));
        cards.add(new Card("Mastercard", "5555 5555 5555 5555", "User Test", "03/03", "789"));
        cards.add(new Card("Visa", "1234 5678 9012 3456", "User User", "04/04", "123"));
        cards.add(new Card("Visa", "1234 5678 9012 3456", "User User", "05/05", "123"));

        return cards;
    }
}