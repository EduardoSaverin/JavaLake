package concurrency;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class MutexExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SequenceGenerator sequenceGenerator = new SequenceGenerator();
        Set<Integer> unique = getUniqueSequence(sequenceGenerator, 1000); // Without mutex we get incorrect count in 1 out of 5 run
        System.out.println(unique.size());
        assert unique.size() == 1000;
        System.out.println("Done");
    }

    private static Set<Integer> getUniqueSequence(SequenceGenerator sequenceGenerator, int count) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        List<Future<Integer>> futureList = new ArrayList<>();
        Set<Integer> uniqueSet = new HashSet<>();
        IntStream.range(0, count).forEach($ -> futureList.add(threadPoolExecutor.submit(sequenceGenerator::getNextSequence)));
        for (Future<Integer> future : futureList) {
            uniqueSet.add(future.get());
        }
        threadPoolExecutor.awaitTermination(1, TimeUnit.SECONDS);
        threadPoolExecutor.shutdown();
        return uniqueSet;
    }
}

class SequenceGenerator {
    private int counter = 0;
    private final Object mutex = new Object();
    private final ReentrantLock reentrantLock = new ReentrantLock();
    private Semaphore semaphore = new Semaphore(1); // number of threads to be allowed to access critical section

    public int getNextSequence() {
        synchronized (mutex) {
            counter += 1;
            return counter;
        }
    }

    public int getNextSequenceViaLock() {
        try {
            reentrantLock.lock();
            return getNextSequence();
        } finally {
            reentrantLock.unlock();
        }
    }

    public int getNextSequenceViaSemaphore() {
        try {
            semaphore.acquire();
            return getNextSequence();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
        return 0;
    }
}
