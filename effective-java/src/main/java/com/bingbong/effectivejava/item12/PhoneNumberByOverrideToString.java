package com.bingbong.effectivejava.item12;

public class PhoneNumberByOverrideToString {

    private String areaCode;
    private String prefix;
    private String lineNum;

    public PhoneNumberByOverrideToString(String areaCode, String prefix, String lineNum) {
        this.areaCode = areaCode;
        this.prefix = prefix;
        this.lineNum = lineNum;
    }

    @Override
    public String toString() {
        return "PhoneNumber : { " + areaCode + "-" + prefix + "-" + lineNum + " }";
    }
}
