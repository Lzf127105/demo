package com.javaniu.servlet;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Lzf
 * 读写锁：ReentrantReadWriteLock
 * 目的:解决高并发下，一写多个读的问题，类似读写分离
 * 写入完成，才开始读（写的键值对，不能被打断，不可被加插）
 */
class MyCache{
    private volatile Map<String,Object> map = new HashMap<>();//跟缓存相关的都要加volatile
    //Lock lock  = new ReentrantLock(); //可以替换synchronized的加锁（只有一个线程能进来，如果希望多个的话，就需要用到读写锁）
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();//写的时候只允许一个线程进去，读的时候多个线程进去共享读

    public void put(String key,Object value){
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 正在写入:" +key);
            //暂停一会儿线程,模拟网络拥堵
            try {TimeUnit.MILLISECONDS.sleep(300);} catch (InterruptedException e) {e.printStackTrace();}
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\t 写入完成:" +value);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public void get(String key){
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 正在读取:");
            //暂停一会儿线程,模拟网络拥堵
            try {TimeUnit.MILLISECONDS.sleep(300);} catch (InterruptedException e) {e.printStackTrace();}
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 读取完成:" +result);
        } finally {
            rwLock.readLock().unlock();
        }
    }
}
/**
 * 总结：
 *   读-读能共存
 *   读-写不能共存
 *   写-写不能共存
 *
 *  写操作：原子+独占（整个过程不可分割的，不可被打断)
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        //5个线程写
        for(int i = 1; i <= 5; i++){
            final int temp = i;
            new Thread(() ->{
                myCache.put(temp+"",temp+"");
            },String.valueOf(i)).start();
        }

        //5个线程读
        for(int i = 1; i <= 5; i++){
            final int temp = i;
            new Thread(() ->{
                myCache.get(temp+"");
            },String.valueOf(i)).start();
        }
    }
}
