package com.bingbong.effectivejava.item7;

import java.util.Arrays;
import java.util.EmptyStackException;

public class CustomStack {

    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public CustomStack() {
        this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public CustomStack(int size) {
        this.elements = new Object[size];
    }

    public void push(final Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    // 잘못된 pop 방법
    public Object wrongPop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        // return만 하고 아무일도 하지 않으면 배열이 그대로 남아있다.
        return elements[--size];
    }

    public Object pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        Object result = elements[--size];
        // null을 할당해서 gc가 일어날 때 없애줄 수 있다.
        elements[size] = null;
        return result;
    }

    public Object size() {
        return this.elements.length;
    }

    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, (size * 2) + 1);
        }
    }

    public Object[] getElements() {
        return elements;
    }
}
