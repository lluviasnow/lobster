package com.njuse.jvmfinal.classloader.classfilereader.classpath;

import com.njuse.jvmfinal.util.IOUtil;

import java.io.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.Enumeration;
/**
 * format : dir/subdir/target.jar
 */
public class ArchivedEntry extends Entry{

    public ArchivedEntry(String classpath) {
        super(classpath);
    }

    @Override
    public byte[] readClassFile(String className) throws IOException {
        ZipFile zip=new ZipFile(new File(classpath));
        Enumeration enumeration=zip.entries();
        while(enumeration.hasMoreElements()){
            ZipEntry zipEntry=(ZipEntry)enumeration.nextElement();
            if(zipEntry.getName().equals(className)){
                byte[] res=null;
                res=IOUtil.readFileByBytes(zip.getInputStream(zipEntry));
                return res;
            }
        }
        return null;
    }

}
