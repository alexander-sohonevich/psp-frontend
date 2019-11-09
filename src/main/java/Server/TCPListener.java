package Server;

public interface TCPListener {
    void onException(TCPConnection tcpConnection, Exception e);
}