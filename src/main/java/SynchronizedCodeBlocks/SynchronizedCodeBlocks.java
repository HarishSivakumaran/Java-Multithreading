package SynchronizedCodeBlocks;

import com.sun.jdi.ThreadReference;
import synchronizedMethodblocks.SynchronizedBlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class SynchronizedCodeBlocks {
    List<Integer> l1 = new ArrayList<>();
    List<Integer> l2 = new ArrayList<>();

    Object lock1 = new Object();
    Object lock2 = new Object();

    Random random = new Random();

    public void stage1 () throws InterruptedException {
        synchronized (lock1) {
            System.out.println("stage1 " +Thread.currentThread().getName());
            Thread.sleep(1);
            l1.add(random.nextInt(100));
        }
    }

    public void stage2 () throws InterruptedException {
        synchronized (lock2) {
            System.out.println("stage2 " + Thread.currentThread().getName());
            Thread.sleep(1);
            l2.add(random.nextInt(100));
        }
    }

    public void process() throws InterruptedException {
        for(int i = 0; i < 1000; i++) {
            this.stage1();
            this.stage2();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        SynchronizedCodeBlocks synchronizedCodeBlocks = new SynchronizedCodeBlocks();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronizedCodeBlocks.process();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronizedCodeBlocks.process();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println(System.currentTimeMillis()-start);
        System.out.println("list1: " + synchronizedCodeBlocks.l1.size());
        System.out.println("list1: " + synchronizedCodeBlocks.l2.size());

    }
}
