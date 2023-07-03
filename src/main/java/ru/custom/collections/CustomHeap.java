package ru.custom.collections;

import java.util.Arrays;
import java.util.Objects;

public class CustomHeap<T extends Comparable<T>> implements Heap<T>{
    private Object[] data;
    private int size;

    public CustomHeap() {
        data = new Object[10];
        size = 0;
    }

    public CustomHeap(int initialCapacity) {
        data = new Object[initialCapacity];
        size = 0;
    }

    public CustomHeap(T[] initialArray) {
        this.data = new Object[initialArray.length];
        size = initialArray.length;
        System.arraycopy(initialArray, 0, this.data, 0, initialArray.length);
        for (int i = initialArray.length/2 - 1; i >= 0; --i) {
            siftDown(i);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int capacity() {
        return data.length;
    }

    @Override
    public void setCapacity(int capacity) {
        if (capacity == data.length) return;
        Object[] updatedData = new Object[capacity];
        System.arraycopy(data, 0, updatedData, 0, Math.min(data.length, capacity));
        this.data = updatedData;
    }

    @SuppressWarnings("unchecked")
    private void siftDown(int index) {
        int left = 2*index + 1;
        int right = 2*index + 2;
        int largest = index;
        if (left < size && left > 0 && ((T) data[left]).compareTo((T) data[index]) >= 0) {
            largest = left;
        }
        if (right < size && right > 0 && ((T) data[right]).compareTo((T) data[largest]) >= 0) {
            largest = right;
        }
        if (largest != index) {
            Object t = data[index];
            data[index] = data[largest];
            data[largest] = t;
            siftDown(largest);
        }
    }

    @SuppressWarnings("unchecked")
    private void siftUp(int index) {
        int parent = (index - 1) / 2;
        while (index > 0) {
            if (((T) data[parent]).compareTo((T) data[index]) >= 0) {
                return;
            } else {
                Object t = data[index];
                data[index] = data[parent];
                data[parent] = t;
                siftUp(parent);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T popMaximum() {
        T element = (T) data[0];
        data[0] = data[size - 1];
        --size;
        siftDown(0);
        return element;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getMaximum() {
        Objects.checkIndex(0, size);
        return (T) data[0];
    }

    @Override
    public boolean add(T element) {
        if (element == null) return false;
        if (size == Integer.MAX_VALUE) return false;
        if (size == this.capacity()) {
            int newSize;
            if (size > Integer.MAX_VALUE/2) {
                newSize = Integer.MAX_VALUE;
            } else {
                newSize = size*2;
            }
            this.setCapacity(newSize);
        }
        data[size] = element;
        ++size;
        siftUp(size - 1);
        return true;
    }

    public T[] toSortedArray(T[] arr) {
        sort();
        return toArray(arr);
    }

    @SuppressWarnings("unchecked")
    public T[] toArray(T[] arr) {
        if (arr.length < size) {
            return (T[]) Arrays.copyOf(data, size, arr.getClass());
        }
        System.arraycopy(data, 0, arr, 0, size);
        if (arr.length > size) {
            arr[size] = null;
        }
        return arr;
    }

    private void sort() {
        int initialSize = size;
        Object[] initialArray = Arrays.copyOf(data, data.length);
        while (size > 1) {
            Object t = data[size - 1];
            data[size - 1] = data[0];
            data[0] = t;
            --size;
            siftDown(0);
        }
        size = initialSize;
        data = initialArray;
    }
}
