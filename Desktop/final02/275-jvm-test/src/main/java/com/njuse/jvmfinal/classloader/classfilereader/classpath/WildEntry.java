package com.njuse.jvmfinal.classloader.classfilereader.classpath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * format : dir/.../*
 */
public class WildEntry extends Entry{
    public WildEntry(String classpath) {
        super(classpath);
    }

    @Override
    public byte[] readClassFile(String className) throws IOException {

        String realPath=classpath.substring(0,classpath.length()-2);
        String allFiles[]=new String[100];
        int fileNum=0;
        File file=new File(realPath);
        File[] fileList=file.listFiles();
        for(int i=0;i<fileList.length;i++){
            File f=fileList[i];
            if(f.getName().endsWith(".JAR")||f.getName().endsWith(".jar")){
                //System.out.println(f.getAbsolutePath());
                if(f.getAbsolutePath()!=null){

                    allFiles[++fileNum]=f.getAbsolutePath();
                }

            }
        }
        if(fileNum==0)return null;
        int now=0;
        while(allFiles[now]==null)now++;
        String newClassname=allFiles[now];
        for(int i=now+1;i<allFiles.length;i++){
            if(allFiles[i]==null)break;
            newClassname+=PATH_SEPARATOR;
            newClassname+=allFiles[i];
        }
        CompositeEntry entry=new CompositeEntry(newClassname);
        return entry.readClassFile(className);
    }
}
