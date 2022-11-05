package xor7studio.util;

import com.moandjiezana.toml.Toml;
import xor7studio.util.Xor7IO;

import java.io.File;
import java.io.IOException;

public class Xor7Toml extends Toml{
    public Xor7Toml(String path,String filename){
        if(open(path,filename)){
            Xor7IO.println("读取配置文件:"+path);
        }
    }
    public boolean open(String path,String filename){
        try{
            Xor7File file=new Xor7File(path,filename);
            this.read(file.file);
            return true;
        }catch (RuntimeException e){
            Xor7IO.error("读取配置文件时出错:"+filename);
            e.printStackTrace();
            return false;
        }
    }
}
