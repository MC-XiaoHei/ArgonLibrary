package xor7studio.actuator;

import org.jetbrains.annotations.NotNull;

public class Xor7Actuator {
    Xor7ActuatorMethods methods;
    public Xor7Actuator(Xor7ActuatorMethods methods){
        this.methods=methods;
    }
    public void run(@NotNull String input){
        String [] tmp=input.split("@",2);
        if(tmp.length!=2)
            throw new IllegalArgumentException();
        String cmd=tmp[0];
        String args=tmp[1];
        methods.runMethod(cmd,args);
    }
}
