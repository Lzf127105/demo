package com.javaniu.servlet;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Lzf
 * HashSet 底层HashMap
 * new HashSet<>().add("0"); 只加一个键是因为它的值是一个常量
 * 两种解决线程不安全的问题：
 * 1、Collections.synchronizedSet(new HashSet<>()); 集合工具类有线程安全的方法
 * 2、new CopyOnWriteSet<>(); （写时复制）内部使用volatile
 */
public class SetDemo {
    public static void main(String[] args) {
        Set<String> list = new HashSet<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() ->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
