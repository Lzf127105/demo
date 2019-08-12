package com.javaniu.servlet;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 概念：同步方法里面再去访问另一个同步方法，获取是的同一把锁
 * 解决方法：synchronized/reentrantLock 可重入锁（也叫递归锁）
 *     方法一：synchronized（原生的）
 *     方法二：reentrantLock
 * 目的：解决死循环
 */
class Phone implements Runnable{

    //===============synchronized实现可重入锁=====================
    public synchronized void sendSms() throws Exception{
        System.out.println(Thread.currentThread().getName()+"\t 发送短信");
        sendEmail();
    }

    public synchronized void sendEmail() throws Exception{
        System.out.println(Thread.currentThread().getName()+"\t 发送邮件");
    }

    //=================reentrantLock实现可重入锁=====================
    Lock lock  = new ReentrantLock(); //可以替换synchronized的加锁（只有一个线程能进来，如果希望多个的话，就需要用到读写锁）

    @Override
    public void run() {
        get();
    }
    public void get(){
        lock.lock();
        lock.lock(); //这里加几把锁，finally下面对应就要解几把锁，不然会程序会卡在这里
        try {
            System.out.println(Thread.currentThread().getName()+"\t get");
            set();
        } finally {
            lock.unlock();
            lock.unlock();
        }
    }
    public void set(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t set");
        } finally {
            lock.unlock();
        }
    }
}

public class ReentrantLockDemo {
    public static void main(String[] args) {
        Phone phone = new Phone();

        //===============synchronized实现可重入锁=====================
        new Thread(() ->{
            try {
                phone.sendSms();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t1").start();

        new Thread(() ->{
            try {
                phone.sendSms();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t2").start();

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        //try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}

        //=================reentrantLock实现可重入锁=====================
        Thread t3 = new Thread(phone,"t3");
        Thread t4 = new Thread(phone,"t4");
        t3.start();
        t4.start();
    }
}
