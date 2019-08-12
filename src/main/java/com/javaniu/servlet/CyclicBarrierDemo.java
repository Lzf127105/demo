package com.javaniu.servlet;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Lzf
 * CyclicBarrier: 减计数方式
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () ->{
                System.out.println("****召唤神龙");
        });
        for (int i = 1; i <= 7; i++) {
            final int temp = i;
            new Thread(() ->{
                System.out.println(Thread.currentThread().getName()+"收集到第:"+temp+"龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}
