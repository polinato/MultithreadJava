package multithread;

import lab_3.Collection;
import java.util.concurrent.Exchanger;


class sumArray implements Runnable{

    private final Exchanger<String> exchanger;
    private final int arraySize;
    private final int id;

    sumArray(int id, Exchanger<String> exchanger, int arraySize) {
        this.exchanger = exchanger;
        this.arraySize = arraySize;
        this.id = id;
    }

    @Override
    public void run() {

        try {
            String message = Integer.toString(Collection.countSumCouple(Collection.generateRandomArray(arraySize)));
            System.out.println("sumArray " + id + " sends: " + message);
            String receivedMessage = exchanger.exchange(message);
            System.out.println("sumArray " + id + " received: " + receivedMessage);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
public class ExchangerUsage {

    public static void main(String[] args) {

        Exchanger<String> exchanger = new Exchanger<>();

        for(int i = 0; i < 2; i++) {
            new Thread(new sumArray(i, exchanger, 10)).start();
        }
    }
}
