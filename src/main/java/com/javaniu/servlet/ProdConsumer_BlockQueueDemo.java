package com.javaniu.servlet;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Lzf
 * 结合：volatile + AtomicInteger + BlockingQueue
 */
class MyResource{
    private volatile boolean FLAG = true; //默认开启，进行生产+消费
    private AtomicInteger atomicInteger = new AtomicInteger();
    BlockingQueue<String> queue = null;

    public MyResource(BlockingQueue<String> queue){
        this.queue = queue;
        System.out.println(queue.getClass().getName());//反射机制，获取完整的类名和包名
    }

    public void myProd() throws Exception{  //生产
        String data = null;
        boolean retValue;
        while (FLAG){
            data = atomicInteger.incrementAndGet()+""; //原子性的 自增 放入 1 2 3...
            retValue = queue.offer(data,2L, TimeUnit.SECONDS);//2秒插不进队列（说明大于了界值），就返回false
            if (retValue){
                System.out.println(Thread.currentThread().getName()+"\t插入队列"+data+"成功");
            }else {
                System.out.println(Thread.currentThread().getName()+"\t插入队列"+data+"失败");
            }
            //暂停一会儿线程
           TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName()+"\t大老板叫停了,表示 FLAG = false,生产动作结束");
    }

    public void myConsumer() throws Exception{  //消费
        String result = null;
        while (FLAG){
            result = queue.poll(2L, TimeUnit.SECONDS);//2秒插不进队列（说明大于了界值），就返回false
            if (null == result || result.equalsIgnoreCase("")){
                FLAG = false;
                System.out.println(Thread.currentThread().getName()+"\t超过2秒钟,没有取到蛋糕，消费退出");
                System.out.println();
                System.out.println();
                return;
            }
            System.out.println(Thread.currentThread().getName()+"\t消费队列蛋糕"+result+"成功");
        }
    }

    public void stop(){ //大老板叫停
        this.FLAG = false;
    }
}
public class ProdConsumer_BlockQueueDemo {
    public static void main(String[] args) {  //main就是大老板
        MyResource myResource = new MyResource(new ArrayBlockingQueue<String>(10));
        new Thread(() ->{
            System.out.println(Thread.currentThread().getName()+"\t 生产线程启动");
            try {
                myResource.myProd();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"Prod").start();

        new Thread(() ->{
            System.out.println(Thread.currentThread().getName()+"\t 消费线程启动");
            System.out.println();
            System.out.println();
            try {
                myResource.myConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"Consumer").start();

        //暂停一会儿线程
        try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("5秒钟时间到,大老板main线程叫停,活动结束");

        myResource.stop();//volatile修饰的
    }
}
