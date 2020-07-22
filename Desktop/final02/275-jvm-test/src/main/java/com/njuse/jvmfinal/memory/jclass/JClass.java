package com.njuse.jvmfinal.memory.jclass;

import com.njuse.jvmfinal.classloader.classfileparser.ClassFile;
import com.njuse.jvmfinal.classloader.classfileparser.FieldInfo;
import com.njuse.jvmfinal.classloader.classfileparser.MethodInfo;
import com.njuse.jvmfinal.classloader.classfileparser.constantpool.ConstantPool;
import com.njuse.jvmfinal.classloader.classfilereader.classpath.EntryType;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.InitState;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import com.njuse.jvmfinal.runtime.JThread;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.Vars;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class JClass {
    private short accessFlags;
    private String name;
    private String superName;
    private String[] interfaceNames;
    private RuntimeConstantPool runtimeConstantPool;
    private Field[] fields;
    private Method[] methods;
    private EntryType loadEntryType; //请自行设计是否记录、如何记录加载器

    //private EntryType loadEntryType;

    private JClass superClass;
    private JClass[] interfaces;
    private int instanceSlotCount;

    private int staticSlotCount;
    private Vars staticVars; // 请自行设计数据结构
    private InitState initState; // 请自行设计初始化状态

    public JClass(ClassFile classFile) {
        this.accessFlags = classFile.getAccessFlags();
        this.name = classFile.getClassName();
        if (!this.name.equals("java/lang/Object")) {
            // index of super class of java/lang/Object is 0
            this.superName = classFile.getSuperClassName();
        } else {
            this.superName = "";
        }
        this.interfaceNames = classFile.getInterfaceNames();
        this.fields = parseFields(classFile.getFields());
        this.methods = parseMethods(classFile.getMethods());
        this.runtimeConstantPool = parseRuntimeConstantPool(classFile.getConstantPool());
    }


    public void setEntry(EntryType entryType){
        loadEntryType=entryType;
    }

    private Field[] parseFields(FieldInfo[] info) {
        int len = info.length;
        fields = new Field[len];
        for (int i = 0; i < len; i++) {
            fields[i] = new Field(info[i], this);
        }
        return fields;
    }

    private Method[] parseMethods(MethodInfo[] info) {
        int len = info.length;
        methods = new Method[len];
        for (int i = 0; i < len; i++) {
            methods[i] = new Method(info[i], this);
        }
        return methods;
    }

    //TODO
    private Method getMethodInClass(String name, String descriptor, boolean isStatic) {
        JClass clazz = this;
        Method[] methods = clazz.getMethods();
        for (Method m : methods) {
            if (m.getDescriptor().equals(descriptor)&&m.getName().equals(name)&&m.isStatic()==isStatic) {
                return m;
            }
        }
        return null;
    }
    public Method getMainMethod() {
        return this.getMethodInClass("main", "([Ljava/lang/String;)V", true);
    }

    public Optional<Method> resolveMethod(String name, String descriptor) {
        for (Method m : methods) {
            if (m.getDescriptor().equals(descriptor) && m.getName().equals(name)) {
                return Optional.of(m);
            }
        }
        return Optional.empty();
    }

    public boolean isAccSuper() {
        return 0 != (this.accessFlags & AccessFlags.ACC_SUPER);
    }

    public void initClass(JThread thread, JClass clazz) {
        clazz.initState=InitState.BUSY;


        this.initThis(thread,clazz);
       // if(!clazz.isInterface())this.initSuper(thread,clazz);


        clazz.initState=InitState.SUCCESS;
    }
    public void initSuper(JThread thread, JClass clazz){
        JClass superClass=clazz.getSuperClass();
        if(superClass==null)return;
        if(superClass.getInitState()==InitState.PREPARED)
            initClass(thread,superClass);
    }
    public void initThis(JThread thread, JClass clazz){
        Method method=clazz.getMethodInClass("<clinit>", "()V", true);
        if(method!=null)
            thread.pushFrame(new StackFrame(thread,method,method.getMaxStack(),method.getMaxLocal()));
    }

    public boolean isInterface() {
        return 0 != (this.accessFlags & AccessFlags.ACC_INTERFACE);
    }

    /**
     * search method in class and its superclass
     *
     * @return
     */


    private RuntimeConstantPool parseRuntimeConstantPool(ConstantPool cp) {
        return new RuntimeConstantPool(cp, this);
    }
}
