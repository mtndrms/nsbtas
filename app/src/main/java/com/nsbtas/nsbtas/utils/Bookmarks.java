package com.nsbtas.nsbtas.utils;

import java.util.HashSet;
import java.util.Set;

public class Bookmarks {
    public static Set<String> all = new HashSet<String>() {{
        add("0");
        add("1");
        add("2");
        add("3");
        add("4");
    }};

    public static String getBookmarkById(int id) {
        switch (id) {
            case 0:
                return "Ödeme Yap";
            case 1:
                return "Hizmetler";
            case 2:
                return "Yardım";
            case 3:
                return "Profil";
            case 4:
                return "Kartlarım";
            case 5:
                return "Aboneliklerim";
            default:
                return null;
        }
    }
}
