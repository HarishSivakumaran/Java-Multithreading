package waitnotify;

import java.util.Scanner;

class Process {
    public void producer () throws InterruptedException {
        synchronized (this) {
            System.out.println("Producer started...");
            wait();// this thread just relinquished control over the intrinsic lock
            System.out.println("Resumed producer");
        }
    }

    public void consumer () throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("Consumer started");
        synchronized (this) {
            System.out.println("Consumer got the lock");
            System.out.println("Press return key to continue....");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            System.out.println("Consumer continue");
            notify();
            Thread.sleep(1000);
            System.out.println("Consumer unlocked lock");
        }
    }
}
public class Demo {
    public static void main(String[] args) throws InterruptedException {
        Process process = new Process();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    process.producer();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    process.consumer();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
