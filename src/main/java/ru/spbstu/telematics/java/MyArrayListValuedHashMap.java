package ru.spbstu.telematics.java;

import java.util.*;

@SuppressWarnings("rawtypes")
public class MyArrayListValuedHashMap <K,V> implements Map<K,V> {
    private static final int INIT_MAP_CAPACITY = 16;
    private static final int INIT_LIST_CAPACITY = 3;
    //private final int initialListCapacity;
    //private final int mapSize;

    private class MyArrayList <V> {
        private int size;
        private int capacity;
        private static final int DEFAULT_CAPACITY_INC = 3;
        V[] arr;

        public MyArrayList() {
            this(0);
        }

        public MyArrayList(int size) {
            this.size = size;
            this.capacity = this.size;
            arr = (V[]) new Object[this.capacity];
        }

        public boolean add(V toAdd) {
            if (this.size < this.capacity) {
                arr[size] = toAdd;
                size++;
                return true;
            }
            else {
                V[] newArr = (V[]) new Object[capacity + DEFAULT_CAPACITY_INC];
                for(int i = 0; i < size; i++) {
                    newArr[i] = arr[i];
                }
                newArr[size] = toAdd;
                arr = newArr;
                newArr = null;
                size++;
                capacity += DEFAULT_CAPACITY_INC;
                return true;
            }
        }

        public void clear() {
            size = 0;
            capacity = 0;
            V[] newArr = (V[]) new Object[capacity];
            arr = newArr;
            newArr = null;
        }

        public V get(int index) throws ArrayIndexOutOfBoundsException {
            if(0 < index || index >= size) {
                throw new ArrayIndexOutOfBoundsException();
            }
            return arr[index];
        }
        public V remove(int index) throws ArrayIndexOutOfBoundsException {
            if(0 < index || index >= size) {
                throw new ArrayIndexOutOfBoundsException();
            }
            V[] newArr = (V[]) new Object[capacity];
            for(int i = 0; i  < index; i++)
                newArr[i] = arr[i];
            V returned = arr[index];
            for(int i = index + 1; i < size; i++)
                newArr[i-1] = arr[i];
            size--;
            arr = newArr;
            return returned;
        }
    }

    public MyArrayListValuedHashMap() {
        this(INIT_MAP_CAPACITY, INIT_LIST_CAPACITY);
    }

    public MyArrayListValuedHashMap(int initialListCapacity) {
        this(INIT_MAP_CAPACITY, initialListCapacity);
    }

    public MyArrayListValuedHashMap(int initialMapCapacity, int initialListCapacity) {
        //super(new HashMap(initialMapCapacity));
        //this.initialListCapacity = initialListCapacity;
    }


    public int size() {
        return 0;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean containsKey(Object key) {
        return false;
    }

    public boolean containsValue(Object value) {
        return false;
    }

    public V get(Object key) {
        return null;
    }

    public V put(K key, V value) {
        return null;
    }

    public V remove(Object key) {
        return null;
    }

    public void putAll(Map<? extends K, ? extends V> m) {

    }

    public void clear() {

    }

    public Set<K> keySet() {
        return null;
    }

    public Collection<V> values() {
        return null;
    }

    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
