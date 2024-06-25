/*
* Чат. Сервер розсилає всім клієнтам інформацію про клієнтів, що ввійшли в чат, що й покинули його.
* */

package lab_5;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {

        try {

            while (!serverSocket.isClosed()){

                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected");

                ClientHandle clientHandle = new ClientHandle(socket);
                Thread thread = new Thread(clientHandle);
                thread.start();
            }
        }
        catch (IOException e) {
            cleanServerSocket();
        }
    }

    public void cleanServerSocket() {

        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}
