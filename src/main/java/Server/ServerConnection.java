package Server;

import java.io.IOException;
import java.net.Socket;

public class ServerConnection implements TCPListener  {
    private static ServerConnection connection;

    private TCPConnection tcpConnection;

    public static ServerConnection takeConnection() {
        if (connection == null) {
            connection = new ServerConnection();
        }
        return connection;
    }

    private ServerConnection() {
        try {
            tcpConnection = new TCPConnection(this,"localhost", 1234);
        } catch (
                IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {

    }

    public TCPConnection getTcpConnection() {
        return tcpConnection;
    }
}
