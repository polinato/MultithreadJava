package multithread;

import lab_3.Collection;
import lab_4.VisualInterface;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class LockUsage {
    private final Object lock = new Object();

    public void startCollection() {
        System.out.println("THREAD_ID: " + Thread.currentThread().getId() + "\n");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter array: ");

        try {
            String str = reader.readLine();

            String[] numbersArray = str.split(",");
            int[] numbers = new int[numbersArray.length];
            for (int i = 0; i < numbersArray.length; i++) {
                numbers[i] = Integer.parseInt(numbersArray[i].trim());
            }

            synchronized (lock) {
                lock.notify();
                lock.wait();
            }

            System.out.println("THREAD_ID: " + Thread.currentThread().getId() + "\n");
            System.out.println("Result: " + Collection.countSumCouple(numbers));

        }
        catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(0);
        }

        synchronized (lock) {
            lock.notify();
        }
    }

    public void startVisualInterface(String[] args) {

        System.out.println("\nTHREAD_ID: " + Thread.currentThread().getId() + "\n");

        VisualInterface.main(args);

        synchronized (lock) {
            lock.notify();
        }
    }

    public void startMyServlet() {

        System.out.println("\nTHREAD_ID: " + Thread.currentThread().getId());

        try {
            Desktop.getDesktop().browse((new URL("http://localhost:8080/Servlet/Sort")).toURI());
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(0);
        }
    }


    public static void main(String[] args) {

        System.out.println("THREAD_ID: " + Thread.currentThread().getId() + "\n");

        LockUsage lockUsage = new LockUsage();

        Thread collectionThread = new Thread(lockUsage::startCollection);
        collectionThread.start();

        Thread visualInterfaceThread = new Thread(() -> lockUsage.startVisualInterface(args));

        try {
            synchronized (lockUsage.lock) {
                lockUsage.lock.wait();
            }
        }
        catch (InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(0);
        }

        visualInterfaceThread.start();

        Thread servletThread = new Thread(lockUsage::startMyServlet);


        try {
            synchronized (lockUsage.lock) {
                lockUsage.lock.wait();
            }
        }
        catch (InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(0);
        }

        servletThread.start();
    }
}
