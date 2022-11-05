package xor7studio.network;

import xor7studio.util.Xor7Runnable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class MsgClient {
    private long listenInterval =100;
    private final int port;
    private final String host;
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    public abstract void onReceive(String msg);
    public MsgClient(int port){
        this("127.0.0.1",port);
    }
    public MsgClient(String host,int port){
        this.host=host;
        this.port=port;
    }
    public void setListenInterval(long listenInterval) {
        this.listenInterval = listenInterval;
    }
    public void connect(){
        try {
            socket=new Socket(host,port);
            input=new DataInputStream(socket.getInputStream());
            output=new DataOutputStream(socket.getOutputStream());
            new Xor7Runnable(){
                @Override
                public void run(){
                    String msg = null;
                    try {
                        msg = input.readUTF();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if(!msg.equals("")) onReceive(msg);
                }
            }.start(listenInterval);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMessage(String msg){
        try {
            output.writeUTF(msg);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void disconnect() {
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
