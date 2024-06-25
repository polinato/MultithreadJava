package other;

import java.io.IOException;
import java.net.Socket;

public class ClientsCommunication {

    public static void main(String[] args) {

        // Створюємо та запускаємо сервер у власному потоці
        Thread serverThread = new Thread(() -> {
            try {
                Server.main(args);
                System.out.println("Server has launched");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        serverThread.start();

        try {
            Socket socket = new Socket("localhost", 1234);

            Thread clientThread = new Thread(() -> {
                try {
                    Client.startClient(socket, "client 1");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            clientThread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

