package com.njuse.jvmfinal;


import com.njuse.jvmfinal.classloader.classfilereader.ClassFileReader;
import com.njuse.jvmfinal.execution.Interpreter;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.Method;
import com.njuse.jvmfinal.runtime.JThread;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.classloader.ClassLoader;

import java.io.File;

public class Starter {
    public static void main(String[] args) {

    }

    /**
     * ⚠️警告：不要改动这个方法签名，这是和测试用例的唯一接口
     */
    public static void runTest(String mainClassName, String cp){
        ClassFileReader.setUserClasspath(cp);
        String JAVA_HOME = System.getenv("JAVA_HOME");
        ClassFileReader.setBootClasspath(String.join(File.separator,JAVA_HOME, "jre"));
        ClassFileReader.setExtClasspath(String.join(File.separator,JAVA_HOME, "jre"));
       // ClassFileReader.setExtClasspath(cp);
       // ClassFileReader.setBootClasspath(cp);
        ClassLoader loader=ClassLoader.getInstance();
        JClass clazz=null;
        try {
            clazz = loader.loadClass(mainClassName, null);
            if(clazz==null);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }



        JThread thread = new JThread();
        Method main = clazz.getMainMethod();
        StackFrame mainFrame = new StackFrame(thread, main, main.getMaxStack(), main.getMaxLocal());
        thread.pushFrame(mainFrame);
        clazz.initClass(thread,clazz);//存疑
        Interpreter.interpret(thread);
    }

}
