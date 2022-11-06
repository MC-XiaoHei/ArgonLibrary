package xor7studio.util;

import java.io.File;
import java.io.IOException;

public class Xor7File {
    public File file;
    public Xor7File(String path,String filename){
        file=new File(path+File.separator+filename);
        if(!file.isFile()) {
            try {
                if(!file.getParentFile().mkdirs() || !file.createNewFile() ){
                    throw new RuntimeException("无法创建文件:"+filename);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
