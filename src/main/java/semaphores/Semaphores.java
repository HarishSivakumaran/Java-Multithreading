package semaphores;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

class Connector{
    private static final Connector instance = new Connector();
    private static Semaphore semaphore = new Semaphore(10, true);
    private int connections = 0;

    private Connector() {}

    public static Connector getInstance() {
        return instance;
    }

    public void connect() throws InterruptedException {
        semaphore.acquire();
        try {
            doConnect();
        } finally {
            semaphore.release();
        }
    }

    private void doConnect() throws InterruptedException {
        synchronized (this) {
            connections++;
            System.out.println("No of connections : " + connections);
        }

        Thread.sleep(2000);

        synchronized (this) {
            connections--;
        }
    }

}

public class Semaphores {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i = 0; i < 200; i++) {
            executorService.submit(new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Connector.getInstance().connect();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }));
        }

        executorService.shutdown();

    }
}
