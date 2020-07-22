package com.njuse.jvmfinal.classloader.classfilereader;

import com.njuse.jvmfinal.classloader.classfilereader.classpath.*;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.IOException;

public class ClassFileReader {
    private static ClassFileReader reader = new ClassFileReader();
    private static final String FILE_SEPARATOR = File.separator;
    private static final String PATH_SEPARATOR = File.pathSeparator;
    public static ClassFileReader getInstance() {
        return reader;
    }
    private static Entry bootClasspath = null;//bootstrap class entry
    private static Entry extClasspath = null;//extension class entry
    private static Entry userClasspath = null;//user class entry
    public static void setBootClasspath(String classpath) {
        bootClasspath = chooseEntryType(String.join(File.separator,classpath,"lib","*"));
    }
    public static void setExtClasspath(String classpath) {
        extClasspath = chooseEntryType(String.join(File.separator,classpath,"lib","ext","*"));
    }
    public static void setUserClasspath(String classpath) {
        userClasspath = chooseEntryType(classpath);
    }

    /**
     * select Entry by type of classpath
     */
    public static Entry chooseEntryType(String classpath) {
        if(classpath==null)return null;
        int len = classpath.length();
        String type = classpath.substring(len - 3, len);
        //System.out.println(type);
        if (type.equals("jar")||type.equals("JAR")||type.equals("zip")||type.equals("ZIP"))
            return new ArchivedEntry(classpath);
        if (classpath.contains(PATH_SEPARATOR))
            return new CompositeEntry(classpath);
        type=classpath.substring(len-1,len);
        if(type.equals("*"))return new WildEntry(classpath);
        return new DirEntry(classpath);
    }

    /**
     * @param className class to be read
     * @param privilege privilege of relevant class
     * @return content of class file and the privilege of loaded class
     */
    public Pair<byte[], Integer> readClassFile(String className, EntryType privilege) throws IOException, ClassNotFoundException {
        String realClassName = className + ".class";
        //realClassName = PathUtil.transform(realClassName);
        //todo
        /**
         * Add some codes here.
         *
         * You can pass realClassName to readClass()
         *
         * Read class file in privilege order
         * USER_ENTRY has highest privileges and Boot_Entry has lowest privileges.
         * If there is no relevant class loaded before, use default privilege.
         * Default privilege is USER_ENTRY
         *
         * Return the result once you read it.
         */
        int valueofPrivilege=EntryType.USER_ENTRY;
        if(privilege!=null)valueofPrivilege=privilege.getValue();
        byte[] thisClass=null;

        if(valueofPrivilege>=EntryType.BOOT_ENTRY)
            thisClass=bootClasspath.readClassFile(realClassName);
        if(thisClass!=null){
            return Pair.of(thisClass,EntryType.BOOT_ENTRY);
        }

        if(valueofPrivilege>=EntryType.EXT_ENTRY)
            thisClass=extClasspath.readClassFile(realClassName);
        if(thisClass!=null){
            return Pair.of(thisClass,EntryType.EXT_ENTRY);
        }

        if(valueofPrivilege>=EntryType.USER_ENTRY)
            thisClass=userClasspath.readClassFile(realClassName);
        if(thisClass!=null){
            return Pair.of(thisClass,EntryType.USER_ENTRY);
        }
        throw new ClassNotFoundException();
    }
}
