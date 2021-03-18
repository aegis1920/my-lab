package com.bingbong.effectivejava.item65;

public class JustObject {

    private String constructorField;
    private String privateField = "private 필드입니다";
    public String publicField = "public 필드입니다";

    public JustObject(String constructorField) {
        this.constructorField = constructorField;
    }

    public String publicMethod() {
        return "public";
    }

    private String privateMethod() {
        return "private";
    }
}
