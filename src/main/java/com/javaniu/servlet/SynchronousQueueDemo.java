package com.javaniu.servlet;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Lzf
 * SynchronousQueue: 同步队列
 * 生产一个，消费一个并立马在生产一个
 */
public class SynchronousQueueDemo {
    public static void main(String[] args) throws Exception{

        SynchronousQueue<Object> queue = new SynchronousQueue<>();
        new Thread(() ->{
            try {
                System.out.println(Thread.currentThread().getName()+"\t put 1");
                queue.put("1");

                System.out.println(Thread.currentThread().getName()+"\t put 2");
                queue.put("2");

                System.out.println(Thread.currentThread().getName()+"\t put 3");
                queue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"AAA").start();

        new Thread(() ->{
            try {
                //暂停一会儿线程
                try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
                System.out.println(Thread.currentThread().getName()+"\t"+queue.take());

                try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
                System.out.println(Thread.currentThread().getName()+"\t"+queue.take());

                try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
                System.out.println(Thread.currentThread().getName()+"\t"+queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"BBB").start();
    }
}
