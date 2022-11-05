package xor7studio.actuator;

import xor7studio.util.Xor7IO;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Xor7ActuatorMethods {
    private final Map<String,Method> methodMap;
    @ActuatorIgnore
    public Xor7ActuatorMethods(){
        methodMap=new HashMap<>();
        List<String> ignoreMethod=new ArrayList<>();
        for(Method method:Object.class.getDeclaredMethods())
            ignoreMethod.add(method.getName());
        for(Method method:this.getClass().getMethods()){
            if(!ignoreMethod.contains(method.getName())){
                boolean flag=false;
                for(Annotation annotation: method.getAnnotations()){
                    if(annotation.annotationType().equals(ActuatorIgnore.class)){
                        flag=true;
                        break;
                    }
                }
                if(!flag) {
                    Xor7IO.debug("加载方法:"+method.getName());
                    methodMap.put(method.getName(), method);
                }
            }
        }
    }
    @ActuatorIgnore
    public void runMethod(String cmd,String args){
        try {
            if(methodMap.containsKey(cmd))
                methodMap.get(cmd).invoke(this,args);
            else
                Xor7IO.println("[错误] 找不到方法:"+cmd);
        } catch (InvocationTargetException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
