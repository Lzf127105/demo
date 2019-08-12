package com.javaniu.servlet;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * synchronized 和 Lock的区别
 *
 * 1、sync是关键字，lock是具体类
 * 2、sync不需要手动释放锁，lock必须手动释放锁
 * 3、等待是否可中断：sync不可中断，lock可中断
 * 4、加锁是否公平： sync非公平锁，lock 两者都可（默认是非公平锁 false）
 * 5、锁绑定多个条件condition：
 *     sync没有（要么随机唤醒，要么全部唤醒）
 *     lock可精确唤醒
 */
class ShareResource{
    private int number = 1;//A:1  B:2  C:3
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition(); //唤醒条件
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5() throws Exception{
        lock.lock();
        try {
            //1、判断
            while (number != 1){ //第一次0 != 0 ，返回false
                //等待，不能生产
                c1.await();
            }
            //2、干活
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //3、只通知唤醒 2号线程
            number = 2;
            c2.signal();
        } finally {
            lock.unlock();
        }
    }

    public void print10() throws Exception{
        lock.lock();
        try {
            //1、判断
            while (number != 2){ //第一次1 != 1 ，返回false
                //等待，不能生产
                c1.await();
            }
            //2、干活
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //3、只通知唤醒 3号线程
            number = 3;
            c3.signal();
        } finally {
            lock.unlock();
        }
    }

    public void print15() throws Exception{
        lock.lock();
        try {
            //1、判断
            while (number != 3){ //第一次0 != 0 ，返回false
                //等待，不能生产
                c2.await();
            }
            //2、干活
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //3、只通知唤醒 1号线程 循环了;
            number = 1;
            c1.signal();
        } finally {
            lock.unlock();
        }
    }
}
public class Synchronized和Lock的区别 {
    /*
     *  lock的好处？
     *  1、可精确唤醒
     *  题目：多线程之间按顺序调用，实现A->B->C三个线程启动，要求如下：
     *  AA打印5次，BB打印10次，CC打印15次...来10轮
     */
    public static void main(String[] args) throws Exception {
        ShareResource shareResource = new ShareResource();
        for (int i = 1; i <= 10; i++) {
            new Thread(() ->{
                try {
                    shareResource.print5();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },"AA").start();
        }

        for (int i = 1; i <= 10; i++) {
            new Thread(() ->{
                try {
                    shareResource.print10();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },"BB").start();
        }

        for (int i = 1; i <= 10; i++) {
            new Thread(() ->{
                try {
                    shareResource.print15();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },"CC").start();
        }
    }
}
