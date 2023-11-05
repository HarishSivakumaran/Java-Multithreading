package blockingqueue;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

class Server {
    public BlockingQueue<Integer> blockingDeque = new ArrayBlockingQueue<>(10);

    public void incomingRequest() {
        Random random = new Random(10);
        while(true) {
            blockingDeque.add(random.nextInt());
            if(blockingDeque.size() == 10) blockingDeque.poll();
        }
    }
    public void process() throws InterruptedException {
        while(blockingDeque.size() > 0) {
            int val = blockingDeque.poll();
            Thread.sleep(100);
            System.out.println("Processed task: " + val + "; Queue size: " + blockingDeque.size());
        }

    }
}
public class App {
    public static void main(String[] args) throws InterruptedException {

        Server server = new Server();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                server.incomingRequest();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server.process();
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
