package deadlocks;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Account {
    private int balance = 100000;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void transfer(Account a, Account b, int amount) {
        a.setBalance(a.getBalance()-amount);
        b.setBalance(b.getBalance()+amount);
    }
}

class DeadlockExample {
    Account a = new Account(), b = new Account();
    ReentrantLock aLock = new ReentrantLock(), bLock = new ReentrantLock();
    public void process1() {
        Random random = new Random();
        aLock.lock();
        bLock.lock();
        for(int i = 0; i < 1000; i++) {
            a.transfer(a, b, random.nextInt(100));
        }
        aLock.unlock();
        bLock.unlock();
    }

    public void process2() {
        Random random = new Random();
        bLock.lock();
        aLock.lock();

        for(int i = 0; i < 1000; i++) {
            b.transfer(b, a, random.nextInt(100));
        }
        aLock.unlock();
        bLock.unlock();
    }
}

class DeadlockFixExample {
    //either always lock in the same order in both process or use trylock
    Account a = new Account(), b = new Account();
    Lock aLock = new ReentrantLock(), bLock = new ReentrantLock();

    private void acquireLock(Lock a, Lock b) throws InterruptedException {
        boolean gotLockA = false, gotLockB = false;
        while(true) {
            try {
                gotLockB = b.tryLock();
                gotLockA = a.tryLock();
            } finally {
                if (gotLockA && gotLockB) return;
                if (gotLockA) a.unlock();
                if (gotLockB) b.unlock();
                Thread.sleep(100);
            }
        }
    }
    public void process1() throws InterruptedException {
        Random random = new Random();
        acquireLock(aLock, bLock);
        for(int i = 0; i < 1000; i++) {
            a.transfer(a, b, random.nextInt(100));
        }
        aLock.unlock();
        bLock.unlock();
    }

    public void process2() throws InterruptedException {
        Random random = new Random();
        acquireLock(bLock, aLock);

        for(int i = 0; i < 1000; i++) {
            b.transfer(b, a, random.nextInt(100));
        }
        aLock.unlock();
        bLock.unlock();
    }
}
public class DeadlocksLearn {
    public static void main(String[] args) throws InterruptedException {
//        DeadlockExample deadlockExample = new DeadlockExample();
        DeadlockFixExample deadlockExample = new DeadlockFixExample();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    deadlockExample.process1();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    deadlockExample.process2();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("a: " + deadlockExample.a.getBalance());
        System.out.println("b: " + deadlockExample.b.getBalance());
        System.out.println("total: " + (deadlockExample.b.getBalance() + deadlockExample.a.getBalance()));

    }
}
