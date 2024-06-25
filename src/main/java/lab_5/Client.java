package lab_5;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private static String username;

    private static final Integer port = 1234;

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Client.username = username;
        }
        catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage() {

        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(username + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }
        catch (IOException e) {

            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageFromGroupChat;

                while (socket.isConnected()) {
                    try {
                        messageFromGroupChat = bufferedReader.readLine();
                        System.out.println(messageFromGroupChat);
                    }
                    catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {

        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        }
        catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        boolean stop = false;
        while (!stop) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Enter your username for the group chat: ");
                String username = reader.readLine();
                if (username.matches("[a-zA-Z].*")) {
                    if (username.length()>=4) {
                        if (username.length() <= 10) {
                            Socket socket = new Socket("localhost", port);
                            Client client = new Client(socket, username);

                            stop = true;

                            client.listenForMessage();
                            client.sendMessage();
                        }
                        else {
                            throw new IllegalUsernameException("The username is too long.");
                        }
                    }
                    else {
                        throw new IllegalUsernameException("The username is too short.");
                    }
                }
                else {
                    throw new IllegalUsernameException("Username can not start with number");
                }
            }
            catch (IllegalUsernameException e) {
                System.out.println("Caught: " + e);
            }
            catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
                System.exit(0);
            }
        }
    }
}

class IllegalUsernameException extends Exception {

    private final String message;

    IllegalUsernameException(String error) {
        message = error;
    }

    @Override
    public String toString() {
        return message + " Try again!";
    }
}