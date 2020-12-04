package ru.spbstu.telematics.java;

import org.apache.commons.collections4.*;
import org.apache.commons.collections4.multiset.HashMultiSet;

import java.util.*;


import static java.lang.Math.abs;

public class MyArrayListValuedHashMap <K,V> implements ListValuedMap<K,V> {

    /**
     * Класс, представляющий элементы коллекции
     * */
    static class Node<K, V> implements Map.Entry<K,V>{

        private final K key;
        private V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public final K getKey() { return key; }

        public V getValue() { return value; }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }
    }//Node class

    /**
     * Класс итератора по коллекции
     * */
    class MyIterator implements MapIterator <K,V> {
        ArrayList<Node<K,V>>[] table;
        int index;
        int indexInArrayList;
        Node<K,V> prev;

        public MyIterator(ArrayList<Node<K,V>>[] t) {
            table = t;
            index = 0;
            indexInArrayList = 0;
            prev = null;
        }

        @Override
        public boolean hasNext() {
            int i = index;
            while (i < table.length && table[i] == null)
                i++;
            return i < table.length;
        }

        @Override
        public K next() {
            Node<K,V> next;
            if(!hasNext())
                return null;
            prev = table[index].get(indexInArrayList);
            if((indexInArrayList + 1) < table[index].size()) {
                next = table[index].get(indexInArrayList + 1);
                indexInArrayList++;
                return next.getKey();
            }
            indexInArrayList = 0;
            while(table[index] == null)
                index++;
            return table[index].get(indexInArrayList).getKey();
        }

        @Override
        public K getKey() {
            return hashTable[index].get(indexInArrayList).getKey();
        }

        @Override
        public V getValue() {
            return hashTable[index].get(indexInArrayList).getValue();
        }

        @Override
        public V setValue(V v) {
            return hashTable[index].get(indexInArrayList).setValue(v);
        }

        /**
         * Заглушка, чтобы компилировалось
         */
        @Override
        public void remove() {
            //Stub
        }
    }//MyIterator Class

    private static final int INIT_MAP_CAPACITY = 16;
    private static final int INIT_LIST_CAPACITY = 3;
    private final ArrayList<Node<K,V>>[] hashTable;
    private final int initialListCapacity;
    private int size;


    public MyArrayListValuedHashMap() {
        this(INIT_MAP_CAPACITY, INIT_LIST_CAPACITY);
    }

    public MyArrayListValuedHashMap(int initialListCapacity) {
        this(INIT_MAP_CAPACITY, initialListCapacity);
    }

    public MyArrayListValuedHashMap(int initialMapCapacity, int initialListCapacity) {
        hashTable = (ArrayList<Node<K,V>>[]) new ArrayList[initialMapCapacity];
        this.initialListCapacity = initialListCapacity;
        size = 0;
    }

    /**
     * @return количество всех объектов, хранящихся в коллекции
     */
    public int size() {
        return size;
    }

    /**
     * @return <code>true</code> если в коллекции нет ни одного объекта
     * */
    public boolean isEmpty() { return size == 0; }

    /**
     * Проверяет наличие объекта с заданным ключом в коллекции
     * @param key ключ, наличие которого в коллекции надо проверить
     * @return <code>true</code> если объект с заданным ключом есть в коллекции
     */
    public boolean containsKey(Object key) {
        int index = getIndex(key);
        if((hashTable[index]) != null) {
            for(Node<K, V> e : hashTable[index]) {
                if(safeEquals(e.getKey(), key))
                    return true;
            }
        }
        return false;
    }

    /**
     * Находит и возвращает первую записанную пару значений {{@param key}, value}
     * @param key ключ интересующей нас записи
     * @return возвращает найденный элемент, если хотя бы один подходящий был найден, в противном случае возвращает <code>null</code>
     */
    final Node<K,V> getNode(K key, V value) {
        int index = getIndex(key);
        ArrayList<Node<K,V>> tabRow;
        if((tabRow = hashTable[index]) != null) {
            for(Node<K, V> e : tabRow) {
                if(safeEquals(e.getKey(), key) &&
                        safeEquals(e.getValue(), value))
                    return e;
            }
        }
        return null;
    }

    /**
     * Проверяет, есть ли в коллекции элемент со значением {@param value}
     * @param value наличие элемента с таким значением надо проверить
     * @return <code>true</code>, если соответствующий элемент есть
     */
    public boolean containsValue(Object value) {
        for (ArrayList<Node<K, V>> nodes : hashTable) {
            if (nodes != null) {
                for (Node<K, V> node : nodes) {
                    if (safeEquals(node.getValue(), value))
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * Проверяет, есть ли в коллекции элемент с заданной парой значений {{@param key} , {@param value}}
     * @param key проверяется наличие элемента с таким ключом
     * @param value проверяется наличие элемента с таким значением
     * @return <code>true</code>, если соответствующий элемент есть в коллекции
     */
    @Override
    public boolean containsMapping(Object key, Object value) {
        return getNode((K) key, (V)value) != null;
    }

    /**
     * Возвращает список элементов с заданным ключом
     * @param key значения с таким ключом будут возвращены
     * @return список значений
     * Если в коллекции не было ни одного значения с заданным ключом, возвращает пустой список
     */
    @Override
    public List<V> get(Object key) {
        int index = getIndex(key);
        List<V> list = new ArrayList<>();
        if (hashTable[index] != null) {
            for (int i = 0; i < hashTable[index].size(); i++) {
                if (safeEquals(hashTable[index].get(i).getKey(), key)) {
                    list.add(hashTable[index].get(i).getValue());
                }
            }
        }
        return list;
    }

    /**
     * Добавляет в коллекцию элемент с парой значений {{@param key},{@param value}}
     * @param key ключ элемента, который надо добавить
     * @param value значение элемента, который надо добавить
     * @return <code>true</code>, если после вызова метода размер коллекции изменился (увеличился)
     */
    @Override
    public boolean put(K key, V value) {
        Node<K, V> e = new Node<>(key, value);
        int index = getIndex(key);
        if (hashTable[index] != null) {
            hashTable[index].add(e);
            size++;
            return true;
        }
        hashTable[index] = new ArrayList<>(initialListCapacity);
        hashTable[index].add(e);
        size++;
        return true;
    }

    /**
     * Добавляет в коллекцию элементы со значениями из {@param values} и ключами {@param k}
     * @param k ключ, с которым будут добавлены элементы
     * @param values значения элементов, которые будут добавлены
     * @return <code>true</code>, если после вызова метода размер коллекции изменился (увеличился)
     */
    @Override
    public boolean putAll(K k, Iterable<? extends V> values) {
        if(values != null) {
            Iterator<V> it = (Iterator<V>) values.iterator();
            while (it.hasNext())
                put(k, it.next());
            return true;
        }
        return false;
    }

    /**
     * Удаляет из коллекции все элемнты с ключом {@param key}
     * @param key элементы с таким ключом надо удалить из коллекции
     * @return список значений удаленных элементов
     * Если в коллекции не было ни одного элемента с заданным ключом, возвращает пустой список
     */
    @Override
    public List<V> remove(Object key) {
        int index = getIndex(key);
        ArrayList<V> list = new ArrayList<>();
        ArrayList<Node<K,V>> toDelete = new ArrayList<>();
        if (hashTable[index] != null) {
            for (Node<K, V> e : hashTable[index]) {
                if (safeEquals(e.getKey(), key)) {
                    list.add(e.value);
                    toDelete.add(e);
                }
            }
        }
        for(int i = 0; i < toDelete.size();i++)
            hashTable[index].remove(toDelete.get(i));
        if(hashTable[index] != null && hashTable[index].size() == 0)
            hashTable[index] = null;
        size -= list.size();
        return list;
    }



    /**
     * Удаляет из коллекции один элемент с ключом {@param key} и значением {@param value}
     * @param key ключ элемента, который будет удален
     * @param value значение элемента, который будет удален
     * @return <code>true</code>, если после вызовва метода размер коллекции изсменился (уменьшился)
     */
    @Override
    public boolean removeMapping(Object key, Object value) {
        Node<K,V> e; int index;
        if((e = getNode((K)key, (V)value)) != null) {
            hashTable[index = getIndex(key)].remove(e);
            size--;
            if(hashTable[index].size() == 0)
                hashTable[index] = null;
            return true;
        }
        return false;
    }

    /**
     * Добавляет в коллекцию все элементы из {@param m}
     * @param m элементы этой коллекции будут добавлены
     * @return <code>true</code> если после вызова метода размер коллекции изменился (увеличился)
     */
    @Override
    public boolean putAll(Map<? extends K, ? extends V> m) {
        if (m != null && m.size() > 0) {
            for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
                put(e.getKey(), e.getValue());
            }
            return true;
        }
        return false;
    }

    /**
     * Добавляет в коллекцию все элементы из {@param multiValuedMap}
     * @param multiValuedMap элементы этой коллекции будут добавлены
     * @return <code>true</code> если после вызова метода размер коллекции изменился (увеличился)
     */
    @Override
    public boolean putAll(MultiValuedMap<? extends K, ? extends V> multiValuedMap) {
        if(multiValuedMap != null && multiValuedMap.size() > 0) {
            for (Map.Entry<? extends K, ? extends V> e : multiValuedMap.entries()) {
                put(e.getKey(), e.getValue());
            }
            return true;
        }
        return false;
    }

    /**
     * Удаляет все элементы из коллекции
     */
    @Override
    public void clear() {
        Arrays.fill(hashTable, null);
        size = 0;
    }

    /**
     * @return список всех элементов коллекции
     */
    @Override
    public Collection<Map.Entry<K, V>> entries() {
        Collection<Map.Entry<K,V>> coll = new ArrayList<>();
        for (ArrayList<Node<K, V>> nodes : hashTable) {
            if (nodes != null) {
                coll.addAll(nodes);
            }
        }
        return coll;
    }

    /**
     * @return MultiSet со всеми ключами коллекции (с дубликатами)
     */
    @Override
    public MultiSet<K> keys() {
        MultiSet<K> set = new HashMultiSet<>();
        for (ArrayList<Node<K, V>> nodes : hashTable) {
            if (nodes != null) {
                for (Node<K, V> node : nodes) {
                    if (node != null) {
                        set.add(node.getKey());
                    }
                }
            }
        }
        return set;
    }

    /**
     * @return Set всех ключей коллекции (без дубликатов)
     */
    @Override
    public Set<K> keySet() {
        return keys().uniqueSet();
    }

    /**
     * @return список занчений всех элементов коллекции (с дубликатами)
     */
    @Override
    public Collection<V> values() {
        Collection<V> coll = new ArrayList<>();
        for (ArrayList<Node<K, V>> nodes : hashTable) {
            if (nodes != null) {
                for (Node<K, V> node : nodes) {
                    if (node != null) {
                        coll.add(node.getValue());
                    }
                }
            }
        }
        return coll;
    }

    /**
     * @return Map, содержащий все кдючи коллекции,
     * каждому ключу сопоставлен список значений, соответствующих этому ключу
     */
    @Override
    public Map<K, Collection<V>> asMap() {
        Map<K, Collection<V>> map = new HashMap<>();
        for (ArrayList<Node<K, V>> nodes : hashTable) {
            if (nodes != null) {
                for (Node<K, V> node : nodes) {
                    if (node != null) {
                        K key;
                        if (!map.containsKey(key = node.getKey())) {
                            Collection<V> interList = new ArrayList<>();
                            interList.add(node.getValue());
                            map.put(key, interList);
                        } else {
                            Collection<V> interList = map.get(key);
                            interList.add(node.getValue());
                        }
                    }
                }
            }
        }
        return map;
    }

    /**
     * @return итератор по коллекции
     */
    @Override
    public MapIterator<K, V> mapIterator() {
        return new MyIterator(hashTable);
    }

    /**
     * Возвращает индекс в таблице, соответствующий заданному ключу
     * @param key ключ, индекс которого надо найти
     * @return индекс ключа в таблице
     */
    private int getIndex(Object key) {
        return (key == null) ? 0 : abs(key.hashCode() % hashTable.length);
    }

    /**
     * Корректно сравнивает два объекта
     * @param o1 первый объект
     * @param o2 второй объект
     * @return <code>true</code>, если значения объектов равны
     */
    static private boolean safeEquals(Object o1, Object o2) {
        return o1 == null && o2 == null || o1 != null && o1.equals(o2);
    }

}
