package ReentrantLocksLearn;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Process {
    private int count = 0;
    private Lock lock = new ReentrantLock();
    private Condition cond = lock.newCondition();

    public void increment() {
        for(int i = 0; i < 10000; i++) count++;
    }

    public void process1 () throws InterruptedException {
        lock.lock(); // similar to sync code blocks
        System.out.println("Locked by process 1");
        System.out.println("Relinquished lock by process 1");

        cond.await();
        System.out.println("Resume by process 1");
        increment();
        lock.unlock();
    }

    public void process2() throws InterruptedException {
        Thread.sleep(1000);
        lock.lock();
        System.out.println("Locked by process 2");
        System.out.println("Press return key.....");
        new Scanner(System.in).nextLine();
        cond.signal();
        System.out.println("notified");
        increment();
        lock.unlock();
        System.out.println("UnLocked by process 2");

    }

    public void finish() {
        System.out.println("Final count is: " + count);
    }
}
public class ReEntrantLocksDemo {
    public static void main(String[] args) throws InterruptedException {
        Process process = new Process();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    process.process1();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    process.process2();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        process.finish();
    }
}
