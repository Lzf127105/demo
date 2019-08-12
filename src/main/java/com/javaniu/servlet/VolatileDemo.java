package com.javaniu.servlet;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  TimeUnit.DAYS          //天
 *  TimeUnit.HOURS         //小时
 *  TimeUnit.MINUTES       //分钟
 *  TimeUnit.SECONDS       //秒
 *  TimeUnit.MILLISECONDS  //毫秒
 * volatile Java虚拟机的轻量级的同步机制
 * 特性一：保证可见性，是指线程之间的可见性，一个线程修改的状态对另一个线程是可见的。
 *          也就是一个线程修改的结果。另一个线程马上就能看到。
 * 特性二：不保证原子性，并发情况下由于速度太快，线程之间来不见通知我已经修改了，多个线程写回主内存值得时候，导致主内存的值有可能被修改了，但是其他线程也正在修改。
 *                      原本是(1表示主内存的值) 线程A: 1->2,线程B: 2->3 从现在出现 线程A: 1->2,线程B: 1->3
 * 特性三：禁止指令重排
 */
class myData {

    volatile int count = 0;

    public void add (){
        this.count = 60;
    }

    public void addPlus() {
        count++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();

    public void addAtomic(){
        atomicInteger.getAndIncrement(); //count++;效果一样，只是增加了原子性
    }
}

public class VolatileDemo {
    public static void main(String[] args) {
        addAtomic();
    }

    // 保证原子性。即原本20000
    // AtomicInteger可以解决i++在多线下不安全问题，底层原理是什么？CAS算法
    private static void addAtomic() {
        myData myData = new myData();
        for(int i = 1; i <= 20; i++){
            new Thread(() ->{
                for (int j = 1; j <= 1000; j++) {
                    myData.addAtomic();
                }
            },String.valueOf(i)).start();
        }
        while (Thread.activeCount() > 2){
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() + "最终值:"+myData.atomicInteger);
    }

    //不保证原子性。即原本20000,但是最终结果达不到20000
    private static void addPlus() {
        myData myData = new myData();
        for(int i = 1; i <= 20; i++){
            new Thread(() ->{
                for (int j = 1; j <= 1000; j++) {
                    myData.addPlus();
                }
            },String.valueOf(i)).start();
        }
        //等待上面20个线程计算完，在用main线程获取最终值
        while (Thread.activeCount() > 2){ //后台默认2个线程，一个是gc线程，另一个是主线程
            Thread.yield(); //暂停当前正在执行的线程对象。(等待20个线程执行完，才执行主（main）线程)
        }
        System.out.println(Thread.currentThread().getName() + "最终值:"+myData.count);
    }

    //volatile可见性。一个线程修改的结果。另一个线程马上就能看到
    private static void seeByVolatile() {
        myData myData = new myData();
        new Thread(() ->{
            System.out.println(Thread.currentThread().getName() + "进入");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.add();
            System.out.println(Thread.currentThread().getName() + "修改为"+myData.count);
        },"t1").start();

        while (myData.count == 0){
            //如果count等于0，死循环
            System.out.println("前面t1线程已经修改为60，main线程还不知道，还是认为是0,没通知");
        }
        System.out.println(Thread.currentThread().getName()+"线程任务完成");
    }
}
