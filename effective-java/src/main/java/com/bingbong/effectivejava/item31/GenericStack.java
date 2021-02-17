package com.bingbong.effectivejava.item31;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GenericStack<E> {

    private final List<E> stack = new ArrayList<>();
    private int position = -1;

    public void push(E element) {
        position += 1;
        stack.add(element);
    }

    public E pop() {
        E popElement = stack.remove(position);
        position -= 1;
        return popElement;
    }

    // 제네릭이 불공변이라 다른 타입은 못들어감
    // GenericStack<Number> numberStack = new GenericStack<>(); 로 선언해놓고 Integer List를 넣으려 하는 경우 안됨!
    // Number보다 Object인 걸 받으려면 super!
//    public void pushAll(Collection<E> elements) {
//        position += elements.size();
//        stack.addAll(elements);
//    }

    // 요렇게 하면 가능!
    public void pushAll(Collection<? extends E> elements) {
        position += elements.size();
        stack.addAll(elements);
    }

    public boolean isEmpty() {
        return position == -1;
    }

    public int stackSize() {
        return position + 1;
    }
}
