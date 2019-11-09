package Server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPConnection implements TCPListener {

    private final Socket socket;
    private final TCPListener eventListener;
    private final BufferedReader in;
    private final BufferedWriter out;

    public TCPConnection(TCPListener eventListener, String IP, int port) throws IOException {
        this(eventListener, new Socket(IP, port));
    }

    private TCPConnection(TCPListener eventListener, Socket socket) throws IOException {
        this.eventListener = eventListener;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
    }

    public synchronized void sendString(String value) {
        try {
            out.write(value + "\r\n");
            out.flush();
        } catch (IOException e) {
            eventListener.onException(TCPConnection.this, e);
            disconnect();
        }
    }


    public synchronized Boolean ping() {
        return socket.isConnected();
    }

    public synchronized String receiveString() {
        try {
            return in.readLine();
        } catch (IOException e) {
            eventListener.onException(TCPConnection.this, e);
            disconnect();
        }
        return null;
    }


    public synchronized void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            eventListener.onException(TCPConnection.this, e);
        }
    }

    @Override
    public String toString() {
        return "Connectivity.TCPConnection: " + socket.getInetAddress() + ": " + socket.getPort();
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {

    }
}