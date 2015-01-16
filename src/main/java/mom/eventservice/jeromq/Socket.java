package mom.eventservice.jeromq;

import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

public class Socket {
    private static final int DEF_TO = 10000;
    public static final int TIMEOUT = DEF_TO;
    private final ZMQ.Socket socket;

    Socket(ZMQ.Socket socket) {
        this.socket = socket;
    }

    public void bind(String address) {
        socket.bind(address);
        setSendTimeOut(DEF_TO);
    }

    public void send(String notification) {
        //ZMsg msg = ZMsg.newStringMsg(notification);
        //msg.send(socket);
        socket.send(notification);
    }

    public void close() {
        socket.close();
    }

    public void connect(String address) {
        socket.connect(address);
        setReceiveTimeOut(DEF_TO);
    }

    public String recvStr() {
        ZMsg msg = ZMsg.recvMsg(socket);
        if (msg == null)
            return null;
        byte[] bytes = msg.getFirst().getData();
        return new String(bytes);
    }

    public void subscribe(byte[] bytes) {
        socket.subscribe(bytes);
    }

    public void setReceiveTimeOut(int time) {
        socket.setReceiveTimeOut(time);
    }

    public void setSendTimeOut(int time) {
        socket.setSendTimeOut(time);
    }
}
