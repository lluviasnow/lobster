package com.njuse.jvmfinal.classloader.classfilereader.classpath;

import java.io.IOException;

/**
 * format : dir/subdir;dir/subdir/*;dir/target.jar*
 */
public class CompositeEntry extends Entry{

    public CompositeEntry(String classpath) {
        super(classpath);
    }

    @Override
    public byte[] readClassFile(String className) throws IOException {
        String allClassname[]=classpath.split(PATH_SEPARATOR);
        int numberOfclass=allClassname.length;
        byte res[]=null;
        for(int i=0;i<numberOfclass;i++){
            Entry entry=ClassFileReader.chooseEntryType(allClassname[i]);
            res=entry.readClassFile(className);
            if(res!=null)return res;
        }
        return null;
    }
}
