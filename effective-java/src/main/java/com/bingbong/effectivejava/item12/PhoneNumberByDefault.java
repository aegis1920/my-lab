package com.bingbong.effectivejava.item12;

public class PhoneNumberByDefault {
    private String areaCode;
    private String prefix;
    private String lineNum;

    public PhoneNumberByDefault(String areaCode, String prefix, String lineNum) {
        this.areaCode = areaCode;
        this.prefix = prefix;
        this.lineNum = lineNum;
    }
}
