package com.bingbong.effectivejava.item28;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Chooser {

    private final Object[] choiceArray;

    public Chooser(Collection choices) {
        this.choiceArray = choices.toArray();
    }

    public Object choose() {
        Random rnd = ThreadLocalRandom.current();
        return choiceArray[rnd.nextInt(choiceArray.length)];
    }
}

class ChooserByGeneric<T> {

    private final T[] choiceArray;

    public ChooserByGeneric(Collection<T> choices) {
        // T가 무슨 타입인지 알 수 없으니 컴파일러는 형변환이 런타임에도 안전한 지 보장할 수 없다는 메시지
        // 원소의 타입 정보가 소거된다. 동작하나 컴파일러가 안전을 보장하지 못할 뿐. 코드를 작성하는 사람이 안전하다고 확신한다면 주석을 남기고 애너테이션을 달아 숨겨도
        this.choiceArray = (T[]) choices.toArray();
    }

    // choose() 메서드는 동일
}


// 런타임에 ClassCastException을 만날 일 없음!
class ChooserByList<T> {

    private final List<T> choiceList;

    ChooserByList(Collection<T> choiceList) {
        this.choiceList = new ArrayList<>(choiceList);
    }

    public Object choose() {
        Random rnd = ThreadLocalRandom.current();
        return choiceList.get(rnd.nextInt(choiceList.size()));
    }
}
