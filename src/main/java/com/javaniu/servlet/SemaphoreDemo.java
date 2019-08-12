package com.javaniu.servlet;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author Lzf
 * Semaphore是synchronized 的加强版，用来控制同时访问某个特定资源的操作数量，或者同时执行某个指定操作的数量。
 *          还可以用来实现某种资源池限制，或者对容器施加边界。
 *  相当于排队吃海底捞：有空位就去抢占，吃完走
 *  有增就有减
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);//模拟3个停车位
        for (int i = 1; i <= 6 ; i++) {//模拟6部汽车
            new Thread(() ->{
                try {
                    semaphore.acquire();//抢占车位
                    System.out.println(Thread.currentThread().getName()+"\t抢到车位");
                    //暂停一会儿线程
                    try {TimeUnit.SECONDS.sleep(3);} catch (InterruptedException e) {e.printStackTrace();}
                    System.out.println(Thread.currentThread().getName()+"\t停车3秒后，离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();//释放停车位
                }
            },String.valueOf(i)).start();
        }
    }
}
