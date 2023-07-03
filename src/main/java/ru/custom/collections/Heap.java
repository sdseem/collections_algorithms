package ru.custom.collections;

/**
 * @author Dmitrii Samsonov email: sdseem@yandex.ru
 * @param <T>
 */
public interface Heap<T extends Comparable<T>> {
    int size();
    int capacity();
    void setCapacity(int capacity);
    T popMaximum();
    T getMaximum();
    boolean add(T element);

    default boolean isEmpty() {
        return this.size() == 0;
    }
}
