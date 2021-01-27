package com.bingbong.effectivejava.item18;

import java.util.Collection;
import java.util.HashSet;

public class CustomHashSetByInheritance<E> extends HashSet<E> {

    private int addCount = 0;

    public CustomHashSetByInheritance() {
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }
}
