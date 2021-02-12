package com.bingbong.effectivejava.item28;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");

        System.out.println("hello = " + strings);

        String[] stringss = new String[3];
        stringss[0] = "1";
        stringss[1] = "2";
        stringss[2] = "3";

        List<?>[] stringsLists = new List<?>[1];
    }
}
