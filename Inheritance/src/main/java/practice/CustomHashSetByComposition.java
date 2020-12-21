package practice;

import java.util.Collection;
import java.util.HashSet;

public class CustomHashSetByComposition<E> {

    private final HashSet<E> hashSet;
    private int addCount = 0;

    public CustomHashSetByComposition(HashSet<E> hashSet) {
        this.hashSet = hashSet;
    }

    public boolean add(E e) {
        addCount++;
        return hashSet.add(e);
    }

    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return hashSet.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }
}
