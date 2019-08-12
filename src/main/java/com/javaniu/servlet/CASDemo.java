package com.javaniu.servlet;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Lzf
 * 参考：https://www.jianshu.com/p/21be831e851e
 * AtomicInteger可以解决i++在多线下的不安全问题，实现底层原理是什么？CAS算法
 * CAS 比较并交换
 * 比较当前内存的值和主内存的值，如果相同则执行规定操作，否则继续比较，直到主内存的值和工作内存的值一致为止
 * 对CAS的理解，CAS是一种无锁算法，
 * CAS有3个操作数，内存值V，旧的预期值A，要修改的新值B。当且仅当预期值A和内存值V相同时，将内存值V修改为B，否则什么都不做。
 */
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);//快照值，从主内存复制过来的值

        //5表示主内存的值，在我写回主内存的时候，如果主内存的值还是5，就会返回true并将主内存的值修改为2019
        System.out.println(atomicInteger.compareAndSet(5,2019)+"\t 当前值为:"+atomicInteger.get());

        //因为上面将主内存的值改成了2019，这个时候主内存的值不是5，就会返回false并将主内存的值修改为2019
        System.out.println(atomicInteger.compareAndSet(5,2018)+"\t 当前值为:"+atomicInteger.get());
    }
}
