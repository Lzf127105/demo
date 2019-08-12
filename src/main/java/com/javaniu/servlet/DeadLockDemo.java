package com.javaniu.servlet;

import java.util.concurrent.TimeUnit;

/**
 C:\Users\Lzf\Desktop\demo>jps -l   //命令定位进程号
 23140 com.servlet.DeadLockDemo
 24072 sun.tools.jps.Jps

 C:\Users\Lzf\Desktop\demo>jstack 23140  //找到死锁查看

 Java stack information for the threads listed above:
 ===================================================
 "ThreadBBB":
 at com.servlet.HoldLockThread.run(DeadLockDemo.java:25)
 - waiting to lock <0x000000076b3e54b8> (a java.lang.String)
 - locked <0x000000076b3e54f0> (a java.lang.String)
 at java.lang.Thread.run(Thread.java:748)
 "ThreadAAA":
 at com.servlet.HoldLockThread.run(DeadLockDemo.java:25)
 - waiting to lock <0x000000076b3e54f0> (a java.lang.String)
 - locked <0x000000076b3e54b8> (a java.lang.String)
 at java.lang.Thread.run(Thread.java:748)

 Found 1 deadlock.
 */
class HoldLockThread implements Runnable{
    private String lockA;
    private String lockB;

    public HoldLockThread(String lockA,String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName()+"\t 自己持有:"+lockA+"\t 尝试获取:"+lockB);
            //暂停一会儿线程
            try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}

            synchronized (lockB){
                System.out.println(Thread.currentThread().getName()+"\t 自己持有:"+lockB+"\t 尝试获取:"+lockA);
            }
        }
    }
}

/**
 * 死锁指两个或以上的进程在执行过程中，
 * 因争夺资源而造成一种互相等待的现象
 * 若无外力干涉他们将无法推行下去
 */
public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new HoldLockThread(lockA,lockB),"ThreadAAA").start();
        new Thread(new HoldLockThread(lockB,lockA),"ThreadBBB").start();
    }
}
