package com.bingbong.effectivejava.item36;


import java.util.EnumSet;
import java.util.Set;

public class TextEnumSet {

    public enum Style { BOLD, ITALIC, UNDERLINE, STRIKETHROUGH }

    public static void applyStyles(Set<Style> styles) {
    }

    public static void main(String[] args) {
        applyStyles(EnumSet.of(Style.BOLD, Style.ITALIC, Style.UNDERLINE));
    }
}
