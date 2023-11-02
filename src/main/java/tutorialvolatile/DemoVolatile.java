package tutorialvolatile;

public class DemoVolatile extends Thread {
    private volatile boolean run = true;

    @Override
    public void run() {
        while(run) {
            System.out.println("hello");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void shut() {
        this.run = false;

    }
}
