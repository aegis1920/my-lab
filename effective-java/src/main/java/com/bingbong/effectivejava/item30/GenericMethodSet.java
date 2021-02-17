package com.bingbong.effectivejava.item30;

import java.util.HashSet;
import java.util.Set;

public class GenericMethodSet {

    public static Set union(Set s1, Set s2) {
        Set result = new HashSet(s1);
        result.addAll(s2);
        return result;
    }

    // 타입 안전!
    public static <E> Set<E> genericUnion(Set<E> s1, Set<E> s2) {
        Set<E> result = new HashSet<>(s1);
        result.addAll(s2);
        return result;
    }
}
