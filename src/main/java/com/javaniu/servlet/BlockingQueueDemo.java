package com.javaniu.servlet;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Lzf
 * 在多线程领域： 所谓阻塞，在某些情况下回挂起线程（即阻塞），一旦条件满足，被挂起的线程有会被自动唤醒
 * MQ核心底层原理就是这个,也是线程池的底层*********
 * BlockingQueue: 阻塞队列
 * 好处：我们不要关心什么时候需要阻塞线程（wait），什么时候需要唤醒线程（notify），因为这一切都给BlockingQueue办好了
 *      空了消费者队列阻塞
 *      满了生产者队列阻塞
 */
@SuppressWarnings("all")
public class BlockingQueueDemo {
    public static void main(String[] args) throws Exception{
        /**
         * ArrayBlockingQueue主要方法：
         * 超时：
         * offer(e,time,unit);插入
         * poll(time,unit);移除
         */
        BlockingQueue<String> queue =  new ArrayBlockingQueue<String>(3);//3表示队列大小（有界）
        System.out.println(queue.offer("a",2L, TimeUnit.SECONDS));
        System.out.println(queue.offer("b",2L, TimeUnit.SECONDS));
        System.out.println(queue.offer("c",2L, TimeUnit.SECONDS));
        //System.out.println(queue.offer("x",2L, TimeUnit.SECONDS));//大于界值3，阻塞2秒，然后才提示插入失败，返回false
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());//没有元素可移除，一直阻塞中

    }

    private static void put() throws InterruptedException {
        /**
         * ArrayBlockingQueue主要方法：
         * 阻塞：put();插入  take();移除
         */
        BlockingQueue<String> queue =  new ArrayBlockingQueue<String>(3);//3表示队列大小（有界）
        queue.put("a");
        queue.put("b");
        queue.put("c");
        //queue.put("x");//大于3会一直阻塞在这里，除非改成4
        System.out.println("===================");
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
    }

    private static void offer() {
        /**
         * ArrayBlockingQueue主要方法：
         * 特殊值：offer();插入  poll();移除  peek();检查
         * offer()插入成功返回true，失败返回false
         * poll()移除成功返回队列的元素，队列没有元素就返回null
         */
        BlockingQueue<String> queue =  new ArrayBlockingQueue<String>(3);//3表示队列大小（有界）
        System.out.println(queue.offer("a"));
        System.out.println(queue.offer("b"));
        System.out.println(queue.offer("c"));
        System.out.println(queue.offer("x"));//大于界值3，插入失败，返回false
        System.out.println("排第一个的元素是："+queue.peek());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }

    private static void exception() {
        /**
         * ArrayBlockingQueue主要方法：
         * 抛异常：add(); remove(); element();检查
         * add()元素大于3时，会报Queue full 异常，表示队列已满
         * remove()大于3时，会报NoSuchElementException，表示满意这个元素
         * 先进先出
         */
        BlockingQueue<String> queue =  new ArrayBlockingQueue<String>(3);//3表示队列大小（有界）
        System.out.println(queue.add("a"));
        System.out.println(queue.add("b"));
        System.out.println(queue.add("c"));
        System.out.println("排第一个的元素是："+queue.element());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
    }
}
