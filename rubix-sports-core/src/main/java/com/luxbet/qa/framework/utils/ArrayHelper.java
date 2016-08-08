package com.luxbet.qa.framework.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by monika on 22/12/2015.
 * This class wil contain all array related function,
 * different operations on an array, hashmap, list etc
 *
 */
public class ArrayHelper {

    /**
     *
     * @param keys List<String>
     * @param values List<String>
     * @return HashMap<String, String>
     */
    public static HashMap<String, String> map(List<String> keys, List<String> values) {
        HashMap<String, String> map = new HashMap<String, String>();
        Iterator<String> keyIt = keys.iterator();
        Iterator<String> valueIt = values.iterator();
        while (keyIt.hasNext() && valueIt.hasNext()) {
            String k = keyIt.next();
            if (null != map.put(k, valueIt.next())){
                throw new IllegalArgumentException("Keys are not unique! Key " + k + " found more then once.");
            }
        }
        if (keyIt.hasNext() || valueIt.hasNext()) {
            throw new IllegalArgumentException("Keys and values collections have not the same size");
        }
        return map;
    }

    public static List trimToSize(List arrayList, int to) {
        arrayList = arrayList.subList(0, to);
        return arrayList;

    }

}
