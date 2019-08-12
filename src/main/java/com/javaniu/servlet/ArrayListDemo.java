package com.javaniu.servlet;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Lzf
 * 报错类型：java.util.ConcurrentModificationException
 * 导致原因：一个线程正在写入，另一个线程过来抢夺，导致数据不一致，并发修改异常
 * 三种解决线程不安全的问题：new ArrayList<>();
 * 1、new Vector<>(); 线程安全的（synchronized）影响效率
 * 2、Collections.synchronizedList(new ArrayList<>()); 集合工具类有线程安全的方法
 * 3、new CopyOnWriteArrayList<>(); （写时复制）内部使用volatile
 */
public class ArrayListDemo {
    public static void main(String[] args) {
        listNotSafe();
    }

    private static void listNotSafe() {
    /*List<String> list = Arrays.asList("a","b","c");
    list.forEach(System.out::println);*/
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() ->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
