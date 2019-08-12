package com.javaniu.servlet;

import java.util.concurrent.CountDownLatch;

/**
 * @author Lzf
 * CountDownLatch: 多线程控制工具类
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws Exception{

        //枚举的应用
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() ->{
                System.out.println(Thread.currentThread().getName()+"国,被灭");
                countDownLatch.countDown();
            },CountryEnum.forEach_CountryEnum(i).getRetMessage()).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t ********秦国统一华夏");

        System.out.println();
        System.out.println(CountryEnum.ONE);//表
        System.out.println(CountryEnum.ONE.getRetCode());//键
        System.out.println(CountryEnum.ONE.getRetMessage());//值
    }


    /**
     * CountDownLatch多线程控制工具类
     * 对于倒计数器，一种典型的场景就是火箭发射。
     * 在火箭发射前，为了保证万无一失，往往还要进行各项设备、仪器的检测。
     * 只有等到所有的检查完毕后，引擎才能点火。那么在检测环节当然是多个检测项可以同时进行的。
     */
    private static void closeDoor() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);//实例化一个倒计数器，count指定计数个数
        for (int i = 1; i <= 6; i++) {
            new Thread(() ->{
                System.out.println(Thread.currentThread().getName()+"\t 上完晚自习，离开教室");
                countDownLatch.countDown();//计数减一
            },String.valueOf(i)).start();
        }
        countDownLatch.await();//api规定，只有减成0,main线程才会执行
        System.out.println(Thread.currentThread().getName()+"\t ********班长最后关门走人");
    }
}
