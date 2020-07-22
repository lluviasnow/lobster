package com.njuse.jvmfinal.classloader.classfilereader.classpath;

import com.njuse.jvmfinal.util.IOUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * format : dir/subdir/.../
 */
public class DirEntry extends Entry{
    public DirEntry(String classpath) {
        super(classpath);
    }

    @Override
    public byte[] readClassFile(String className) throws IOException {
        if(className.contains(".")){
            className=className.replace(".", "\\");
            className=className.replace("\\class", ".class");
        }
        try {
            File file=new File(classpath+FILE_SEPARATOR+className);
            FileInputStream fileInputStream=new FileInputStream(file);
            byte[] res=null;
            res= IOUtil.readFileByBytes(fileInputStream);
            if(res==null) try {
                throw new ClassNotFoundException();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return res;
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}



