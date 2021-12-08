package concurrency;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * Print of null value confirms that same instance will have different ThreadLocal Variable.
 */
public class ThreadLocalExample{
    public static void main(String[] args) {
        ThreadLocalContextAware threadLocalContextAware = new ThreadLocalContextAware("First");
        IntStream.of(0,5).forEach(i -> new Thread(threadLocalContextAware).start());
    }
}

class ThreadLocalContextAware implements Runnable {

    private final static ThreadLocal<Context> threadLocal = new ThreadLocal<>();
    private final String threadName;

    public ThreadLocalContextAware(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void run() {
        System.out.println("Before Setting Context -> " + threadLocal.get());
        threadLocal.set(new Context(threadName + new Random().nextLong()));
        System.out.println("After Setting Context -> " + threadLocal.get().getName());
    }

    public static ThreadLocal<Context> getThreadLocal() {
        return threadLocal;
    }
}

class Context {
    private final String name;

    public Context(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
