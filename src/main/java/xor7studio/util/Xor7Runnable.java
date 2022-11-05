package xor7studio.util;


public abstract class Xor7Runnable {
    public abstract void run();
    private long basicTime;
    private long time;
    private boolean flag;
    private boolean startFlag;
    public Xor7Runnable(){
        basicTime=System.currentTimeMillis();
    }
    public void start(long time){
        flag=true;
        startFlag=false;
        this.time=time;
        new Thread(this::loop).start();
    }
    public void startOnce(){
        flag=false;
        new Thread(this::run).start();
    }
    public void stop(){flag=false;}
    private void loop(){
        if(!startFlag) {
            startFlag=true;
            run();
        }
        else if(System.currentTimeMillis()-time>=basicTime){
            basicTime+=time;
            run();
        }
        if(flag) new Thread(this::loop).start();
    }
}