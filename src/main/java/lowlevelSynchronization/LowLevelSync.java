package lowlevelSynchronization;


import javax.management.ObjectName;
import java.util.LinkedList;
import java.util.Queue;

class Process {
    private Queue<Integer> tasks = new LinkedList<>();
    private int LIMIT = 10;
    private Object lock = new Object();

    public void producer() throws InterruptedException {
        int val = 0;
        while (true) {
            synchronized (lock) {
                while (tasks.size() == LIMIT) lock.wait();
                tasks.add(val++);
                lock.notify();
            }
        }
    }

    public void consume() throws InterruptedException {
        while(true) {
            synchronized (lock) {
                while (tasks.size() == 0) lock.wait();
                int val = tasks.poll();
                System.out.println("Tasks size: " + tasks.size() + "; Value polled: " + val);
                Thread.sleep(100); // processing mocking
                lock.notify();
            }
        }
    }
}
public class LowLevelSync {
    public static void main(String[] args) {
        Process process = new Process();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    process.consume();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    process.producer();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();

    }

}
