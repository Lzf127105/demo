package com.javaniu.servlet;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Lzf
 * 传统版的生产者和消费者模式
 * sync 被 lock 替代
 * wait 被 await 替代
 * notify 被 signalAll 替代
 *
 * API 规定 只能用 while ，用if 可能出现多个
 */
//资源类
class ShareData{
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition(); //唤醒条件

    public void increment() throws InterruptedException {
        lock.lock();
        try {
            //1、判断
            while (number != 0){ //第一次0 != 0 ，返回false
                //等待，不能生产
                condition.await();
            }
            //2、干活
            number++;
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            //3、通知唤醒
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() throws InterruptedException {
        lock.lock();
        try {
            //1、判断
            while (number == 0){ //第一次0 != 0 ，返回false
                //等待，不能生产
                condition.await();
            }
            //2、干活
            number--;
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            //3、通知唤醒
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

/**
 * 题目：一个初始值0的变量，两个线程对其进行交替操作，一个加1，一个减1，循环5轮
 * 1、线程    操作（方法） 资源类
 * 2、判断    干活         通知
 * 3、防止线程虚假唤醒机制
 */
public class ProdConsumer_TraditionDemo {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        new Thread(() ->{
            for (int i = 1; i <= 5; i++) {
                try {
                    shareData.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"AA").start();

        new Thread(() ->{
            for (int i = 1; i <= 5; i++) {
                try {
                    shareData.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"BB").start();
    }
}
