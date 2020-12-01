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
        ArrayListValuedHashMap<String,String> alvhm = new ArrayListValuedHashMap<String,String>();
        alvhm.put("First","Hello World!");
        alvhm.put("Second","Bye World!");
        alvhm.put("Second","How the hell it works???");
        alvhm.put("Second", "shiiiiiit....");
        List<String> flag = alvhm.get("Second");
    }
}
