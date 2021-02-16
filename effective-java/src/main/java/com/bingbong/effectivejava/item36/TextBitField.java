package com.bingbong.effectivejava.item36;

public class TextBitField {
    public static final int STYLE_BOLD = 1 << 0; // 1
    public static final int STYLE_ITALIC = 1 << 1; // 2
    public static final int STYLE_UNDERLINE = 1 << 2; // 4
    public static final int STYLE_STRIKETHROUGH = 1 << 3; // 8

    public static int applyStyles(int styles) {
        return styles;
    }

    public static void main(String[] args) {
        System.out.println(applyStyles(STYLE_BOLD | STYLE_ITALIC));
        System.out.println(applyStyles(STYLE_BOLD | STYLE_UNDERLINE));
    }
}
