package ru.spbstu.telematics.java;

import org.apache.commons.math3.util.Pair;

import java.util.*;


import static java.lang.Math.abs;

@SuppressWarnings("rawtypes")
public class MyArrayListValuedHashMap <K,V> implements Map<K,V> {

    class Node<K, V> {

        private final K key;
        private V value;
        int hashCode;
        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.hashCode = key.hashCode();
        }

        public final K getKey() { return key; }

        public V getValue() { return value; }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        /*public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Map.Entry) {
                Map.Entry<?,?> e = (Map.Entry<?,?>)o;
                if (Objects.equals(key, e.getKey()) &&
                        Objects.equals(value, e.getValue()))
                    return true;
            }
            return false;
        }*/
    }

    private static final int INIT_MAP_CAPACITY = 16;
    private static final int INIT_LIST_CAPACITY = 3;
    private final int initialListCapacity;
    private int mapContentsAmount;
    private ArrayList<Node<K,V>>[] hashTable;

    public MyArrayListValuedHashMap() {
        this(INIT_MAP_CAPACITY, INIT_LIST_CAPACITY);
    }

    public MyArrayListValuedHashMap(int initialListCapacity) {
        this(INIT_MAP_CAPACITY, initialListCapacity);
    }

    public MyArrayListValuedHashMap(int initialMapCapacity, int initialListCapacity) {
        hashTable = (ArrayList<Node<K,V>>[]) new Object[initialMapCapacity];
        this.initialListCapacity = initialListCapacity;
        for(int i = 0; i < initialMapCapacity; i++) {
            hashTable[i] = new ArrayList<Node<K,V>>(initialListCapacity);
        }
        mapContentsAmount = 0;
    }

    /**
     * @return количество всех объектов, хранящихся в коллекции
     */
    public int size() {
        return mapContentsAmount;
    }

    /**
     * @return <code>true</code> если в коллекции нет ни одного объекта
     * */
    public boolean isEmpty() {
        if (mapContentsAmount != 0) {
            return false;
        }
        return true;
    }

    /**
     * Проверяет наличие объекта с заданным ключом в коллекции
     * @param key ключ, наличие которого в коллекции надо проверить
     * @return <code>true</code> если объект с заданным ключом есть в коллекции
     */
    public boolean containsKey(Object key) {
        int index = getIndex((K) key);
        return getNode(getIndex((K)key), key) != null;
    }

    /**
     * Находит и возвращает первую записанную пару значений {@param key, value}
     * (в частности, чтобы убедиться, что такие вообще есть)
     * @param index номер элемента таблицы, где находится (может, по крайней мере) ArrayList с интересующей нас записью
     * @param key ключ интересующей нас записи
     * @return возвращает найденный элемент, если хотя бы один подходящий был найден, в противном случае возвращает <code>null</code>
     */
    final Node<K,V> getNode(int index, Object key) {
        ArrayList<Node<K,V>> tabRaw; K k;
        if(((tabRaw = hashTable[index]) != null)&&(mapContentsAmount != 0)) {
            for(Node<K, V> e : tabRaw) {
                if(safeEquals(e.getKey(),key))
                    return e;
            }
        }
        return null;
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


    /**
     * Возвращает индекс в таблице, соответствующий заданному ключу
     * @param key ключ, индекс которого надо найти
     * @return индекс ключа в таблице
     */
    private int getIndex(K key) {
        return (key == null) ? 0 : abs(key.hashCode() % hashTable.length);
    }
    /*static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }*/

    static private boolean safeEquals(Object o1, Object o2) {
        return o1 == null && o2 == null || o1 != null && o1.equals(o2);
    }
}
