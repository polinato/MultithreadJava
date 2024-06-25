package multithread;

import lab_3.Collection;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

class MyDate implements Runnable {

    private final CountDownLatch latch;
    private final String name;

    MyDate(CountDownLatch latch, String name) {
        this.latch = latch;
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(name + " is starting work...");
        (new CountDownLatchUsage()).startCollection();
        System.out.println(name + " has finished work.");
        latch.countDown();
    }
}

public class CountDownLatchUsage {

    public void startCollection() {

        int [] array = Collection.generateRandomArray(10);
        System.out.print(Thread.currentThread().getName() + " -> Enter array: " + Arrays.toString(array) + "\n");
        System.out.println(Thread.currentThread().getName() + " -> Result: " + Collection.countSumCouple(array));
    }
    public static final int COUNT = 3;

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(COUNT);

        for (int i = 0; i < COUNT; i++) {
            new Thread(new MyDate(latch, "Thread " + i)).start();
        }
        latch.await();
    }
}
