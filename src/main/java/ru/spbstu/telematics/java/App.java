package ru.spbstu.telematics.java;

import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        ArrayListValuedHashMap<Integer,String> alvhm = new ArrayListValuedHashMap<Integer,String>();
        alvhm.put(30,"Hello World!");
        alvhm.put(30,"Bye World!");
        alvhm.put(46,"How the hell it works???");
        List<String> list = alvhm.get(30);
    }
}
