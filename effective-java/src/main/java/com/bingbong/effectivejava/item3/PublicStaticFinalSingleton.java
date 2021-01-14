package com.bingbong.effectivejava.item3;

public class PublicStaticFinalSingleton {
    // 상수화를 통한 싱글턴 방식
    public static final PublicStaticFinalSingleton INSTANCE = new PublicStaticFinalSingleton();

    private PublicStaticFinalSingleton() {}
}
