package multithread;

import lab_1.Main;
import lab_2.RegularExpressions;
import lab_3.Collection;
import lab_4.VisualInterface;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class OneByOne {

    private static void runThread(Runnable task) {
        Thread thread = new Thread(task);
        System.out.println("THREAD_ID: " + thread.getId() + "\n");
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println();
    }

    public static void main(String[] args) {

        runThread(() -> Main.main(args));
        runThread(() -> RegularExpressions.main(args));
        runThread(() -> Collection.main(args));
        runThread(() -> VisualInterface.main(args));
    }
}