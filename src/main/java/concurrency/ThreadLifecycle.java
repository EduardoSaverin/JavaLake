package concurrency;

import java.util.concurrent.CountDownLatch;

public class ThreadLifecycle {
    public static void main(String[] args) throws InterruptedException {
        // Demo showing blocked thread
        SampleThread sampleThread = new SampleThread();
        SampleThread sampleThread1 = new SampleThread();
        Thread t1 = new Thread(sampleThread);
        Thread t2 = new Thread(sampleThread1);
        t1.start();
        t2.start();
        Thread.sleep(1000);
        System.out.println(t2.getState());
        System.exit(0);
    }
}

class SampleThread implements Runnable {

    @Override
    public void run() {
        syncBlock();
    }

    public static synchronized void syncBlock() {
        while (true) {
            // Run Infinite
        }
    }
}
