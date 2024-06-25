package other;
import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    private boolean newMessage;
    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.username = username;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.newMessage = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized String receiveMessage() {
        try {
            while (!newMessage) {
                wait(); // Чекаємо, поки не буде отримано нове повідомлення
            }
            newMessage = false;
            String message = bufferedReader.readLine();
            notify(); // Повідомляємо головний потік, що повідомлення було отримане
            return message;
        } catch (IOException | InterruptedException e) {
            close();
            return null;
        }
    }

    public synchronized void sendMessage(String message) {
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            newMessage = true;
            notify(); // Повідомляємо інший потік, що нове повідомлення готове для отримання
        }
        catch (IOException e) {
            close();
        }
    }

    public void close() {
        try {
            if (bufferedReader != null) bufferedReader.close();
            if (bufferedWriter != null) bufferedWriter.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startClient(Socket socket, String username) throws IOException {

        Client client = new Client(socket, username);
        System.out.println("Connected to the server.");

        // Отримуємо і відправляємо повідомлення про приєднання до сервера
        client.sendMessage(username);

        while (socket.isConnected()) {

            // Отримуємо повідомлення від інших клієнтів
            String receivedMessage = client.receiveMessage();
            if (receivedMessage != null) {
                System.out.println(receivedMessage);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            // Надсилаємо введене користувачем повідомлення
            String messageToSend = reader.readLine();
            client.sendMessage(username + ": " + messageToSend);

        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter your username for the group chat: ");
        String username = reader.readLine();

        Socket socket = new Socket("localhost", 1234);
        startClient(socket, username);
    }
}

