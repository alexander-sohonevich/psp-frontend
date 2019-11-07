package Connection;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private BufferedReader in;
    private BufferedWriter out;

    public Client() {
        System.out.println("Starting client");

        try {
            try {
                clientSocket = new Socket("127.0.0.1", 8189);

                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            } catch (IOException e) {
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void send(String value) {
        try {
            out.write(value);
            out.flush();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public String receive() {
        try {
            String value = in.readLine();
            return value;
        } catch (IOException e) {
            System.err.println(e);
        }
        return null;
    }

    public void closeConnection() {
        try {
            clientSocket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

}
