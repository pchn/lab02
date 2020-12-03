package ru.spbstu.telematics.java;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.*;

/**
 * Unit test for MyArrayListValuedHashMap.
 */
public class MyArrayListValuedHashMapTest
    extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MyArrayListValuedHashMapTest(String testName) {
        super(testName);
    }

    public static void main(String[] args) {
        TestCase test = new MyArrayListValuedHashMapTest("MyArrayListValuedHashMap test");
        test.run();
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(MyArrayListValuedHashMapTest.class);
    }

    public void runTest() {
        testMyArrayListValuedHashMap();
    }
    private <K,V> boolean equals (MultiValuedMap<K,V> list1, MultiValuedMap<K,V> list2) {
        if(list1 == null || list2 == null)
            return list1 == list2;
        if(list1.size() != list2.size())
            return false;
        for(Map.Entry<K,V> e : list1.entries()) {
            if (!list2.containsMapping(e.getKey(), e.getValue()))
                return false;
        }
        for(Map.Entry<K,V> e : list2.entries()) {
            if (!list1.containsMapping(e.getKey(), e.getValue()))
                return false;
        }
        return true;
    }

    private <V> boolean equals(Collection<V> coll1, Collection<V> coll2) {
        if (coll1 == null || coll2 == null)
            return coll1 == coll2;
        if (coll1.size() != coll2.size())
            return false;
        for(V e : coll1) {
            if(!coll2.contains(e))
                return false;
        }
        for(V e : coll2) {
            if(!coll1.contains(e))
                return false;
        }
        return true;
    }

    private <K, V> boolean equals(Map<K, Collection<V>> map1, Map<K, Collection<V>> map2) {
        if(map1 == null || map2 == null)
            return map1 == map2;
        if(map1.size() != map2.size()) {
            return false;
        }
        for(K key : map1.keySet()) {
            if (!equals(map1.get(key), map2.get(key)))
                return false;
        }
        for(K key : map2.keySet()) {
            if (!equals(map2.get(key), map1.get(key)))
                return false;
        }
        return true;
    }

    private <K,V> void testContainsKey(MultiValuedMap<K,V> list1, MultiValuedMap<K,V> list2, K key) {
        assertEquals(list1.containsKey(key), list2.containsKey(key));
    }

    private <K,V> void testContainsValue(MultiValuedMap<K,V> list1, MultiValuedMap<K,V> list2, V value) {
        assertEquals(list1.containsValue(value), list2.containsValue(value));
    }

    private <K,V> void testContainsMapping(MultiValuedMap<K,V> list1, MultiValuedMap<K,V> list2, K key, V value) {
        assertEquals(list1.containsMapping(key, value), list2.containsMapping(key, value));
    }
    private <K,V> void testGet(MultiValuedMap<K,V> list1, MultiValuedMap<K,V> list2, K key) {
        assertEquals(list1.get(key), list2.get(key));
    }
    private <K,V> void testPut(MultiValuedMap<K,V> list1, MultiValuedMap<K,V> list2, K key, V value) {
        assertEquals(list1.put(key, value), list2.put(key, value));
        assertTrue(equals(list1, list2));
    }
    private <K,V> void testAll(MultiValuedMap<K,V> list1, MultiValuedMap<K,V> list2,
                                  K key, Iterable<? extends V> values) {
        assertEquals(list1.putAll(key, values), list2.putAll(key, values));
        assertTrue(equals(list1,list2));
    }
    private <K,V> void testRemove(MultiValuedMap<K,V> list1, MultiValuedMap<K,V> list2, K key) {
        assertEquals(list1.remove(key), list2.remove(key));
        assertTrue(equals(list1,list2));
    }
    private <K,V> void testRemoveMapping(MultiValuedMap<K,V> list1, MultiValuedMap<K,V> list2, K key, V value) {
        assertEquals(list1.removeMapping(key, value), list2.removeMapping(key, value));
        assertTrue(equals(list1, list2));
    }
    private <K,V> void testPutAll(MultiValuedMap<K,V> list1, MultiValuedMap<K,V> list2,
                                  Map<? extends K, ? extends V> map) {
        assertEquals(list1.putAll(map), list2.putAll(map));
        assertTrue(equals(list1, list2));
    }
    private <K,V> void testPutAll(MultiValuedMap<K,V> list1, MultiValuedMap<K,V> list2,
                                        MultiValuedMap<? extends K, ? extends V> map) {
        assertEquals(list1.putAll(map), list2.putAll(map));
        assertTrue(equals(list1,list2));
    }
    private <K,V> void testClear(MultiValuedMap<K,V> list1, MultiValuedMap<K,V> list2) {
        list1.clear();
        list2.clear();
        assertTrue(equals(list1, list2));
    }
    private <K,V> void testKeys(MultiValuedMap<K,V> list1, MultiValuedMap<K,V> list2) {
        assertTrue(equals(list1.keys(), list2.keys()));
    }
    private <K,V> void testKeySet(MultiValuedMap<K,V> list1, MultiValuedMap<K,V> list2) {
        assertTrue(equals(list1.keySet(), list2.keySet()));
    }
    private <K,V> void testValues(MultiValuedMap<K,V> list1, MultiValuedMap<K,V> list2){
        assertTrue(equals(list1.values(), list2.values()));
    }
    private <K,V> void testAsMap(MultiValuedMap<K,V> list1, MultiValuedMap<K,V> list2) {
        assertTrue(equals(list1.asMap(), list2.asMap()));
    }

    public void testMyArrayListValuedHashMap (){
        ArrayListValuedHashMap<Integer,Integer> libraryList = new ArrayListValuedHashMap<>();
        MyArrayListValuedHashMap<Integer, Integer> myList = new MyArrayListValuedHashMap<>();

        testPut(libraryList,myList, 1,1);

        Map<Integer,Integer> map = new HashMap<>();
        map.put(2,2);
        map.put(null,null);//проверка с null
        testPutAll(libraryList, myList, map);

        ArrayListValuedHashMap<Integer,Integer> testList = new ArrayListValuedHashMap<>();
        testList.put(3,1);
        testList.put(3,2);
        testList.put(3,3);//с одинаковым ключом
        testList.put(null,null);
        testList.put(2,2);//уже существующие
        testList.put(4,1);//просто так

        testPutAll(libraryList, myList, testList);

        testKeys(libraryList, myList);

        testKeySet(libraryList, myList);

        testValues(libraryList, myList);

        testAsMap(libraryList, myList);

        testRemove(libraryList, myList, 3);//одинаковые ключи
        testRemove(libraryList, myList, null);//null и одинаковые ключи
        testRemove(libraryList, myList,-100);//не существует

        libraryList.put(null, null);
        libraryList.put(null, null);
        myList.put(null, null);
        myList.put(null, null);

        testRemoveMapping(libraryList, myList, null, null);//null и одинаковые ключи
        testRemoveMapping(libraryList, myList, 0,0);//не сущесвует
        testRemoveMapping(libraryList, myList, 2,2);//простая проверка

        List<Integer> testList2 = new ArrayList<>();
        testList2.add(null);
        testList2.add(1);
        testList2.add(2);
        testAll(libraryList, myList,3 ,testList2);
        testAll(libraryList, myList, null, testList2);

        testGet(libraryList, myList, null);
        testGet(libraryList, myList, 3);

        testContainsKey(libraryList, myList, 100);
        testContainsKey(libraryList, myList, null);
        testContainsKey(libraryList, myList, 3);

        testContainsValue(libraryList, myList, null);
        testContainsValue(libraryList, myList, 100);
        testContainsValue(libraryList, myList, 2);

        testContainsMapping(libraryList, myList, null, null);
        testContainsMapping(libraryList, myList, 100, 100);
        testContainsMapping(libraryList, myList, 3, 2);

        testClear(libraryList, myList);

        testKeys(libraryList, myList);

        testKeySet(libraryList, myList);

        testValues(libraryList, myList);

        testAsMap(libraryList, myList);

        testRemove(libraryList, myList, 3);

        testRemoveMapping(libraryList, myList, null, null);

        testGet(libraryList, myList, null);

        testContainsKey(libraryList, myList, null);

        testContainsValue(libraryList, myList, null);

        testContainsMapping(libraryList, myList, null, null);

        testClear(libraryList, myList);
    }
}
