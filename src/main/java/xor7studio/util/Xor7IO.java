package xor7studio.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class Xor7IO {
    public static String modId="Undefined";
    public static boolean printDebugInfo = false;
    public static void debug(String s){
        if(printDebugInfo) println("[调试] "+s);
    }
    public static void error(String s){
        println("\033[31m[错误] "+s+"\033[0m");
    }
    public static void println(String s){print(s+"\n");}
    public static void print(String s){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format= new SimpleDateFormat("HH:mm:ss");
        System.out.printf("[%s] [%s] %s",format.format(date),modId,s);
        System.out.flush();
    }
}
