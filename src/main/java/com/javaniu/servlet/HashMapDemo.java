package com.javaniu.servlet;

import com.google.common.collect.Maps;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lzf
 * HashMap 和  ConcurrentHashMap 的不同
 * 两种解决线程不安全的问题：
 * 1、Collections.synchronizedMap(new HashMap<>()); 集合工具类有线程安全的方法
 * 2、ConcurrentHashMap：分段锁技术
 */
public class HashMapDemo {
    public static void main(String[] args) {
        Map<String, String> maps = new ConcurrentHashMap<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                maps.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(maps);
            }, String.valueOf(i)).start();
        }
        Maps.newConcurrentMap();
        //================================================================
        // List<Map>排序(https://www.cnblogs.com/i-tao/archive/2018/05/29/9105163.html)
        List<Map<String, Integer>> listMap = new ArrayList<Map<String, Integer>>();
        Map<String, Integer> map = new HashMap<>();
        map.put("age", 20);
        map.put("sex", 1);
        listMap.add(map);
        Map<String, Integer> map2 = new HashMap<>();
        map2.put("age", 29);
        map2.put("sex", 2);
        listMap.add(map2);
        Map<String, Integer> map3 = new HashMap<>();
        map3.put("age", 35);
        map3.put("sex", 1);
        listMap.add(map3);
        // 按照map值排序
        Collections.sort(listMap, new Comparator<Map<String, Integer>>() {
            @Override
            public int compare(Map<String, Integer> o1, Map<String, Integer> o2) {
                return o2.get("age").compareTo(o1.get("age"));// age升序排序
            }
        });
        // 排序后输出
        for (Map<String, Integer> m : listMap) {
            System.out.println(m);
        }
    }
}
