package xor7studio.network;

import org.jetbrains.annotations.NotNull;
import xor7studio.util.Xor7Runnable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class MsgServer {
    private long listenInterval =100;
    private final int port;
    private ServerSocket socket;
    public abstract void onReceive(String msg);
    public MsgServer(int port){
        this.port=port;
    }
    public void setListenInterval(long listenInterval) {
        this.listenInterval = listenInterval;
    }
    public void start(){
        try {
            socket=new ServerSocket(port);
            new Xor7Runnable(){
                @Override
                public void run(){
                    Socket s;
                    try {
                        s=socket.accept();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    new SocketRunnable(){
                        @Override
                        public void run() {
                            String msg;
                            try {
                                msg=input.readUTF();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            if(!msg.equals("")) onReceive(msg);
                        }
                    }.setSocket(s).start(listenInterval);
                    this.startOnce();
                }
            }.startOnce();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public abstract static class SocketRunnable extends Xor7Runnable {
        DataInputStream input;
        DataOutputStream output;
        public SocketRunnable setSocket(@NotNull Socket socket){
            try {
                input=new DataInputStream(socket.getInputStream());
                output=new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return this;
        }
    }
}
